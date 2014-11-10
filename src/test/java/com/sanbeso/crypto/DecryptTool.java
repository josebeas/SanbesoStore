package com.sanbeso.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.openpgp.PGPException;

import com.sanbeso.crypto.openpgp.EncryptToolImpl;

/**
 * Test class to decrypt a previously encrypted file, using a private key and a passphrase
 * 
 * @author Jose Beas
 * November 6, 2014
 *
 */
public class DecryptTool {

	/**
	 * 
	 * @param args[0] file who wants to decrypt
	 * @param args[1] private key
	 * @param args[2] passphrase
	 * @throws NoSuchProviderException
	 * @throws IOException
	 * @throws PGPException
	 */
	public static void main(String[] args) throws NoSuchProviderException, IOException, PGPException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		EncryptTool tool = new EncryptToolImpl();
		//Read crypted file
		StringBuilder sb = new StringBuilder();
		String everything = "";
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
	    try {
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        everything = sb.toString();
	    } finally {
	        br.close();
	    }
		byte[] encrypted = everything.getBytes();
		System.out.println("decrypting data for test purposes" + new String(encrypted));
		//Read private Key
		FileInputStream secKey = new FileInputStream(args[1]);
		//decrypt data
		byte[] decrypted = tool.decrypt(encrypted, secKey, args[2].toCharArray());
		String result = new String(decrypted);
		//print decrypted data on console
		System.out.println(result);
		//Save results into a new file
		File file = new File(args[0]+" - decripted");
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(result);
		bw.close();
		
	}
}
