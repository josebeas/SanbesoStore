package com.sanbeso.crypto.openpgp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

import org.apache.log4j.Logger;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.Features;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPKeyPair;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPGPKeyPair;
import org.springframework.stereotype.Component;

import com.sanbeso.crypto.RSAKeyGen;
import com.sanbeso.service.impl.ProductServiceImpl;

/**
 * 
 * @author Jose Beas
 * November 6, 2014
 */
@Component("RSAKeyGen")
public class RSAKeyGenImpl implements RSAKeyGen {

	private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class);
	
	@Override
	public void generateKeys(String path, String user, String passphrase) throws Exception {
		LOG.info("Generating keys for " + user + " on path " + path);
		File file = new File(path);
		if(!file.exists())
			file.mkdirs();
		PGPKeyRingGenerator krgen = generateKeyRingGenerator(user, passphrase.toCharArray());
		//Generate public key ring, dump to file.
		PGPPublicKeyRing pkr = krgen.generatePublicKeyRing();
		BufferedOutputStream pubout = new BufferedOutputStream(new FileOutputStream(path+"/"+user+".pkr"));
		pkr.encode(pubout);
		pubout.close();
		//Generate public key ring, dump to file.
		PGPSecretKeyRing skr = krgen.generateSecretKeyRing();
		BufferedOutputStream secout = new BufferedOutputStream(new FileOutputStream(path+"/"+user+".skr"));
		skr.encode(secout);
		secout.close();
	}

	@Override
	public PGPKeyRingGenerator generateKeyRingGenerator(String id, char[] pass) throws Exception {
		return generateKeyRingGenerator(id, pass, 0xc0);
	}

	@Override
	public PGPKeyRingGenerator generateKeyRingGenerator(String id, char[] pass, int s2kcount) throws Exception {
		//This object generates individuals key-pairs
		RSAKeyPairGenerator kpg = new RSAKeyPairGenerator();
		// Boilerplate RSA parameters, no need to change anything
        // except for the RSA key-size (2048). You can use whatever
        // key-size makes sense for you -- 4096, etc.
		kpg.init (new RSAKeyGenerationParameters (BigInteger.valueOf(0x10001), new SecureRandom(), 2048, 12));
        // First create the master (signing) key with the generator.
        PGPKeyPair rsakp_sign = new BcPGPKeyPair(PGPPublicKey.RSA_SIGN, kpg.generateKeyPair(), new Date());
        // Then an encryption subkey.
        PGPKeyPair rsakp_enc = new BcPGPKeyPair(PGPPublicKey.RSA_ENCRYPT, kpg.generateKeyPair(), new Date());
        // Add a self-signature on the id
        PGPSignatureSubpacketGenerator signhashgen = new PGPSignatureSubpacketGenerator();
        // Add signed metadata on the signature.
        // 1) Declare its purpose
        signhashgen.setKeyFlags(false, KeyFlags.SIGN_DATA|KeyFlags.CERTIFY_OTHER);
        // 2) Set preferences for secondary crypto algorithms to use
        //    when sending messages to this key.
        signhashgen.setPreferredSymmetricAlgorithms(false, new int[] {
                SymmetricKeyAlgorithmTags.AES_256,
                SymmetricKeyAlgorithmTags.AES_192,
                SymmetricKeyAlgorithmTags.AES_128
            });
        signhashgen.setPreferredHashAlgorithms(false, new int[] {
                HashAlgorithmTags.SHA256,
                HashAlgorithmTags.SHA1,
                HashAlgorithmTags.SHA384,
                HashAlgorithmTags.SHA512,
                HashAlgorithmTags.SHA224,
            });
        // 3) Request senders add additional checksums to the
        //    message (useful when verifying unsigned messages.)
        signhashgen.setFeature(false, Features.FEATURE_MODIFICATION_DETECTION);
        // Create a signature on the encryption subkey.
        PGPSignatureSubpacketGenerator enchashgen = new PGPSignatureSubpacketGenerator();
        // Add metadata to declare its purpose
        enchashgen.setKeyFlags(false, KeyFlags.ENCRYPT_COMMS|KeyFlags.ENCRYPT_STORAGE);
        // Objects used to encrypt the secret key.
        PGPDigestCalculator sha1Calc = new BcPGPDigestCalculatorProvider().get(HashAlgorithmTags.SHA1);
        PGPDigestCalculator sha256Calc = new BcPGPDigestCalculatorProvider().get(HashAlgorithmTags.SHA256);
        // bcpg 1.48 exposes this API that includes s2kcount. Earlier
        // versions use a default of 0x60.
        PBESecretKeyEncryptor pske = (new BcPBESecretKeyEncryptorBuilder(PGPEncryptedData.AES_256, sha256Calc, s2kcount)).build(pass); 
        // Finally, create the keyring itself. The constructor
        // takes parameters that allow it to generate the self
        // signature.
        PGPKeyRingGenerator keyRingGen = new PGPKeyRingGenerator(PGPSignature.POSITIVE_CERTIFICATION, rsakp_sign,
             id, sha1Calc, signhashgen.generate(), null, new BcPGPContentSignerBuilder(rsakp_sign.getPublicKey().getAlgorithm(),
             HashAlgorithmTags.SHA1), pske);
        // Add our encryption subkey, together with its signature.
        keyRingGen.addSubKey(rsakp_enc, enchashgen.generate(), null);
        return keyRingGen;
	}

}
