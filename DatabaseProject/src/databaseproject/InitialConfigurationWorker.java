package databaseproject;

import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

class InitialConfigurationWorker extends SwingWorker<Boolean, Void> {
	
	public AdministratorFunctions administratorFunctions;
	public PanelCentral panelCentral;
	public ProgramLogs programLogs;
	public ConfigurationOperations configurationOperations;
	
	private char[] adminPassphrase;
	private String adminFirstName;
	private String adminLastName;
	private String adminPassword;
	
	public InitialConfigurationWorker(PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
		this.administratorFunctions = new AdministratorFunctions(this.panelCentral);
		this.programLogs = panelCentral.programLogs;
		this.configurationOperations = administratorFunctions.configurationOperations;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		
		this.isAdministratorPassphraseExists();
		result = this.isAdministratorAccountExists();
		
		return result;
	}
	
	@Override
	protected void done() {
		try {
			boolean success = get();
			if(success) {
				JOptionPane.showMessageDialog(null, "Configuration Success");
				
			} else {
				JOptionPane.showMessageDialog(null, "Configuration Failed");
			}
		} catch (InterruptedException | ExecutionException e) {
			JOptionPane.showMessageDialog(null, "Fatal Error, shutting down...");
		}
	}
	
	//-----------------------------------------------------------------------------------
	public boolean isAdministratorPassphraseExists() {
		//this.adminPassphrase = this.configurationOperations.getAdminPassphrase().toCharArray();
		boolean meetsPasswordRequirements = false;
		
		// If the administrator passphrase returns with a
		do {
			
			this.setAdminPassphrase(JOptionPane.showInputDialog(null, "Enter Admin Passphrase:\nPlease Enter An Admin Passphrase", 
					"Passphrase", JOptionPane.INFORMATION_MESSAGE).toCharArray());
			
			if(this.getAdminPassphrase() == null) {
				JOptionPane.showMessageDialog(null, "Pasphrase Cancelled, EOP...", "Passphrase Operation", JOptionPane.ERROR_MESSAGE);
				
				this.programLogs.logCurrentEvent("ADMIN", 
						"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_FAILED());
				
				System.exit(0);
				return false;
			}
			
			meetsPasswordRequirements = this.administratorFunctions.inputOperations.isMeetsPasswordRequirements(new String(this.getAdminPassphrase()));
			
		} while(!meetsPasswordRequirements);
		
	
		
		return true;
	}
	
	public boolean isAdministratorAccountExists() {
		boolean result = this.administratorFunctions.csvOperations.isAdminExists;
		
		if(result) {
			return true;
		}
		
		else if(!result) {
			JOptionPane.showMessageDialog(null, "Must Create User Account");
			this.adminFirstName = "";
			this.adminLastName = "";
			
			
			this.adminFirstName = JOptionPane.showInputDialog(null, "Enter Admin First Name");
			
			if(this.adminFirstName != null) {
				while(this.adminFirstName != null && this.adminFirstName.length() <= 3
					|| !this.administratorFunctions.inputOperations.isOnlyLetterCharacters(this.adminFirstName)) {
					this.adminFirstName = JOptionPane.showInputDialog(null, "Enter Admin First Name (Must Only Contain Letters)", "Alphabet Characters Only!!!", JOptionPane.ERROR_MESSAGE);
				}
				
				this.adminLastName = JOptionPane.showInputDialog(null, "Enter Admin Last Name");
				while(this.adminFirstName != null && this.adminLastName.length() <= 3
					|| !this.administratorFunctions.inputOperations.isOnlyLetterCharacters(this.adminLastName)) {	
					this.adminLastName = JOptionPane.showInputDialog(null, "Enter Admin Last Name (Must Only Contain Letters)", "Alphabet Characters Only!!!", JOptionPane.ERROR_MESSAGE);
				}
				
				this.adminPassword = JOptionPane.showInputDialog(null, "Enter Admin Password");
				
				while(this.adminFirstName != null && this.adminPassword.length() <= 3
					|| !this.administratorFunctions.inputOperations.isMeetsPasswordRequirements(this.adminPassword)) {
					
					this.adminPassword = JOptionPane.showInputDialog(null, "Enter Valid Password:"
							+ "\n8 Char Minimum"
							+ "\n1 Number"
							+ "\n1 Special Symbol", 
							"Alphabet Characters Only!!!", 
							JOptionPane.ERROR_MESSAGE);
				}
				
				if(this.adminFirstName != null
				&& this.adminLastName != null 
				&& this.adminPassword != null) {
					this.saveConfigurationChanges();
					result = true;
				}
			}
				
			else {
				JOptionPane.showMessageDialog(null, "Must Create User... Please Restart Configuration", "Exit", JOptionPane.ERROR_MESSAGE);
				
				this.programLogs.logCurrentEvent("ADMIN", 
						"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_FAILED());
				
				System.exit(0);
			}
		}
		
		return result;
	}
	
	//-----------------------------------------------------------------------------------
	public void saveConfigurationChanges() {
		
		// Create the new admin here
		boolean isAdminCreated = this.administratorFunctions.createInitialAdmin(this.adminFirstName, this.adminLastName, this.adminPassword.toCharArray());
		
		if(isAdminCreated) {
			this.administratorFunctions.configurationOperations.createAdministrativePassphrase(this.getAdminPassphrase());			
			this.administratorFunctions.csvOperations.initializeEssentialFiles();
			
			this.administratorFunctions.csvOperations.overwriteConfigFile();
			this.administratorFunctions.csvOperations.overwriteAdminFile();
			
			this.programLogs.logCurrentEvent("ADMIN", 
					"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_SUCCESS());
			
			this.administratorFunctions.csvOperations.initializeInitialFileRead();
			
			JOptionPane.showMessageDialog(null, "Admin Name:\n" + "\nConfiguration Success...\nNow You Can Customize Your Database", "SUCCESS!!!", JOptionPane.INFORMATION_MESSAGE);
			new PanelCentral();
		}
	}
	
	public char[] getAdminPassphrase() {
		return adminPassphrase;
	}

	public void setAdminPassphrase(char[] adminPassphrase) {
		this.adminPassphrase = adminPassphrase;
	}

	public String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
}