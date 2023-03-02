 /*
	TEST COMPLETE: All passwords properly encrypted
	The encryptPassword test will ensure that strings passed into it
	are properly encrypted. These strings will first by validated for
	
 */

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

class Test_PasswordEncryption_encryptPassword {
	InputOperations inputOperations = new InputOperations();
	PasswordEncryption passwordEncryption = new PasswordEncryption();
	
	@Test
	void test() {
	    StringBuilder salt = new StringBuilder();
		Random randomCharacter = new Random();
		
		// Create a for loop that will go through 8 iterations. Each
		// Iterated will designate a random value of the following 8
		// String arrays.
		
		String upperCaseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 	// 0 && 3 - UpperCase
		String lowerCaseString = upperCaseString.toLowerCase(); // 1 && 4 - LowerCase
		String specialCharacterString = "@$!%*#?&"; 			// 2 && 5 - Special Character
		String numberString = "1234567890"; 					// 3 && 6 - Number
		
		String[] currentString = new String[100];
		
		for(int outerCounter = 0; outerCounter < 100; outerCounter++) {
			// Iterate 8 times and select two random characters of each type
			for(int counter = 0; counter < 16; counter++) {
				
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
			currentString[outerCounter] = salt.toString();
			
			String encryptedPassword = this.passwordEncryption.hashPassword(currentString[outerCounter].toCharArray(), numberString);
			
			// Assert that the password encryption length is 128 characters.
			assertEquals(encryptedPassword.length(), 128);
			
			salt.setLength(0);
		}
	}

}
