package br.com.myaquarium.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import br.com.myaquarium.exceptions.UserException;
import br.com.myaquarium.exceptions.enuns.UserExceptions;

// TODO this class will be changed in future, we will use spring security
public class PasswordSecurity {
	
	final static Logger logger = Logger.getLogger(PasswordSecurity.class);
	
	/**
	 * This method is use to transform a string in a hash value
	 * @param password
	 * @return
	 * @throws UserException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String transformPassword(String password)
			throws UserException, NoSuchAlgorithmException, UnsupportedEncodingException {
		String passwordInHash = null;

		if (password == null || password.isEmpty()) {
			logger.error("Password cannot be null");
			throw new UserException(UserExceptions.PASSWORD_CAN_NOT_BE_NULL);
		}

		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		passwordInHash = hexString.toString();

		return passwordInHash;
	}

}
