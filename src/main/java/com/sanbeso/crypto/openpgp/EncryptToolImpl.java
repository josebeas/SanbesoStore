package com.sanbeso.crypto.openpgp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.springframework.stereotype.Component;

import com.sanbeso.crypto.EncryptTool;
import com.sanbeso.service.impl.ProductServiceImpl;

/**
 * 
 * @author Jose Beas
 * November 6, 2014
 *
 */
@Component("cryptTool")
public class EncryptToolImpl implements EncryptTool {

	private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class);
	
	@SuppressWarnings("deprecation")
	@Override
	public PGPPrivateKey findSecretKey(PGPSecretKeyRingCollection pgpSec, long keyID, char[] pass) 
			throws PGPException, NoSuchProviderException {
		PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);
        if (pgpSecKey == null) {
            return null;
        }
        return pgpSecKey.extractPrivateKey(pass, "BC");
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "resource" })
	@Override
	public byte[] decrypt(byte[] encrypted, InputStream secretkeyIn, char[] password) 
			throws IOException, PGPException, NoSuchProviderException {
		LOG.info("Trying to decrypt data");
		InputStream in = new ByteArrayInputStream(encrypted);
        in = PGPUtil.getDecoderStream(in);
        PGPObjectFactory pgpF = new PGPObjectFactory(in);
        PGPEncryptedDataList enc = null;
        Object o = pgpF.nextObject();
        //
        // the first object might be a PGP marker packet.
        //
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpF.nextObject();
        }
        //
        // find the secret key
        //
        Iterator it = enc.getEncryptedDataObjects();
        PGPPrivateKey sKey = null;
        PGPPublicKeyEncryptedData pbe = null;
        PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                PGPUtil.getDecoderStream(secretkeyIn));

        while (sKey == null && it.hasNext()) {
            pbe = (PGPPublicKeyEncryptedData) it.next();
            sKey = findSecretKey(pgpSec, pbe.getKeyID(), password);
        }
        if (sKey == null) {
            throw new IllegalArgumentException("secret key for message not found.");
        }
        InputStream clear = pbe.getDataStream(sKey, "BC");
        PGPObjectFactory pgpFact = new PGPObjectFactory(clear);
        PGPCompressedData cData = (PGPCompressedData) pgpFact.nextObject();
        pgpFact = new PGPObjectFactory(cData.getDataStream());
        PGPLiteralData ld = (PGPLiteralData) pgpFact.nextObject();
        InputStream unc = ld.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int ch;
        while ((ch = unc.read()) >= 0) {
            out.write(ch);
        }
        byte[] returnBytes = out.toByteArray();
        out.close();
        return returnBytes;
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte[] encrypt(byte[] clearData, PGPPublicKey encKey, String fileName, boolean withIntegrityCheck, boolean armor)
			throws IOException, PGPException, NoSuchProviderException {
		LOG.info("Trying to encrypt data");
		if (fileName == null) {
            fileName = PGPLiteralData.CONSOLE;
        }
        ByteArrayOutputStream encOut = new ByteArrayOutputStream();
        OutputStream out = encOut;
        if (armor) {
            out = new ArmoredOutputStream(out);
        }
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(
                PGPCompressedDataGenerator.ZIP);
        OutputStream cos = comData.open(bOut); // open it with the final
        // destination
        PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
        // we want to generate compressed data. This might be a user option
        // later,
        // in which case we would pass in bOut.
        OutputStream pOut = lData.open(cos, // the compressed output stream
                PGPLiteralData.BINARY, fileName, // "filename" to store
                clearData.length, // length of clear data
                new Date() // current time
                );
        pOut.write(clearData);
        lData.close();
        comData.close();
        PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(
        		PGPEncryptedData.CAST5, withIntegrityCheck, new SecureRandom(), "BC");
        cPk.addMethod(encKey);
        byte[] bytes = bOut.toByteArray();
        OutputStream cOut = cPk.open(out, bytes.length);
        cOut.write(bytes); // obtain the actual bytes from the compressed stream
        cOut.close();
        out.close();
        return encOut.toByteArray();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PGPPublicKey readPublicKey(InputStream in) throws IOException, PGPException {
		LOG.info("REading public key");
		in = PGPUtil.getDecoderStream(in);
        PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(in);
        //
        // we just loop through the collection till we find a key suitable for
        // encryption, in the real world you would probably want to be a bit 
        // smarter about this.
        //
        // iterate through the key rings.
        //
        Iterator rIt = pgpPub.getKeyRings();
        while (rIt.hasNext()) {
            PGPPublicKeyRing kRing = (PGPPublicKeyRing) rIt.next();
            Iterator kIt = kRing.getPublicKeys();
            while (kIt.hasNext()) {
                PGPPublicKey k = (PGPPublicKey) kIt.next();
                if (k.isEncryptionKey()) {
                    return k;
                }
            }
        }
        throw new IllegalArgumentException("Can't find encryption key in key ring.");
	}

	@SuppressWarnings("resource")
	@Override
	public byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
	}

	@Override
	public byte[] readFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[4096];
        int numRead = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((numRead = fis.read(buf)) < 0) {
                baos.write(buf, 0, numRead);
        }
        fis.close();
        byte[] returnVal = baos.toByteArray();
        baos.close();
        return returnVal;
	}

}
