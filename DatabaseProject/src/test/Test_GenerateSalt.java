/*
 * Test Case Completed. 
 * Number of Assertions: 6
 * 1. Salts generated are 128 characters long
 * 2. Salts generated are not equal to the previous salt
 * 3. 
 * 4. 
 * 5. 
 * 6. 
 */


package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class Test_GenerateSalt {
	private PasswordEncryption passwordEncryption = new PasswordEncryption();
	
	@Test
	void test() {
		// Create 100 salted strings, each will be 128 characters in length
		//String[] saltStrings = new String[100];
		List <String> saltStrings = new ArrayList<String>();
		
		Pattern specialCharacterPattern = Pattern.compile("^[@$!%*#?&]+$");
		Matcher matcher;
		
		for(int counter = 0; counter < 100; counter++) {
			saltStrings.add(passwordEncryption.generateSalt());
			
			// The salt is 128 characters in length
			assertEquals(128, saltStrings.get(counter).length());

			for(int innerCounter = 0; innerCounter < 128; innerCounter++) {
				char currentCharacter = saltStrings.get(counter).charAt(innerCounter);
				
				switch(innerCounter%4) {
					case 0:
						// Current Character is uppercase
						assertTrue(Character.isUpperCase(currentCharacter));

						break;
						
					case 1:
						// Current Character is lowercase
						assertTrue(Character.isLowerCase(currentCharacter));

						break;
						
					case 2:
						// Current Character is a special symbol
						matcher = specialCharacterPattern.matcher(String.valueOf(currentCharacter));
						boolean thirdTestPassed = matcher.find();
						assertTrue(thirdTestPassed);

						break;
						
					case 3:
						// Churrent character is a digit
						assertEquals(true, Character.isDigit(currentCharacter));
						
						break;
						
					default:
						break;
				}
			}
		}
		
		// Keep string outside of for loop to prevent constant initialization of a new string object
		String currentString = "";
		
		// List will ensure that the value never repeats
		for(int duplicatesCounter = 0; duplicatesCounter < saltStrings.size(); duplicatesCounter++) {
			currentString = saltStrings.get(duplicatesCounter);
			
			// Ensure that Collections.frequency only appears once in the list
			assertEquals(Collections.frequency(saltStrings, currentString) == 1, true);
			
		}
	}

}
