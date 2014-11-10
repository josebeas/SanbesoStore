package com.sanbeso.crypto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchProviderException;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;

/**
 * 
 * @author Jose Beas
 * November 6, 2014
 *
 */

public interface EncryptTool {

	/**
	 * 
	 * @param pgpSec
	 * @param keyID
	 * @param pass
	 * @return
	 * @throws PGPException
	 * @throws NoSuchProviderException
	 */
	public PGPPrivateKey findSecretKey(PGPSecretKeyRingCollection pgpSec, long keyID, char[] pass) 
			throws PGPException, NoSuchProviderException;
	
	/**
	 * 
	 * @param encrypted
	 * @param secretkeyIn
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws PGPException
	 * @throws NoSuchProviderException
	 */
	public byte[] decrypt(byte[] encrypted, InputStream secretkeyIn, char[] password)
		    throws IOException, PGPException, NoSuchProviderException;
	
	/**
	 * 
	 * @param clearData
	 * @param encKey
	 * @param fileName
	 * @param withIntegrityCheck
	 * @param armor
	 * @return
	 * @throws IOException
	 * @throws PGPException
	 * @throws NoSuchProviderException
	 */
	public byte[] encrypt(byte[] clearData, PGPPublicKey encKey, String fileName,boolean withIntegrityCheck, boolean armor)
            throws IOException, PGPException, NoSuchProviderException;
	
	/**
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws PGPException
	 */
	public PGPPublicKey readPublicKey(InputStream in) throws IOException, PGPException;
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public byte[] getBytesFromFile(File file) throws IOException;
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public byte[] readFile(File file) throws IOException;
}
