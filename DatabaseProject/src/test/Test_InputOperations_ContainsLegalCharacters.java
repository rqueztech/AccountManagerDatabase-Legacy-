/*
 	TEST Complete.
 	Returns TRUE if legalCharacaterArray contains legal characters.
	This test is for the legal characters entered function. This function is used
	generally for all input that is ingested into the database (CSV file for this
	prototype). A separate test is used for minimum password requirements.
*/

package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Test_InputOperations_ContainsLegalCharacters {
	InputOperations inputOperations = new InputOperations();
	
	@Test
	void testContainsLegalCharacters() {
		// The only legal characters allowed are: @$!%*#?&
		
		// Test for legal passwords
		String[] legalCharacterArray = {"Jacov7&", "Paco7&#?", "Ray%7#%&", "Charlie&7", "Bacon4#", "Charles@7", 
				"Char12341234314325123@#&", "People@!45"};
		
		for(String iterator : legalCharacterArray) {
			boolean legalCharacters = inputOperations.containsLegalCharacters(iterator);
			
			// Illegal character detector returned false
			assertTrue(legalCharacters);
		}
		
		// Test to see if illegal characters are found
		String[] illegalCharacterArray = {"Jac=ov7&", "Pac(o7&", "Ray)7&", "Charl[ie&7", "Ba]con4#", "Cha{rles@7", 
				"Char123412343}14325123_@./#&+-", "Charl'es&7", "Char=la73@", "People!_@/.45",
				"apwoeigjaweg-2fF-3='''''", "fepo=wijeg@#%#woeWEOF", "Ch&7 "};
		
		// Iterate through the String[] illegalCharacterArray
		for(String iterator : illegalCharacterArray) {
			
			// Returns true if illegal characters were entered, false if they were not
			boolean legalCharacters = inputOperations.containsLegalCharacters(iterator);
			assertFalse(legalCharacters);
		}
	}
}
