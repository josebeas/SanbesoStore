package com.sanbeso.crypto;

import org.bouncycastle.openpgp.PGPKeyRingGenerator;

/**
 * 
 * @author Jose Beas
 * November 6, 2014
 *
 */
public interface RSAKeyGen {

	/**
	 * 
	 * @param path
	 * @param user
	 * @param passphrase
	 * @throws Exception
	 */
	public void generateKeys(String path, String user, String passphrase) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	public PGPKeyRingGenerator generateKeyRingGenerator(String id, char[] pass) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @param pass
	 * @param s2kcount
	 * @return
	 * @throws Exception
	 */
	public PGPKeyRingGenerator generateKeyRingGenerator(String id, char[] pass, int s2kcount) throws Exception;
}
