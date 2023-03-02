/*
	TEST COMPLETE:
	Password Requirements Test met ensures that every password that is accepted
	meets the minimum requirements check (at least 8 characters, one special character,
	and one number). Test Passes when passwords given to it said requirements.
*/

package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class Test_InputOperations_PasswordRequirementsMet {

	@Test
	void test() {
		// The only legal characters allowed are: @$!%*#?&
		InputOperations inputOperations = new InputOperations();
		
		// This array contains legal tested passwords, which will all return true
		char[][] legalTestPasswords = {
			    {'C', 'h', 'a', 'r', 'l', 'e', 's', '7', '&'},
			    {'P', 'e', 'o', 'p', 'l', 'e', '2', '@', '%'},
			    {'1', 'q', 'a', 'z', '2', 'w', 's', 'x', '!', 'Q', 'A', 'Z', '@', 'W', 'S', 'X'},
			    {'J', 'a', 'r', 'k', 'p', 'a', 't', 'a', 'r', 'k', '5', 't', 'g', 'b', '6', 'y', 'h', 'n', '@', '$'},
			    {'P', 'l', 'a', 'r', 't', '6', '@', '@', '@'},
			    {'P', 'a', 'r', 'k', '4', '@', 'z', '!', '%', '*', '#', '?', '&'},
			    {'2', 'w', 's', 'x', '3', 'e', 'd', 'c', '!', 'Q', 'A', 'Z', '@', 'W', 'S', 'X'},
			    {'$', 'R', 'F', 'V', '%', 'T', 'G', 'B', '4', 'r', 'f', 'v', '5', 't', 'g', 'b'},
			    {'P', 'e', 'o', 'p', 'l', 'e', '&', '@', '6'}
			};

		// Iterate through the String[] legalTestPasswords
		for(char[] iterator : legalTestPasswords) {
			
			// Returns true if the password passed through it meets all legal requirements
			boolean passwordMeetsRequirements = inputOperations.isMeetsPasswordRequirements(iterator);
			assertEquals(true, passwordMeetsRequirements);
		}
		
		// This array contains illegal tested passwords, which will all return true
		char[][] illegalTestPasswords = {
				{'1', 'q', 'a', 'z', '-', '2', 'w', 's', 'x', '!', 'Q', 'A', 'Z', '@', 'W', 'S', 'X' },
				{'J', 'a', 'r', 'k', 'p', 'a', '^', 't', 'a', 'r', 'k', '5', 't', 'g', 'b', '6', 'y', 'h', 'n', '@', '$' },
				{'P', 'l', 'a', 'r', 't', ')', '6', '@', '@', '@' },
				{'P', 'a', 'r', 'k', '(', '4', '@', '$', '!', '%', '*', '#', '?', '&' },
				{'2', 'w', 's', 'x', '3', 'e', 'd', 'c', '!', '/', 'Q', 'A', 'Z', '@', 'W', 'S', 'X' },
				{'P', 'e', 'o', 'p', '4', '#' },
				{'p', 'e', 'o', 'l', '\'','e', '&', '#', '4' },
				{'$', 'R', '\'', '\"', 'F', 'V', '%', 'T', 'G', 'B', '4', 'r', 'f', 'v', '5', 't', 'g', 'b' },
				{'C', 'h', 'a', 'r', 'l', 'e', 's', '7', '&', '~' },
				{'C', 'h', 'a', 'r', 'l', 'e', 's', '7', '&', '~', '`'} 
		};
		
		// Iterate through the String[] illegalTestPasswords
		for(char[] iterator : illegalTestPasswords) {
			
			// Returns true if the password passed through it meets all illegal requirements
			boolean passwordMeetsRequirements = inputOperations.isMeetsPasswordRequirements(iterator);
			
			// Assert illegal characters are found
			assertFalse(passwordMeetsRequirements);
		}
		
		char[][] passwordsTooShort = {
				{'1', 'q', 'a', 'z'},
				{'J', 'a', 'r', 'k'},
				{'P', 'l', 'a', 'r'},
				{'P', 'a', 'r', 'k'},
				{'2', 'w', 's', 'x'},
				{'P', 'e', 'o', 'p'},
				{'p', 'e', 'o', 'l'},
				{'$', 'R', '\'', '\"'},
				{'C', 'h', 'a', 'r'},
				{'s', '7', '&', '~', '`'}
		};
		
		// Iterate through the String[] legalTestPasswords
		for(char[] iterator : passwordsTooShort) {
			
			// Returns true if the password passed through it meets all legal requirements
			boolean passwordMeetsRequirements = inputOperations.isMeetsPasswordRequirements(iterator);
			
			// Assert the password is too short, therefore returning false
			assertFalse(passwordMeetsRequirements);
		}
	}
}
