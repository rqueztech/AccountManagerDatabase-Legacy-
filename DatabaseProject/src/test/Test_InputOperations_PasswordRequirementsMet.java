/*
	TEST COMPLETE:
	Password Requirements Test met ensures that every password that is accepted
	meets the minimum requirements check (at least 8 characters, one special character,
	and one number). Test Passes when passwords given to it said requirements.
*/

package test;

import static org.junit.jupiter.api.Assertions.*;
import databaseproject.InputOperations;
import org.junit.jupiter.api.Test;

class Test_InputOperations_PasswordRequirementsMet {

	@Test
	void test() {
		// The only legal characters allowed are: @$!%*#?&
		InputOperations inputOperations = new InputOperations();
		
		// This array contains legal tested passwords, which will all return true
		String[] legalTestPasswords = {"Charles7&", "People2@%", "1qaz2wsx!QAZ@WSX", "Jarkpatark5tgb6yhn@$", "Plart6@@@", 
				"Park4@z!%*#?&", "2wsx3edc!QAZ@WSX", "$RFV%TGB4rfv5tgb", "People&@6"};
		
		boolean testPassed = true;
		
		// Iterate through the String[] legalTestPasswords
		for(String iterator : legalTestPasswords) {
			
			// Returns true if the password passed through it meets all legal requirements
			boolean passwordMeetsRequirements = inputOperations.isMeetsPasswordRequirements(iterator);
			
			// If passwordMeetsRequirements is false, then print out the illegal password
			// Accepted message
			if(!passwordMeetsRequirements) {
				System.out.println("****************************************************");
				System.out.println("FAIL: InputOperations_PasswordRequirementsMetTest:\n" 
						+ "Following Illegal Password Accepted: " + iterator);
				System.out.println("");
			}
			
			assertEquals(true, passwordMeetsRequirements);
		}
		
		if(testPassed) {
			System.out.println("****************************************************");
 			System.out.println("PASS: InputOperations_PasswordRequirementsMetTest:\nAll legal passwords were accepted");
 			System.out.println("");
		}
	}

}
