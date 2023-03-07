package databaseproject;

import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

class InitialConfigurationWorker extends SwingWorker<Boolean, Void> {
	
	private AdministratorFunctions administratorFunctions;
	public ConfigurationOperations configurationOperations;
	private InputOperations inputOperations;
	private PanelCentral panelCentral;
	
	
	private char[] adminPassphrase;
	private char[] adminPassword;
	private char[] adminPasswordReentered;
	private String adminFirstName;
	private String adminLastName;

	//-----------------------------------------------------------------------------------
	InitialConfigurationWorker(PanelCentral panelCentral, String adminFirstName, String adminLastName, 
			char[] adminPassphrase, char[] adminPassword, char[] adminPasswordReentered) {
		this.panelCentral = panelCentral;
		
		this.adminPassphrase = adminPassphrase;
		this.adminPassword = adminPassword;
		this.adminFirstName = adminFirstName;
		this.adminLastName = adminLastName;
		this.adminPasswordReentered = adminPasswordReentered;
	}
	
	//-----------------------------------------------------------------------------------
	@Override
	protected Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub
		this.administratorFunctions = new AdministratorFunctions(this.panelCentral);

		this.configurationOperations = administratorFunctions.configurationOperations;
		this.inputOperations = administratorFunctions.inputOperations;
		
		StringBuilder inputErrorMessage = new StringBuilder();
		
		// Validations to check no numbers or strange symbols are inserted into
		// The first name and last name Fields
		// --------------------------------------------------------------------------
		if (!this.inputOperations.isOnlyLetterCharacters(adminFirstName).isEmpty()) {
			inputErrorMessage.append(String.format("FirstName %s%n", this.inputOperations.isOnlyLetterCharacters(adminFirstName)));		
		}
		
		if (!this.inputOperations.isOnlyLetterCharacters(adminLastName).isEmpty()) {
			inputErrorMessage.append(String.format("LastName %s%n", this.inputOperations.isOnlyLetterCharacters(adminLastName)));		
		}
		
		// Validations to ensure all password requirements are met
		// --------------------------------------------------------------------------
		if (!this.inputOperations.isMeetsPasswordRequirements(adminPassphrase).isEmpty()) {
			inputErrorMessage.append(String.format("Passphrase %s%n", this.inputOperations.isMeetsPasswordRequirements(adminPassphrase)));
			inputErrorMessage.append(specificFeedback(adminPassphrase, "Passphrase "));
		}
		
		if (!this.inputOperations.isMeetsPasswordRequirements(adminPassword).isEmpty()) {
			inputErrorMessage.append(String.format("Password %s%n", this.inputOperations.isMeetsPasswordRequirements(adminPassword)));
			inputErrorMessage.append(specificFeedback(adminPassword, "Password "));
		}
		
		if (!this.inputOperations.isMeetsPasswordRequirements(adminPasswordReentered).isEmpty()) {
			inputErrorMessage.append(String.format("Password Reentry%s%n ", this.inputOperations.isMeetsPasswordRequirements(adminPasswordReentered)));
			inputErrorMessage.append(specificFeedback(adminPasswordReentered, "Reentered Password "));
		}
		
		if(inputErrorMessage.isEmpty()) {
			completeConfigurationChanges();
			return true;
		}
			
		else {
			JOptionPane.showMessageDialog(null, inputErrorMessage.toString());
		}
		
		return false;
	}
	
	//-----------------------------------------------------------------------------------
	@Override
	protected void done() {
		try {
			boolean success = get();
			if(success) {
				JOptionPane.showMessageDialog(null, "Admin Name:\n" + "\nConfiguration Success...\nNow You Can Customize Your Database", "SUCCESS!!!", JOptionPane.INFORMATION_MESSAGE);
				new PanelCentral();
			} else {
				JOptionPane.showMessageDialog(null, "Configuration Failed");
			}
		} catch (InterruptedException | ExecutionException e) {
			JOptionPane.showMessageDialog(null, "Fatal Error, shutting down...");
		}
	}
	
	//-----------------------------------------------------------------------------------
	public String specificFeedback(char[] currentInput, String currentSearch) {
		StringBuilder specificMessage = new StringBuilder();
		
		// Create an array that will hold specific error messages
		// Such as no lower case, no upper case, no characters, and no numbers.
		String[][] checks = {
			{currentSearch, this.inputOperations.isNoLowerCaseCharacters(String.valueOf(currentInput))},
			{currentSearch, this.inputOperations.isNoUpperCaseCharacters(String.valueOf(currentInput))},
			{currentSearch, this.inputOperations.isNoSpecialCharacters(String.valueOf(currentInput))},
			{currentSearch, this.inputOperations.isNoNumbersFound(String.valueOf(currentInput))}
		};
		
		// Iterate through every element of 2D Array. If the message is not empty,
		// Then append the message to the current messages.
		for(int counter = 0; counter < checks.length; counter++) {
			if(!checks[counter][1].isEmpty()) {
				specificMessage.append(String.format("%s%n", checks[counter][1]));
			}
		}
		
		return specificMessage.toString();
	}
	
	//-----------------------------------------------------------------------------------
	void completeConfigurationChanges() {
		// Initialize every essential file that will be written into
		// In the program
		this.administratorFunctions.csvOperations.initializeEssentialFiles();
		
		// Create the initial configuration file
		this.administratorFunctions.configurationOperations.createAdministrativePassphrase(this.adminPassphrase);			
		this.administratorFunctions.csvOperations.configurationCSVOperations.overwriteConfigFile();
		
		// Create the initial administrator file
		this.administratorFunctions.createInitialAdmin(this.adminFirstName, this.adminLastName, this.adminPassword);
		this.administratorFunctions.csvOperations.adminCSVOperations.overwriteAdminFile();
		
		// Initialize File Read to ensure all hashmaps are properly up to date
		this.administratorFunctions.csvOperations.initializeInitialFileRead();
	}
}