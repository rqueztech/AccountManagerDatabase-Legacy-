/*
	Password Requirements Test met ensures that every password that is accepted
	meets the minimum requirements check (at least 8 characters, one special character,
	and one number). Test Passes when passwords given to it said requirements.
*/

package test;

import static org.junit.jupiter.api.Assertions.*;
import databaseproject.InputOperations;
import org.junit.jupiter.api.Test;

class Test_InputOperations_PasswordRequirementsNotMet {

	@Test
	void test() {
		// The only legal characters allowed are: @$!%*#?&
		
		InputOperations inputOperations = new InputOperations();
		
		// This array contains legal tested passwords, which will all return true
		String[] legalTestPasswords = {"Charle=s7&", "Peo+ple2@%", "1qaz-2wsx!QAZ@WSX", "Jarkpa^tark5tgb6yhn@$", "Plart)6@@@", "Park(4@$!%*#?&",
				"2wsx3edc!'QAZ@WSX", "Peop4#", "peol,e&#4", "$R\"FV%TGB4rfv5tgb", "Charles7&~", "Charles7&~`"};
		
		boolean testPassed = true;
		
		// Iterate through the String[] legalTestPasswords
		for(String iterator : legalTestPasswords) {
			
			// Returns true if the password passed through it meets all legal requirements
			boolean passwordMeetsRequirements = inputOperations.isMeetsPasswordRequirements(iterator);
			
			try {
				assertEquals(false, passwordMeetsRequirements);
			}
			
			catch (AssertionError e) {
				System.out.println("****************************************************");
				System.out.println("FAIL: InputOperations_PasswordRequirementsNotMet:\nIllegal password was accepted: " + iterator);
				System.out.println("");
				testPassed = false;
			}
			
			assertEquals(false, passwordMeetsRequirements);
		}
		
		if(testPassed) {
			System.out.println("****************************************************");
 			System.out.println("PASS: InputOperations_PasswordRequirementsNotMet:\nAll illegal passwords were rejected");
 			System.out.println("");
		}
	}

}
