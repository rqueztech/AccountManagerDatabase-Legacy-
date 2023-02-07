package test;

import org.junit.jupiter.api.Test;

import databaseproject.AdministratorFunctions;
import databaseproject.PanelAdminAddUser;
import databaseproject.PanelCentral;

class AdministratorOperations_writeToConfigFile {
	PanelCentral panelCentral = new PanelCentral();
	AdministratorFunctions administratorFunctions = new AdministratorFunctions(panelCentral);
	PanelAdminAddUser panelAdminAddUser = new PanelAdminAddUser(administratorFunctions, panelCentral) ;
	
	@Test
	void test() {
		panelCentral.administratorFunctions.configurationOperations.createAdministrativePassphrase("Jacka$$3d");
	}

}
