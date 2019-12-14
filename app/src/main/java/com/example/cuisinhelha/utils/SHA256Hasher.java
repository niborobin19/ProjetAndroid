package com.example.cuisinhelha.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hasher {
	public static final String ALGORITHM_NAME = "SHA-256";
	public static final int HASH_SIZE = 64;

	/**
	 * Hashes a String using the SHA-256 hash algorithm. Hashes the passed String to
	 * an array of bytes and then converts it into a string.
	 * 
	 * @param toHash the String object to hash.
	 * @throws IllegalArgumentException if the passed String is either empty or
	 *                                  null.
	 * @return String object, the hashed String.
	 */
	public static String hash(String toHash) {
		if (toHash.length() > 0) {
			try {
				MessageDigest digest = MessageDigest.getInstance(ALGORITHM_NAME);
				byte[] byteHash = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));

				String hashedString = convertBytesToString(byteHash);

				// Verify that the hashedString has the right size
				if (isHashedLength(hashedString))
					return hashedString;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} else {
			// When toHash is empty or null
			throw new IllegalArgumentException("The passed String is either empty or null.");
		}

		return null;
	}

	/**
	 * Converts an array of bytes to a String.
	 * 
	 * @param toConvert the bytes array to convert.
	 * 
	 * @return String object, the converted String.
	 */
	public static String convertBytesToString(byte[] toConvert) {
		String converted = String.format( "%064x", new BigInteger( 1, toConvert ) );
		return converted;
	}

	/**
	 * Tests if the given string size is equal to SHA-256 hash size.
	 * 
	 * @param hashed String, the presumed hashed string.
	 * @return boolean, if hashed has the right size.
	 */
	public static boolean isHashedLength(String hashed) {
		return hashed.length() == HASH_SIZE;
	}

}
