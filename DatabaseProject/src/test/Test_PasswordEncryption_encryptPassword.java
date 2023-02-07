 /*
	TEST COMPLETE: All passwords properly encrypted
	The encryptPassword test will ensure that strings passed into it
	are properly encrypted. These strings will first by validated for
	
 */

package test;

import databaseproject.InputOperations;
import databaseproject.PasswordEncryption;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Test_PasswordEncryption_encryptPassword {
	InputOperations inputOperations = new InputOperations();
	PasswordEncryption passwordEncryption = new PasswordEncryption();

	@Test
	void test() {
		StringBuilder sb = new StringBuilder();
		
		// String of legal arrays that can be ingested into the database
		
		// List of legal characters
		String[] legalCharacterArray = {"Jacov7&", "Paco7&#?", "Ray%7#%&", "Charlie&7", "Bacon4#", "Charles@7", 
				"Char12341234314325123@#&", "People@!45", "pQow#@352", "charles&#&#&#924827051923817523"};
		
		// Create an iterator to go through the legal character array
		for(String iterator : legalCharacterArray) {
			
			String hashedPassword = "";
			boolean legalCharacters = inputOperations.legalCharactersEntered(iterator);
			
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
