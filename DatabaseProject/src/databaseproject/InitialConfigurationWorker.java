package databaseproject;

import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

class InitialConfigurationWorker extends SwingWorker<Boolean, Void> {
	
	private AdministratorFunctions administratorFunctions;
	public ConfigurationOperations configurationOperations;
	private InputOperations inputOperations;
	private PanelCentral panelCentral;
	private ProgramLogs programLogs;
	
	private char[] adminPassphrase;
	private char[] adminPassword;
	private String adminFirstName;
	private String adminLastName;

	//-----------------------------------------------------------------------------------
	InitialConfigurationWorker(PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub
		this.administratorFunctions = new AdministratorFunctions(this.panelCentral);
		this.programLogs = panelCentral.programLogs;
		this.configurationOperations = administratorFunctions.configurationOperations;
		this.inputOperations = administratorFunctions.inputOperations;
		
		boolean isAdministratorExists = this.isAdministratorAccountExists();
		boolean isAdminConfigurationSuccess = false;
		
		if(!isAdministratorExists) {
			this.setAdministratorPassphrase();
			JOptionPane.showMessageDialog(null, "Must Create Account");
			this.setAdminFirstName();
			this.setAdminLastName();
			isAdminConfigurationSuccess = this.setAdminPassword();
		}
		
		if(isAdminConfigurationSuccess) {
			if(this.adminFirstName != null
			&& this.adminLastName != null 
			&& this.adminPassword != null) {
				this.saveConfigurationChanges();
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Must Create Admin... Please Restart Configuration", "Exit", JOptionPane.ERROR_MESSAGE);
				
				this.programLogs.logCurrentEvent("ADMIN", 
						"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_FAILED());
				
				return false;
			}
		}
		
		return true;
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
	boolean setAdministratorPassphrase() {
		//this.adminPassphrase = this.configurationOperations.getAdminPassphrase().toCharArray();
		boolean meetsPasswordRequirements = false;
		
		// Prompt the user to enter the administrator passphrase
		this.setAdminPassphrase(JOptionPane.showInputDialog(null, "Enter Admin Passphrase:\nPlease Enter An Admin Passphrase", 
				"Passphrase", JOptionPane.INFORMATION_MESSAGE).toCharArray());
		
		// If the administrator passphrase returns with a
		do {
			
			if(!this.inputOperations.isMeetsPasswordRequirements(this.getAdminPassphrase())) {
				this.setAdminPassphrase(
					JOptionPane.showInputDialog(null, "Enter Valid Password", "Password Error", JOptionPane.WARNING_MESSAGE).toCharArray()
				);
			}
			
			
			if(this.getAdminPassphrase() == null) {
				JOptionPane.showMessageDialog(null, "Pasphrase Cancelled, EOP...", "Passphrase Operation", JOptionPane.ERROR_MESSAGE);
				
				this.programLogs.logCurrentEvent("ADMIN", 
						"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_FAILED());
				
				System.exit(0);
			}
			
			meetsPasswordRequirements = this.administratorFunctions.inputOperations.isMeetsPasswordRequirements(this.getAdminPassphrase());
			
		} while(!meetsPasswordRequirements);
		
		return true;
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
	private String validateNameInput(String fieldName) {
	    String inputValue = JOptionPane.showInputDialog(null, "Enter " + fieldName);
	    while (inputValue != null && inputValue.length() <= 3
	            || !this.administratorFunctions.inputOperations.isOnlyLetterCharacters(inputValue)) {
	        	inputValue = JOptionPane.showInputDialog(null, "Enter " + fieldName + " (Must Only Contain Letters)", "Alphabet Characters Only!!!", JOptionPane.ERROR_MESSAGE);
	    }
	    return inputValue;
	}

	//-----------------------------------------------------------------------------------
	void setAdminFirstName() {
	    this.adminFirstName = validateNameInput("Admin First Name");
	}

	//-----------------------------------------------------------------------------------
	void setAdminLastName() {
	    this.adminLastName = validateNameInput("Admin Last Name");
	}
	
	//-----------------------------------------------------------------------------------
	boolean setAdminPassword() {
		this.adminPassword = JOptionPane.showInputDialog(null, "Enter Admin Password").toCharArray();
		boolean isMeetsPasswordRequirements = this.administratorFunctions.inputOperations.isMeetsPasswordRequirements(this.adminPassword);
		
		while(!isMeetsPasswordRequirements) {
			
			this.adminPassword = JOptionPane.showInputDialog(null, "Enter Valid Password:"
					+ "\n8 Char Minimum"
					+ "\n1 Number"
					+ "\n1 Special Symbol", 
					"Alphabet Characters Only!!!", 
					JOptionPane.ERROR_MESSAGE).toCharArray();
			
			isMeetsPasswordRequirements = this.administratorFunctions.inputOperations.isMeetsPasswordRequirements(this.adminPassword);
		}
		
		return true;
	}
	
	//-----------------------------------------------------------------------------------
	void saveConfigurationChanges() {
		
		// Create the new admin here
		boolean isAdminCreated = this.administratorFunctions.createInitialAdmin(this.adminFirstName, this.adminLastName, this.adminPassword);
		
		if(isAdminCreated) {
			this.administratorFunctions.csvOperations.initializeEssentialFiles();
			this.administratorFunctions.configurationOperations.createAdministrativePassphrase(this.getAdminPassphrase());			
			
			this.administratorFunctions.csvOperations.configurationCSVOperations.overwriteConfigFile();
			this.administratorFunctions.csvOperations.adminCSVOperations.overwriteAdminFile();
			
			this.programLogs.logCurrentEvent("ADMIN", 
					"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_SUCCESS());
			
			this.administratorFunctions.csvOperations.initializeInitialFileRead();
		}
	}

	//-----------------------------------------------------------------------------------
	char[] getAdminPassphrase() {
		return adminPassphrase;
	}

	//-----------------------------------------------------------------------------------
	void setAdminPassphrase(char[] adminPassphrase) {
		this.adminPassphrase = adminPassphrase;
	}

	//-----------------------------------------------------------------------------------
	String getAdminFirstName() {
		return adminFirstName;
	}

	//-----------------------------------------------------------------------------------
	void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	//-----------------------------------------------------------------------------------
	String getAdminLastName() {
		return adminLastName;
	}

	//-----------------------------------------------------------------------------------
	void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}

	//-----------------------------------------------------------------------------------
	char[] getAdminPassword() {
		return this.adminPassword;
	}

	//-----------------------------------------------------------------------------------
	void setAdminPassword(char[] adminPassword) {
		this.adminPassword = adminPassword;
	}
}