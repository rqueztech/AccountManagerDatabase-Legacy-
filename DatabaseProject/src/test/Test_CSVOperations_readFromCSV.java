package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import databaseproject.AdministratorFunctions;
import databaseproject.CSVOperations;
import databaseproject.PanelCentral;

class Test_CSVOperations_readFromCSV {
	AdministratorFunctions administratorFunctions = new AdministratorFunctions();
	CSVOperations csvOps = new CSVOperations(this.administratorFunctions);
	PanelCentral panelCentral = new PanelCentral();
	
	@Test
	void test() {
		this.csvOps.initializeEssentialFiles();
		this.csvOps.readFromAdminFile();
		
		boolean passesTest = false;
		
		System.out.println(this.administratorFunctions
				.getEmployeeHashMap().size());
		
		if(this.administratorFunctions.getEmployeeHashMap().size() != 0) {
			passesTest = true;
		}
		
		System.out.println(this.administratorFunctions.getEmployeeHashMap());
		assertEquals(true, passesTest);
	}

}
