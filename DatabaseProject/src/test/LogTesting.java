package test;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.junit.jupiter.api.Test;

import databaseproject.AdministratorFunctions;
import databaseproject.CSVOperations;
import databaseproject.ConfigurationOperations;
import databaseproject.InputOperations;
import databaseproject.LoginOperations;
import databaseproject.PanelAdminAddUser;
import databaseproject.PanelCentral;
import databaseproject.PasswordEncryption;
import databaseproject.ProgramLogs;

class LogTesting {
	AdministratorFunctions administratorFunctions = new AdministratorFunctions();
	PanelCentral panelCentral = new PanelCentral();
	PanelAdminAddUser panelAdminAddUser = new PanelAdminAddUser(administratorFunctions, panelCentral) ;
	
	@Test
	void test() {
		panelCentral.setCurrentPanelString(this.panelCentral.PANEL_LOGIN);
	}

}
