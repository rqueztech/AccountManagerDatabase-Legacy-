/*
	TEST COMPLETE
	The password entered by the user is properly accepted by the database.
	
	PRECONDITIONS:
	There must be no existing database in the system. This is testing against
	known default values. It will
	
	-Create a new database
	-Test known value
	
	REASONING:
	There is no possible way to know the end-user's passwords and what to test against
	on an active database (normal functions)
*/

package test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;

import databaseproject.AdministratorFunctions;
import databaseproject.CSVOperations;
import databaseproject.PanelCentral;
import databaseproject.PasswordEncryption;

class Test_CSVOperations_UserAuthenticationPassed {
	PanelCentral panelCentral = new PanelCentral();
	AdministratorFunctions administratorFunctions = new AdministratorFunctions(panelCentral);
	CSVOperations csvOperations = new CSVOperations(administratorFunctions);
	PasswordEncryption pEnc = new PasswordEncryption();
	
	@Test
	void test() throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Will create 10 new employees. We are not testing the output that will
		// be written into the database.csv file. Instead, we are concerned with
		// what will be written into the hashmap, which will be used to test password
		// encryption / login.
		this.administratorFunctions.csvOperations.initializeEssentialFiles();
		
		// Create two arrays (first and last)
		String firstName[] = {"Samantha", "Carley", "Shoko", "Christina", "Miyako", "Ricardo", "Charles", "Carl", "Taylor", "Takeshi"};
		String lastName[] = {"Charles", "Quezada", "Yamada", "Gutierrez", "Sanchez", "Quezada"};
		String gender[] = {"Female", "Female", "Female", "Female", "Female", "Male", "Male", "Male", "Male", "Male",};
		String typeOfUser[] = {"ADMIN", "ADMIN", "ADMIN", "ADMIN", "ADMIN", "USER", "USER", "USER", "USER", "USER"};
		
		this.administratorFunctions.csvOperations.initializeInitialFileRead();
		this.administratorFunctions.csvOperations.initializeInitialFileRead();
		
		for(int counter = 0; counter < firstName.length; counter++) {
			this.administratorFunctions.createNewEmployee(firstName[counter], lastName[counter], typeOfUser[counter] , gender[counter]);
			
			if(counter < 5) {
				System.out.println(
					
					firstName[counter].substring(0,1) + lastName[counter].substring(0,4)
				);
			}
			
			else {
				this.administratorFunctions.getEmployeeHashMap().get(firstName[counter].substring(0, 1) + lastName[counter].substring(0,4));
			}
		}
	}

}
