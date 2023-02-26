package databaseproject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordEncryption {

	public String hashPassword(char[] attemptedPassword, String salt) {
	    int iterations = 10000;
	    int keyLength = 512;
	    byte[] saltBytes = salt.getBytes();
	    
	    try {
	    	
	        PBEKeySpec spec = new PBEKeySpec(attemptedPassword, saltBytes, iterations, keyLength);
	        SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
	        byte[] hashedPassword = key.generateSecret(spec).getEncoded();
	        
	        StringBuilder sb = new StringBuilder();
	        for(byte b : hashedPassword) {
	            sb.append(String.format("%02x", b));
	        }
	        
	        return sb.toString();
	    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	        e.printStackTrace();
	    }
	    
	    return "";
	}
	
	// Generate a string of 8 random characters. This will be aggregated to a given password
	// In order to add randomization when the SHA-512 hash will be performed on the user's password
	public String generateSalt() {
		StringBuilder salt = new StringBuilder();
		Random randomCharacter = new Random();
		
		// Create a for loop that will go through 8 iterations. Each
		// Iterated will designate a random value of the following 8
		// String arrays.
		
		String upperCaseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 	// 0 && 3 - UpperCase
		String lowerCaseString = upperCaseString.toLowerCase(); // 1 && 4 - LowerCase
		String specialCharacterString = "@$!%*#?&"; 			// 2 && 5 - Special Character
		String numberString = "1234567890"; 					// 3 && 6 - Number
		
		// Iterate 8 times and select two random characters of each type
		for(int counter = 0; counter < 128; counter++) {
			
			switch(counter%4) {
				case 0:
					salt.append(upperCaseString.charAt(randomCharacter.nextInt(upperCaseString.length())));
					break;
					
				case 1:
					salt.append(lowerCaseString.charAt(randomCharacter.nextInt(lowerCaseString.length())));
					break;
					
				case 2:
					salt.append(specialCharacterString.charAt(randomCharacter.nextInt(specialCharacterString.length())));
					break;
					
				case 3:
					salt.append(numberString.charAt(randomCharacter.nextInt(numberString.length())));
					break;
					
				default:
					System.out.println("Error");
					break;
			}
		}
		
		// Convert the salt into a string
		return salt.toString();
	}
}