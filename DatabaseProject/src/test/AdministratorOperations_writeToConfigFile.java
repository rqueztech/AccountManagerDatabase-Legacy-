package test;

import org.junit.jupiter.api.Test;

import databaseproject.AdministratorFunctions;
import databaseproject.EmployeeNode;
import databaseproject.InputOperations;
import databaseproject.PanelCentral;
import databaseproject.PasswordEncryption;

class AdministratorOperations_writeToConfigFile {
	
	public PanelCentral panelCentral = new PanelCentral();
	public AdministratorFunctions administratorFunctions = new AdministratorFunctions(panelCentral);
	public EmployeeNode empNode = new EmployeeNode();
	public InputOperations inputOperations = new InputOperations();
	public PasswordEncryption pEnc = new PasswordEncryption();
	
	@Test
	void test() {
		for(int counter = 0; counter < 500; counter++) {
			administratorFunctions.createNewEmployee("Ricardo", "Jarlie", "Male");
		}
	}

}
