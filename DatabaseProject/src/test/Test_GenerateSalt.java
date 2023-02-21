// **TEST CASE COMPLETE

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import databaseproject.PasswordEncryption;

class Test_GenerateSalt {
	private PasswordEncryption passwordEncryption = new PasswordEncryption();
	
	@Test
	void test() {
		// Create 100 salted strings, each will be 32 characters in length
		String[] saltStrings = new String[100];
		
		Pattern specialCharacterPattern = Pattern.compile("^[@$!%*#?&]+$");
		Matcher matcher;
		
		for(int counter = 0; counter < 100; counter++) {
			saltStrings[counter] = passwordEncryption.generateSalt();
			
			// Assert that the current generated string is 32 characters in length
			assertEquals(32, saltStrings[counter].length());

			for(int innerCounter = 0; innerCounter < 32; innerCounter++) {
				char currentCharacter = saltStrings[counter].charAt(innerCounter);
				
				switch(innerCounter%4) {
					case 0:
						// Assert that the current character is uppercase
						assertTrue(Character.isUpperCase(currentCharacter));

						break;
						
					case 1:
						assertTrue(Character.isLowerCase(currentCharacter));

						break;
						
					case 2:
						matcher = specialCharacterPattern.matcher(String.valueOf(currentCharacter));
						boolean thirdTestPassed = matcher.find();
						assertTrue(thirdTestPassed);

						break;
						
					case 3:
						assertEquals(true, Character.isDigit(currentCharacter));
						
						break;
						
					default:
						break;
				}
			}
		}
	}

}
