/*
	TEST COMPLETE
	AssertEquals will test the new randomly generated password to make sure
	it meets the proper password requirements for a default password. The
	password generated must meet all requirements for the password
*/

package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import databaseproject.AdministratorFunctions;
import databaseproject.EmployeeNode;
import databaseproject.InputOperations;
import databaseproject.PasswordEncryption;

class Test_AdministratorFunctions_generateDefaultUserPassword {
	public AdministratorFunctions administratorFunctions = new AdministratorFunctions();
	public EmployeeNode empNode = new EmployeeNode();
	public InputOperations inputOperations = new InputOperations();
	public PasswordEncryption pEnc = new PasswordEncryption();
	
	@Test
	void test() {
		boolean testPassed = true;
		
		final String upperCase = "abcdefghijklmnopqrstuvwxyz";
		
		Random rand = new Random(26);
		
		String[] randomNames = new String[600];
		
		// The outer loop will iterate through the randomNames array (a number of 600 times due to the current size)
		for(int positionCounterNames = 0; positionCounterNames < randomNames.length; positionCounterNames++) {
			String currentRandomName = "";
			
			// Will iterate 8 times, each time will place a character in a new randomName String
			for(int randomPasswordCounter = 0; randomPasswordCounter < 8; randomPasswordCounter++) {
				
				// Create an integer that will capture the current randomized position
				int currentCharacterPosition = rand.nextInt(26);
				
				if(randomPasswordCounter == 0) {
					currentRandomName += upperCase.substring(currentCharacterPosition, currentCharacterPosition + 1).toUpperCase();
				}
				
				else {
					currentRandomName += upperCase.substring(currentCharacterPosition, currentCharacterPosition + 1);
				}
			}
			
			randomNames[positionCounterNames] = currentRandomName;
			
			String currentPassword = this.administratorFunctions.generateDefaultUserPassword(randomNames[positionCounterNames]);
			
			System.out.println(currentPassword);
		}
		
		// Iterate through random generated passwords
		for(int counter = 1; counter <randomNames.length; counter++) {
			String newPassword = this.administratorFunctions.generateDefaultUserPassword("Ricardo");
			
			testPassed = inputOperations.isMeetsPasswordRequirements(newPassword);
			
			// If testPassed is set to false, an error message will be printed
			// before asserEquals terminates the program due to failure
			if(!testPassed) {
				System.out.println("****************************************************");
				System.out.println("FAIL: AdministratorFunctions_GenerateNewUserPassword\nRandomly generated password did not meet password requirements: " + newPassword);
				System.out.println("");
			}
			
			// Assert that the new generated password passed the password requirements test
			assertEquals(true, testPassed);
		}
		
		System.out.println("****************************************************");
		System.out.println("PASS: AdministratorFunctions_GenerateNewUserPassword\nRandomly generated passwords met all password requirements");
		System.out.println("");
	}
}
