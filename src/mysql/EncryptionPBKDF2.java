package mysql;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


/* @author Anh Tran
 * 
 * Description:
 * PBKDF2 with HmacSHA512 password cryptography in Java.
 * Hash + salt user's password and add salt to database
 * 
 * 
 * Source: @author Suraj Kumar
 */

public class EncryptionPBKDF2 {
	
	private static final String ALGORITHM = EncryptionPBKDF2.class.getSimpleName();
	private static final int ITERATION_COUNT = 1000;
	
	
	
	

}
