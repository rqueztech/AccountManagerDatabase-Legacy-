package test;

import org.junit.jupiter.api.Test;

class LogTesting {
	PanelCentral panelCentral = new PanelCentral();
	AdministratorFunctions administratorFunctions = new AdministratorFunctions(panelCentral);
	AdminAddUserPanel panelAdminAddUser = new AdminAddUserPanel(administratorFunctions, panelCentral) ;
	
	@Test
	void test() {
		for(int counter = 0; counter < 100; counter++) {
			administratorFunctions.createNewAdmin("Ricardo", "Quezada");
			administratorFunctions.createNewUser("Charles", "Bronson", "Male");
		}
	}

}
