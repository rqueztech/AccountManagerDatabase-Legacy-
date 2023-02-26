 /*
	TEST COMPLETE: All passwords properly encrypted
	The encryptPassword test will ensure that strings passed into it
	are properly encrypted. These strings will first by validated for
	
 */

package test;

import databaseproject.InputOperations;
import databaseproject.PasswordEncryption;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

class Test_PasswordEncryption_encryptPassword {
	InputOperations inputOperations = new InputOperations();
	PasswordEncryption passwordEncryption = new PasswordEncryption();
	
	@Test
	void test() {
		StringBuilder sb = new StringBuilder();
		
		// String of legal arrays that can be ingested into the database
		
		for(int counter = 0; counter < 400; counter++) {
			this.passwordEncryption.generateSalt();
		}
		
		StringBuilder password = new StringBuilder();
		Random randomCharacter = new Random();
		Random rand = new Random(); 
		
		// Create a for loop that will go through 8 iterations. Each
		// Iterated will designate a random value of the following 8
		// String arrays.
		
		String upperCaseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 	// 0 && 3 - UpperCase
		String lowerCaseString = upperCaseString.toLowerCase(); // 1 && 4 - LowerCase
		String specialCharacterString = "@$!%*#?&"; 			// 2 && 5 - Special Character
		String numberString = "1234567890"; 					// 3 && 6 - Number
		char[] passwordStrings = new char[300];
		
		for(int outerCounter = 0; outerCounter < 300; outerCounter++) {
			int stringLength = rand.nextInt((16 - 8) + 1) + 8;
			
			// Iterate 8 times and select two random characters of each type
			for(int counter = 0; counter < stringLength; counter++) {
				
				switch(counter%4) {
					case 0:
						password.append(upperCaseString.charAt(randomCharacter.nextInt(upperCaseString.length())));
						break;
						
					case 1:
						password.append(lowerCaseString.charAt(randomCharacter.nextInt(lowerCaseString.length())));
						break;
						
					case 2:
						password.append(specialCharacterString.charAt(randomCharacter.nextInt(specialCharacterString.length())));
						break;
						
					case 3:
						password.append(numberString.charAt(randomCharacter.nextInt(numberString.length())));
						break;
						
					default:
						System.out.println("Error");
						break;
				}
			}
			
			passwordStrings = password.toString().toCharArray();
		}
		
		// Create an iterator to go through the legal character array
		for(char iterator : passwordStrings) {
			
			String hashedPassword = "";
			boolean legalCharacters = inputOperations.isLegalCharactersEntered(iterator);
			
			if(legalCharacters) {
				// The hashed password string will take the encrypted version of the password passed to it
				
				String customSalt = sb.append(passwordEncryption.generateSalt()).toString();
				hashedPassword = passwordEncryption.hashPassword(iterator, customSalt);
			
					
				// If the new string has a length other than 128, it has not been successfully hashed
				if(hashedPassword.length() < 128) {
					
					// If not hashed, print out error message
					System.out.println("****************************************************");
					System.out.println("FAIL: PasswordEncryption_hashedPassword()\n" 
							+ "Following Password Not Hashed Properly: " + iterator + " -> " + hashedPassword
							+ "Probable causes:\n-Hash Not Working\nIllegal Character Detected");
				}
			}
			
			else {
				System.out.println("****************************************************");
				System.out.println("FAIL: PasswordEncryption_hashedPassword()\nIllegal Characters In Password -> " + iterator);
				hashedPassword = " illegal Characters";
			}
			
			// If the hashedPassword is 128 characters, it was properly hashed
			assertEquals(128, hashedPassword.length());
		}
		
		// If not hashed, print out error message
		System.out.println("****************************************************");
		System.out.println("PASS: PasswordEncryption_hashedPassword()\n" 
				+ "All passwords properly hashed");
	}
}
