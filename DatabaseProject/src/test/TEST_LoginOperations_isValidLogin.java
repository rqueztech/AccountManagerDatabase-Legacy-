package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import databaseproject.LoginOperations;
import databaseproject.PasswordEncryption;
import databaseproject.CSVOperations;
import databaseproject.AdministratorFunctions;

class TEST_LoginOperations_isValidLogin {
	public PasswordEncryption passwordEncryption = new PasswordEncryption();
	public AdministratorFunctions administratorFunctions = new AdministratorFunctions();
	public LoginOperations loginOperations = new LoginOperations(administratorFunctions, passwordEncryption);
	public CSVOperations csvOperations = new CSVOperations(administratorFunctions);
	
	@Test
	void test() {
		
		// 
		for(int counter = 0; counter < 10; counter++) {
			administratorFunctions.createNewEmployee("Ricardo", "Quezada");
		}
		
		csvOperations.readFromCSV();
		
		//System.out.println(administratorFunctions.getEmployeeHashMap());

		String userName = "rquez3";
		String attemptedPassword = "abcR5t6y%T^Y";
		
		assertEquals(true, loginOperations.isValidLogin(userName, attemptedPassword));
		
		System.out.println("****************************************************");
		System.out.println("PASS: LoginOperations_isValidLogin:\nLogin password used was successful");
		System.out.println("");

	}
}
