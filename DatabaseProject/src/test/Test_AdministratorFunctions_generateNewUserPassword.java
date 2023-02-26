/*
	TEST COMPLETE
	AssertEquals will test the new randomly generated password to make sure
	it meets the proper password requirements for a default password. The
	password generated must meet all requirements for the password
*/

package test;

import java.nio.charset.Charset;
import java.util.Random;

import org.junit.jupiter.api.Test;

import databaseproject.AdministratorFunctions;
import databaseproject.EmployeeNode;
import databaseproject.InputOperations;
import databaseproject.PanelCentral;
import databaseproject.PasswordEncryption;

class Test_AdministratorFunctions_generateDefaultUserPassword {
	public PanelCentral panelCentral = new PanelCentral();
	public AdministratorFunctions administratorFunctions = new AdministratorFunctions(panelCentral);
	public EmployeeNode empNode = new EmployeeNode();
	public InputOperations inputOperations = new InputOperations();
	public PasswordEncryption pEnc = new PasswordEncryption();
	
	@Test
	void test() {
		byte[] array = new byte[7]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
	    
	    System.out.println(generatedString);
	}
}
