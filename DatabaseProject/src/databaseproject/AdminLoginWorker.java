package databaseproject;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

//This entire class creates the login panel. The login panel will prompt
//the user for the username and password.

class AdminLoginWorker extends SwingWorker<Boolean, Void> {
	private String adminName;
	private char[] adminPassword;
	public String finalMessage;
	private int numberOfAttemptsAdminPassphrase;
	
	private AdministratorFunctions administratorFunctions;
	private InputOperations inputOperations;
	private LoginOperations loginOperations;
	
	
	/**
	 * This class constructs the general information required to create an user
	 * in a separate thread to be passed in to the createUser function found in
	 * the AdministratorFunctions class
	 * @param firstName gets the first name of the admin to be added
	 * @param lastName gets the last name of the admin to be added
	 * @param gender gets the gender of the admin to be added
	 * @param administratorFunctions
	 */
	AdminLoginWorker(String adminName, char[] adminPassword, AdministratorFunctions administratorFunctions) {
		this.adminName = adminName;
		this.adminPassword = adminPassword;
		this.administratorFunctions = administratorFunctions;
		this.inputOperations = administratorFunctions.inputOperations;
		this.loginOperations = administratorFunctions.loginOperations;
		this.setNumberOfAttempts(this.numberOfAttemptsAdminPassphrase);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	protected Boolean doInBackground() throws Exception {
		//boolean isAdminPassphrase = false;
		boolean isLoginSuccessful = false;
		boolean isPasswordChangeSuccessful = false;
		
		boolean doesAdminExist = this.loginOperations.adminLoginOperations.searchAdmin(adminName);
		
		// If the administrator does not exist, return false and exit
		if(!doesAdminExist) {
			finalMessage = "Adminname/Password Incorrect";
			return false;
		}
		
		// If the password is not correct, return fail message
		if(!this.isPassphraseCorrect()) {
			finalMessage = "Incorrect Passphrase, number of attempts left: " + this.numberOfAttemptsAdminPassphrase;
			return false;
		}
		
		// Return true if the login is successful
		isLoginSuccessful = this.loginOperations.adminLoginOperations.isValidAdminLogin(adminName, adminPassword);
		
		// If the login is unsuccessful, return false
		if(!isLoginSuccessful) { 
			//*CREATE LOG - Login was unsuccessful
			finalMessage = "AdminName/Login Incorrect";
			return false; 
		}
		
		// *At this point, the login was successrul.
		// We will test to see if the default admin password is being used.
		boolean isDefaultAdminPassword = this.administratorFunctions.loginOperations.adminLoginOperations.checkDefaultAdminPassword(adminName, adminPassword);
		
		// If the default password is being used, prompt for entry. The prompt contains
		// A while loop that will keep checking until the user has entered the correct
		// Passphrase.
		if(isDefaultAdminPassword) {
			isPasswordChangeSuccessful = this.promptAdminForReentry();
		
			// If the password change is not successful, return false 
			// And end the program, do not proceed to changing.
			
			if(!isPasswordChangeSuccessful) { 
				finalMessage = "Admin Password Change Cancelled";
				return false;
			}
		}
		
		// If you have reached this point, congratulations, all changes
		// Have passed. The changed user password will be set in done function
		return true;
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	protected void done() {
		try {
			boolean success = get();
	   
			if (success) {
				this.administratorFunctions.loginOperations.setLogUserIn(this.adminName, true, "ADMIN");
				this.administratorFunctions.panelCentral.setCurrentPanelString(this.administratorFunctions.panelCentral.PANEL_ADMINCENTRAL);
				JOptionPane.showMessageDialog(null, "User Login Success");
			} 
			else {
				if(this.numberOfAttemptsAdminPassphrase == 0) { 
					//*CREATE LOG - Passphrase exceeded attempts
					JOptionPane.showMessageDialog(null, "Failed Attempts Exceeded... Shutting Down");
					System.exit(0); 
				}
				
				else {
					// Print out the final message in accordance to DRY principles
					JOptionPane.showMessageDialog(null, finalMessage);
				}
			}
		} 
			
		catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(null, "Error searching error.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// -----------------------------------------------------------------------------------
	void setNumberOfAttempts(int numberOfAttemptsAdminPassphrase) {
		this.numberOfAttemptsAdminPassphrase = numberOfAttemptsAdminPassphrase;
	}

	//-----------------------------------------------------------------------------------
	boolean isPassphraseCorrect() {
		boolean isAdminPassphrase = false;
		
		// ENTRY PASSPHRASE FOR THE USER
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		panel.setFocusable(true);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 10, 10, 10);
		panel.add(new JLabel("Enter Admin Passphrase"), constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(0, 10, 10, 10);
		JPasswordField passwordField = new JPasswordField();
		passwordField.setFocusable(true);
		panel.add(passwordField, constraints);
		
		int option = JOptionPane.showConfirmDialog(null, panel, "Authentication Required", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		char[] adminPassphrase = passwordField.getPassword();
		
		if(option == JOptionPane.OK_OPTION) {
			passwordField.requestFocusInWindow();
			isAdminPassphrase = this.loginOperations.adminLoginOperations.isAdminPassphrase(adminPassphrase);
		}
		
		return isAdminPassphrase;
	}
	
	// -----------------------------------------------------------------------------------
	boolean searchAdmin(String userName) {
		boolean result = false;
		
		// **LOG WILL BE PUT IN THIS FUNCTION
		if(this.administratorFunctions.databaseHashMaps.getAdminHashMap() != null 
		&& this.administratorFunctions.databaseHashMaps.getAdminHashMap().get(userName) != null) {
			result = true;
		}		
		
		else {
			System.out.println("MASSIVE ERROR: NO USER ENTERED");
		}
			
		return result;
	}

	//-----------------------------------------------------------------------------------
	boolean promptAdminForReentry() {
		boolean passwordsValidate = false;
		boolean passwordsMatch = false;
		
		JPanel panel = new JPanel(new GridBagLayout());
	    panel.setBackground(Color.BLACK);

	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.insets = new Insets(5, 5, 5, 5);
	    constraints.anchor = GridBagConstraints.WEST;

	    JPasswordField passwordField = new JPasswordField(10);
	    passwordField.setBackground(Color.WHITE);
	    passwordField.setForeground(Color.BLACK);
	    passwordField.setEchoChar(' ');
	    JLabel passwordLabel = new JLabel("Enter password:");
	    passwordLabel.setForeground(Color.WHITE);
	    constraints.gridx = 0;
	    constraints.gridy = 0;
	    panel.add(passwordLabel, constraints);
	    constraints.gridx = 1;
	    panel.add(passwordField, constraints);

	    JPasswordField confirmPasswordField = new JPasswordField(10);
	    confirmPasswordField.setBackground(Color.WHITE);
	    confirmPasswordField.setForeground(Color.BLACK);
	    confirmPasswordField.setEchoChar(' ');
	    
	    JLabel confirmPasswordLabel = new JLabel("Confirm password:");
	    confirmPasswordLabel.setForeground(Color.WHITE);
	    constraints.gridx = 0;
	    constraints.gridy = 1;
	    panel.add(confirmPasswordLabel, constraints);
	    constraints.gridx = 1;
	    panel.add(confirmPasswordField, constraints);

	    int result = JOptionPane.showConfirmDialog(null, panel, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon());

	    UIManager.put("OptionPane.backgroundColor", Color.BLACK);
	    UIManager.put("Panel.background", Color.BLACK);
		
		while (!passwordsValidate) {    
		    if (result == JOptionPane.OK_OPTION) {
		        char[] password = passwordField.getPassword();
		        char[] confirmPassword = confirmPasswordField.getPassword();
		        passwordsValidate = this.performPasswordValidations(password, confirmPassword);
		        
		        if(passwordsValidate && password.equals(confirmPassword)) {
		        	passwordsMatch = true;
		        	return passwordsMatch;
		        }
		    } else {
		        // Admin clicked cancel or closed the dialog
		    	//*CREATE LOG - User Admin Change Cancelled
		    	finalMessage = "Password Change Cancelled";
		    	return false;
		    }
		}
		
		return false;
	}

	//-----------------------------------------------------------------------------------
	void validateAdministratorCredentials() {
		this.administratorFunctions.databaseHashMaps.getAdminHashMap().get(adminName);
	}
	
	// Check to see if the passwords meet password validation
	boolean performPasswordValidations(char[] newPasswordEntered, char[] newPasswordReentered) {
		
		if(!this.inputOperations.isMeetsPasswordRequirements(newPasswordEntered).isEmpty()) {
			return false;
		}
			
		if(!this.inputOperations.isMeetsPasswordRequirements(newPasswordReentered).isEmpty()) {
			return false;
		}
		
		return true;
	}
}
