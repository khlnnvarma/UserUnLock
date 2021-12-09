package com.java.util;


import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.customexception.CommonException;


public class EncryptionAndDecryption {
	
	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'E', 'S','N','T', 'E', 'S','N','T', 'E', 'S','N','T', 'E', 'S','J' };

	private static Logger log= LoggerFactory.getLogger(EncryptionAndDecryption.class);
	
	

	private EncryptionAndDecryption() {
		super();
	}

	/**
	 * Encrypt a string using AES encryption algorithm.
	 *
	 * @param pwd the password to be encrypted
	 * @return the encrypted string
	 * @throws CommonException 
	 */
	public static String encrypt(String pwd) throws CommonException {
	    String encodedPwd = null;
	    try {
	        Key key = generateKey();
	        Cipher c = Cipher.getInstance(ALGO);
	        c.init(Cipher.ENCRYPT_MODE, key);
	        byte[] encVal = c.doFinal(pwd.getBytes());
	        encodedPwd = Base64.getEncoder().encodeToString(encVal);

	    } catch (Exception e) {
	    	log.info(e.getMessage(),e);
			throw new CommonException(e.getMessage());
	    }
	    return encodedPwd;

	}

	/**
	 * Decrypt a string with AES encryption algorithm.
	 *
	 * @param encryptedData the data to be decrypted
	 * @return the decrypted string
	 * @throws CommonException 
	 */
	public static String decrypt(String encryptedData) throws CommonException {
	    String decodedPWD = null;
	    try {
	        Key key = generateKey();
	        Cipher c = Cipher.getInstance(ALGO);
	        c.init(Cipher.DECRYPT_MODE, key);
	        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
	        byte[] decValue = c.doFinal(decordedValue);
	        decodedPWD = new String(decValue);

	    } catch (Exception e) {
	    	log.info(e.getMessage(),e);
			throw new CommonException(e.getMessage());
	    }
	    return decodedPWD;
	}

	/**
	 * Generate a new encryption key.
	 */
	private static Key generateKey() {
	     return new SecretKeySpec(keyValue, ALGO);
	}

}
