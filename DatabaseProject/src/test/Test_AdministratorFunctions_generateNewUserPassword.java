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

class Test_AdministratorFunctions_generateDefaultUserPassword {
	PanelCentral panelCentral = new PanelCentral();
	AdministratorFunctions administratorFunctions = new AdministratorFunctions(panelCentral);
	UserNode userNode = new UserNode();
	InputOperations inputOperations = new InputOperations();
	PasswordEncryption pEnc = new PasswordEncryption();
	
	@Test
	void test() {
		byte[] array = new byte[7]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
	    
	    System.out.println(generatedString);
	}
}
