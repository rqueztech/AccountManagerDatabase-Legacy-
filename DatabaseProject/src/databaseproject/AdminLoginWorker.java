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
	private InputOperations inputOperations;
	private AdministratorFunctions administratorFunctions;
	private LoginOperations loginOperations;
	private String adminName;
	private char[] adminPassword;
	public String failMessage;
	private int numberOfAttempts;
	
	/**
	 * This class constructs the general information required to create an employee
	 * in a separate thread to be passed in to the createEmployee function found in
	 * the AdministratorFunctions class
	 * @param firstName gets the first name of the admin to be added
	 * @param lastName gets the last name of the admin to be added
	 * @param gender gets the gender of the admin to be added
	 * @param administratorFunctions
	 */
	public AdminLoginWorker(String adminName, char[] adminPassword, AdministratorFunctions administratorFunctions) {
		this.adminName = adminName;
		this.adminPassword = adminPassword;
		this.administratorFunctions = administratorFunctions;
		this.inputOperations = administratorFunctions.inputOperations;
		this.loginOperations = administratorFunctions.loginOperations;
		this.setNumberOfAttempts(this.numberOfAttempts);
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		boolean isLoginSuccessful = false;
		boolean isAdminPassphrase = false;
		
		boolean doesAdminExist = this.loginOperations.searchAdmin(adminName);
		//String message = performAdminLoginValidations();
		String message = performAdminLoginValidations();
		
		// If the administrator does not exist and there were no errors in the
		// Input, print error message and return false.
		if(!doesAdminExist) {
			JOptionPane.showMessageDialog(null, "Adminname/Password Incorrect");
			return false;
		}
		
		else if(doesAdminExist) {
			// Prompt the user to enter the administrator passphrase using JOptionPane
			char[] adminPassphrase = JOptionPane.showInputDialog(null, "Enter Admin Passphrase").toCharArray();
			
			// Pass the adminPassphrase in to the check admin passphrase function. This passphrase
			// Will use the salt for the current user on the new passphrase and match it to the admin's hashed
			// Passphrase. If it returns true, that means the user passphrase was entered correctly
			isAdminPassphrase = this.loginOperations.checkAdminPassphrase(adminPassphrase);
		}
		
		else if(!message.equals("")) {
			JOptionPane.showMessageDialog(null, message);
			return false;
		}
		
		// If the passphrase was not the correct passphrase, end the function and return false
		if(!isAdminPassphrase) { 
			failMessage = "Incorrect Passphrase, number of attempts: " + this.numberOfAttempts;
			this.numberOfAttempts--;
			return false; 
		}
		
		if(this.numberOfAttempts == 0) { 
			JOptionPane.showMessageDialog(null, "Failed Attempts Exceeded");
			System.exit(0); 
		};
		
		// If the login is unsuccessful, return false
		if(!isLoginSuccessful) { 
			return false; 
		}
		
		boolean isDefaultAdminPassword = this.administratorFunctions.loginOperations.checkDefaultAdminPassword(adminName, adminPassword);
		
		if(isDefaultAdminPassword) {
			this.promptAdminForReentry();
		}
			
		return true;
	}
	
	// -----------------------------------------------------------------------------------
	public void setNumberOfAttempts(int numberOfAttempts) {
		this.numberOfAttempts = numberOfAttempts;
	}
	
	// -----------------------------------------------------------------------------------
	public void decreaseNumberOfAttempts() {
		this.numberOfAttempts--;
	}
	
	// -----------------------------------------------------------------------------------
	public int getNumberOfAttempts() {
		return this.numberOfAttempts;
	}
	
	// -----------------------------------------------------------------------------------
	public boolean searchAdmin(String userName) {
		boolean result = false;
		
		// **LOG WILL BE PUT IN THIS FUNCTION
		if(this.administratorFunctions.getAdminHashMap() != null 
		&& this.administratorFunctions.getAdminHashMap().get(userName) != null) {
			result = true;
		}		
		
		else {
			System.out.println("MASSIVE ERROR: NO USER ENTERED");
		}
			
		return result;
	}
	
	public boolean promptAdminForReentry() {
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
		    	JOptionPane.showMessageDialog(null, "PASSWORD CHANGE CANCELLED");
		    	return false;
		    }
		}
		
		return false;
	}
	
	public void validateAdministratorCredentials() {
		this.administratorFunctions.getAdminHashMap().get(adminName);
	}
	
	// Check to see if the passwords meet password validation
	public boolean performPasswordValidations(char[] newPasswordEntered, char[] newPasswordReentered) {
		
		if(!this.inputOperations.isMeetsPasswordRequirements(newPasswordEntered)) {
			return false;
		}
			
		if(!this.inputOperations.isMeetsPasswordRequirements(newPasswordReentered)) {
			return false;
		}
		
		return true;
	}
	
	public String performAdminLoginValidations() {
		String message = "";
		// Ensure admin only contains legal characters
		boolean validAdminnameCharacters = this.inputOperations.isOnlyLettersAndNumbers(adminName);
		boolean validPasswordCharacters = this.inputOperations.isLegalCharactersEntered(new String(adminPassword));
		
		if(!validAdminnameCharacters) {
			message += "Error: AdminName Can Only Contain Characters\n";
		}
		
		if(!validPasswordCharacters) {
			message += "Error: Illegal Password Characters Detected";
		}
		
		return message;
	}
	
	@Override
	protected void done() {
		try {
			boolean success = get();
	   
			if (success) {
				this.administratorFunctions.loginOperations.setLogUserIn(this.adminName, true, "ADMIN");
				this.administratorFunctions.panelCentral.setCurrentPanelString(this.administratorFunctions.panelCentral.PANEL_ADMINCENTRAL);
			} 
			else {
				JOptionPane.showMessageDialog(null, failMessage, "Creation Failed", JOptionPane.ERROR_MESSAGE);
			}
		} 
			
		catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(null, "Error searching error.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
