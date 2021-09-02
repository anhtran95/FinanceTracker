package mysql;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


/* @author Anh Tran
 * 
 * Description:
 * PBKDF2 with HmacSHA512 password cryptography.
 * Hash and salt user's password
 */

public class PBKDF2WithHmacSHA512 
{
	//debug
	private static final String file_name = "PBKDF2WithHmacSHA512.java";	

	//constants
	private static final String ALGORITHM = 		PBKDF2WithHmacSHA512.class.getSimpleName();
	private static final int ITERATION_COUNT = 		1000;	//bigger the number, takes longer to generate a key
	private static final int KEY_LENGTH = 			64;		//length of derive key
	
	
	//Private constructor to avoid instantiated	
	private PBKDF2WithHmacSHA512() 
	{
		throw new AssertionError();
	}
	
	
	/**
	 * This method returns an encrypted byte[] of the password.
	 * 
	 * @param password
	 *            The password to encrypt.
	 * @param SALT
	 *            The random data used for the hashing function.
	 * @return The encrypted password as a byte[].
	 * @throws NoSuchAlgorithmException
	 *             If the cryptographic algorithm is unavailable.
	 * @throws InvalidKeySpecException
	 *             If the derived key cannot be produced.
	 */
	public static byte[] hash(final String password, final byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		final KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
		final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		return secretKeyFactory.generateSecret(keySpec).getEncoded();
	}
	
	/**
	 * Generates a random salt used for password matching.
	 * 
	 * @return A randomly produced byte[].
	 * 
	 * @throws NoSuchAlgorithmException
	 *             If SHA1PRNG does not exist on the system.
	 */
	public static byte[] salt()
	{
		final byte[] salt = new byte[16];
		try 
		{
			SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);
			return salt;
		}
		catch(NoSuchAlgorithmException exp)
		{
			System.out.println("Failed to get salt...");
			DebugHelper.getCurrentLineAndDir(file_name);
			System.out.print(exp);
		}
		
		return salt;
	}
	
	
	/**
	 * This method takes a byte[] and converts it to a String.
	 * E.g. byte[] test = {1, 2, 3, 4} this method will convert that 
	 * into a String that looks like this: "1 2 3 4";
	 * 
	 * This allows you to save an array to a database easily.
	 * 
	 * @param payload The byte[] to convert to a String
	 * @return The converted byte[] as a String
	 */
	public static String byteConvertToString(final byte[] payload)
	{
		String result = "";
		for(byte b : payload)
		{
			result += b + " ";
		}
		
		return result.trim();
	}
	
	
	/**
	 * This method is specific to this password hashing service. It converts a
	 * String (assuming it contains suitable values that can be converted to a
	 * byte) to a byte[].
	 * 
	 * @param s
	 *            The String to convert
	 * @return The converted String to a byte[]
	 */
	public static byte[] stringToByteArray(final String s)
	{
		final String[] sArray = s.split(" ");
		final byte[] byteArray = new byte[sArray.length];
		for(int sIndex = 0; sIndex < sArray.length; sIndex++)
		{
			byteArray[sIndex] = Byte.parseByte(sArray[sIndex]);
		}
		
		return byteArray;
	}
	
	
	/**
	 * Checks the attemptedPassword against the encryptedPassword using the
	 * random salt.
	 * 
	 * @param attemptedPassword
	 *            The password entered by the user.
	 * @param encryptedPassword
	 *            The hashed password stored on the database.
	 * @param SALT
	 *            The salt to use
	 * @return If the attempted password matched the hashed password.
	 * @throws Exception
	 *             If the algorithm cannot be performed.
	 */
	public static boolean authenticatePassword(final String attemptedPassword,
											 final byte[] salt,
											 final byte[] hashedPassword)
	{
		boolean passCompare = false;
		try
		{
			return Arrays.equals(hash(attemptedPassword, salt), hashedPassword);
		}
		catch(Exception e)
		{
			System.out.println("Failed to compare passwords...");
			DebugHelper.getCurrentLineAndDir(file_name);
			System.out.println(e);
		}
		
		return passCompare;
		
	}
	
	
	/**
	 * Convert from byte[] to string
	 * 
	 * @param binary
	 * 				byte array that needs to convert to string
	 * @return
	 */
	public static String byteToString(final byte[] binary)
	{
		return Base64.getEncoder().encodeToString(binary);
		
	}
}
