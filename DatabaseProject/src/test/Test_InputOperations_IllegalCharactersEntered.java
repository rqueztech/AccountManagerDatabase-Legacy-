/*
	TEST COMPLETE
	Returns FALSE for illegalCharArray[] since all characters contain illegal characters
	The test will be conducted against Input Opeartions: legalCharactersEntered class.
	The test should return true if illegal characters are detected in the string entry.
*/

package test;

import static org.junit.jupiter.api.Assertions.*;
import databaseproject.InputOperations;
import org.junit.jupiter.api.Test;

class Test_InputOperations_IllegalCharactersEntered {
	InputOperations inputOperations = new InputOperations();
	
	@Test
	void test() {
		
		// List of illegal characters
		String[] illegalCharacterArray = {"Jac=ov7&", "Pac(o7&", "Ray)7&", "Charl[ie&7", "Ba]con4#", "Cha{rles@7", 
				"Char123412343}14325123_@./#&+-", "Charl'es&7", "Char=la73@", "People!_@/.45",
				"apwoeigjaweg-2fF-3='''''", "fepo=wijeg@#%#woeWEOF"};
		
		// Iterate through the String[] illegalCharacterArray
		for(String iterator : illegalCharacterArray) {
			
			// Returns true if illegal characters were entered, false if they were not
			boolean illegalCharacters = inputOperations.legalCharactersEntered(iterator);
			
			// If illegal characters are detected, print out fail message
			if(illegalCharacters) {
				System.out.println("****************************************************");
				System.out.println("FAIL: IllegalCharactersEnteredTest\nPassword Illegal Char Not Detected In: " + iterator);
				System.out.println("");
			}
			
			assertEquals(false, illegalCharacters);
		}
		
		System.out.println("****************************************************");
		System.out.println("PASS: InputOperations_IllegalCharactersEnteredTest:\nIllegal Characters in all passwords detected");
		System.out.println("");
		
	}

}
