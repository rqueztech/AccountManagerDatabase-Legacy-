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

// This entire class creates the login panel. The login panel will prompt
// the user for the username and password.

public class UserLoginWorker extends SwingWorker<Boolean, Void> {
	private InputOperations inputOperations;
	private AdministratorFunctions administratorFunctions;
	private LoginOperations loginOperations;
	private String userName;
	private char[] userPassword;
	//private PasswordEncryption passwordEncryption;
	
	/**
	 * This class constructs the general information required to create an employee
	 * in a separate thread to be passed in to the createEmployee function found in
	 * the UseristratorFunctions class
	 * @param firstName gets the first name of the user to be added
	 * @param lastName gets the last name of the user to be added
	 * @param gender gets the gender of the user to be added
	 * @param administratorFunctions
	 */
	public UserLoginWorker(String userName, char[] userPassword, AdministratorFunctions administratorFunctions) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.administratorFunctions = administratorFunctions;
		this.inputOperations = administratorFunctions.inputOperations;
		this.loginOperations = administratorFunctions.loginOperations;
		//this.passwordEncryption = administratorFunctions.passwordEncryption;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		boolean isLoginSuccessful = false;
		
		boolean doesUserExist = this.loginOperations.searchUser(userName);
		//String message = performUserLoginValidations();
		String message = performUserLoginValidations();
		
		// If the administrator does not exist and there were no errors in the
		// Input, print error message and return false.
		if(!doesUserExist) {
			JOptionPane.showMessageDialog(null, "User Username/Password Incorrect");
			return false;
		}
		
		else if(!message.equals("")) {
			JOptionPane.showMessageDialog(null, "Invalid Input. Please enter valid input.");
			return false;
		}
		
		// We are going to validate the administrator's credentials
		isLoginSuccessful = this.loginOperations.isValidUserLogin(userName, userPassword);
		
		// If the login is unsuccessful, return false
		if(!isLoginSuccessful) { return false; }
		
		boolean defaultPasswordDetected = this.loginOperations.checkDefaultUserPassword(userName, userPassword);
		
		if(defaultPasswordDetected) { 
			boolean updatePassword = this.promptUserForReentry();
			
			if(!updatePassword) {
				return false;
			}
		}
		
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean promptUserForReentry() {
		String password = "";
		String confirmPassword = "";
		
		boolean passwordsValidate = false;
		boolean passwordsMatch = false;
		
		while (!passwordsValidate) {
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
		    
		    
		    if (result == JOptionPane.OK_OPTION) {
		        password = new String(passwordField.getPassword());
		        confirmPassword = new String(confirmPasswordField.getPassword());
		        passwordsValidate = this.performPasswordValidations(password, confirmPassword);
		        
		        if(passwordsValidate && password.equals(confirmPassword)) {
		        	this.administratorFunctions.updateNewUserPassword(userName, userPassword);
		        	passwordsMatch = true;
		        	return passwordsMatch;
		        }
		    } else {
		        // User clicked cancel or closed the dialog
		    	JOptionPane.showMessageDialog(null, "PASSWORD CHANGE CANCELLED");
		    	return false;
		    }
		}
		
		return false;
	}
	
	public void validateUseristratorCredentials() {
		this.administratorFunctions.getEmployeeHashMap().get(userName);
	}
	
	// Check to see if the passwords meet password validation
	public boolean performPasswordValidations(String newPasswordEntered, String newPasswordReentered) {
		
		if(!this.inputOperations.isMeetsPasswordRequirements(newPasswordEntered)) {
			return false;
		}
		
		if(!this.inputOperations.isMeetsPasswordRequirements(newPasswordReentered)) {
			return false;
		}
		
		return true;
	}
	
	public String performUserLoginValidations() {
		String message = "";
		// Ensure user only contains legal characters
		boolean validUsernameCharacters = this.inputOperations.isOnlyLettersAndNumbers(userName);
		boolean validPasswordCharacters = this.inputOperations.isLegalCharactersEntered(new String(userPassword));
		
		if(!validUsernameCharacters) {
			message += "Error: UserName Can Only Contain Characters\n";
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
         	JOptionPane.showMessageDialog(null, "User Creation Successful", "Creation Successful", JOptionPane.INFORMATION_MESSAGE);
         	this.administratorFunctions.loginOperations.setLogUserIn(this.userName, true, "ADMIN");
         	this.administratorFunctions.panelCentral.setCurrentPanelString(this.administratorFunctions.panelCentral.PANEL_ADMINCENTRAL);
         } else {
         	JOptionPane.showMessageDialog(null, this.performUserLoginValidations(), "Creation Failed", JOptionPane.ERROR_MESSAGE);
         }
     } catch (InterruptedException | ExecutionException e) {
         JOptionPane.showMessageDialog(null, "Error searching error.", "Error", JOptionPane.ERROR_MESSAGE);
     }
	}
}