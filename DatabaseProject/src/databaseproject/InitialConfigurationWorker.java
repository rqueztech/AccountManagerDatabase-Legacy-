package databaseproject;

import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.plaf.synth.SynthProgressBarUI;

class InitialConfigurationWorker extends SwingWorker<Boolean, Void> {
	
	private AdministratorFunctions administratorFunctions;
	public ConfigurationOperations configurationOperations;
	private InputOperations inputOperations;
	private PanelCentral panelCentral;
	private ProgramLogs programLogs;
	
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
	
	@Override
	protected Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub
		this.administratorFunctions = new AdministratorFunctions(this.panelCentral);
		this.programLogs = panelCentral.programLogs;
		this.configurationOperations = administratorFunctions.configurationOperations;
		this.inputOperations = administratorFunctions.inputOperations;
		
		StringBuilder inputErrorMessage = new StringBuilder();
		
		// 
		if (!this.inputOperations.isOnlyLetterCharacters(adminFirstName).isEmpty()) {
			inputErrorMessage.append(String.format("FirstName %s%n", this.inputOperations.isOnlyLetterCharacters(adminFirstName)));		
		}
		
		if (!this.inputOperations.isOnlyLetterCharacters(adminLastName).isEmpty()) {
			inputErrorMessage.append(String.format("LastName %s%n", this.inputOperations.isOnlyLetterCharacters(adminLastName)));		
		}
		
		if (!this.inputOperations.isMeetsPasswordRequirements(adminPassphrase).isEmpty()) {
			inputErrorMessage.append(String.format("Passphrase %s%n", this.inputOperations.isMeetsPasswordRequirements(adminPassphrase)));
		}
		
		if (!this.inputOperations.isMeetsPasswordRequirements(adminPassword).isEmpty()) {
			inputErrorMessage.append(String.format("Password %s%n", this.inputOperations.isMeetsPasswordRequirements(adminPassword)));
			inputErrorMessage.append(specificFeedback(adminPassword));
		}
		
		if (!this.inputOperations.isMeetsPasswordRequirements(adminPasswordReentered).isEmpty()) {
			inputErrorMessage.append(String.format("Password Reentry%s%n ", this.inputOperations.isMeetsPasswordRequirements(adminPasswordReentered)));
			inputErrorMessage.append(specificFeedback(adminPasswordReentered));
		}
		
		String people = inputErrorMessage.toString();
		
		JOptionPane.showMessageDialog(null, inputErrorMessage.toString());
		
		return true;
	}
	
	public String specificFeedback(char[] currentInput) {
		StringBuilder specificMessage = new StringBuilder();
		
		String noLowerCaseFoundMessage = this.inputOperations.isNoLowerCaseCharacters(String.valueOf(currentInput));
		
		if(!noLowerCaseFoundMessage.isEmpty()) {
			specificMessage.append(String.format("%s%n", noLowerCaseFoundMessage));
		}
		
		String noUpperCaseFoundMessage = this.inputOperations.isNoUpperCaseCharacters(String.valueOf(currentInput));
		
		if(!noUpperCaseFoundMessage.isEmpty()) {
			specificMessage.append(String.format("%s%n", noUpperCaseFoundMessage));
		}
		
		String noSpecialCharactersFoundMessage = this.inputOperations.isNoSpecialCharacters(String.valueOf(currentInput));
		
		if(!noSpecialCharactersFoundMessage.isEmpty()) {
			specificMessage.append(String.format("%s%n", noSpecialCharactersFoundMessage));
		}
		
		String noNumbersFoundMessage = this.inputOperations.isNoNumbersFound(String.valueOf(currentInput));
		
		if(!noNumbersFoundMessage.isEmpty()) {
			specificMessage.append(String.format("%s%n", noNumbersFoundMessage));
		}
		
		return specificMessage.toString();
	}
	
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
	boolean isAdministratorAccountExists() {
		boolean result = this.administratorFunctions.csvOperations.adminCSVOperations.isAdminExists;
		
		if(result) {
			return true;
		}
		
		return false;
	}
	
	//-----------------------------------------------------------------------------------
	void saveConfigurationChanges() {
		
		// Create the new admin here
		boolean isAdminCreated = this.administratorFunctions.createInitialAdmin(this.adminFirstName, this.adminLastName, this.adminPassword);
		
		if(isAdminCreated) {
			this.administratorFunctions.csvOperations.initializeEssentialFiles();
			this.administratorFunctions.configurationOperations.createAdministrativePassphrase(this.adminPassphrase);			
			
			this.administratorFunctions.csvOperations.configurationCSVOperations.overwriteConfigFile();
			this.administratorFunctions.csvOperations.adminCSVOperations.overwriteAdminFile();
			
			this.programLogs.logCurrentEvent("ADMIN", 
					"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_SUCCESS());
			
			this.administratorFunctions.csvOperations.initializeInitialFileRead();
		}
	}
}