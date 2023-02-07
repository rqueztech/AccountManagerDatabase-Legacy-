/*
 	TEST Complete.
 	Returns TRUE if legalCharacaterArray contains legal characters.
	This test is for the legal characters entered function. This function is used
	generally for all input that is ingested into the database (CSV file for this
	prototype). A separate test is used for minimum password requirements.
*/

package test;

import static org.junit.jupiter.api.Assertions.*;
import databaseproject.InputOperations;

import org.junit.jupiter.api.Test;

class Test_InputOperations_LegalCharactersEntered {
	InputOperations inputOperations = new InputOperations();
	
	@Test
	void test() {
		// The only legal characters allowed are: @$!%*#?&
		
		// List of legal characters
		String[] legalCharacterArray = {"Jacov7&", "Paco7&#?", "Ray%7#%&", "Charlie&7", "Bacon4#", "Charles@7", 
				"Char12341234314325123@#&", "People@!45"};
		
		boolean testPassed = true;
		
		for(String iterator : legalCharacterArray) {
			boolean legalCharacters = inputOperations.legalCharactersEntered(iterator);
			
			// Call the flag before the assertion. Once the assertion fails, the test will crash
			if(!legalCharacters) {
				testPassed = false;
				System.out.println("****************************************************");
				System.out.println("FAIL: InputOperations_LegalCharactersEntered:\nIllegal string " + iterator + " Accepted");
				System.out.println("");
			}
			
			// Illegal character detector returned false
			assertEquals(true, legalCharacters);
		}
		
		if(testPassed) {
			System.out.println("****************************************************");
			System.out.println("PASS: InputOperations_LegalCharactersEntered:\nAll legal passwords accepted");
			System.out.println("");
		}
	}
}
