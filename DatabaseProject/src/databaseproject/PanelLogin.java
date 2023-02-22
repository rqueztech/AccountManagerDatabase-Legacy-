package databaseproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// This entire class creates the login panel. The login panel will prompt
// the user for the username and password.

public class PanelLogin extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -702019029783943216L;
	
	public PanelCentral panelCentral;
	public PanelAdminCentral panelAdminCentral;
	public ProgramLogs programLogs;
	
	public Image image;
	public GridBagConstraints grid;
	public ProgramLogs pgmLogs;
	
	JPanel pwdInput;
	
	// Text fields for usrName and Password
	public JTextField usrName;
	public JPasswordField usrPassword;
	
	public AdministratorFunctions administratorFunctions;
	
	public LoginOperations loginOperations;
	public CSVOperations csvOperations;
	
	// This instance does not rely on other instances
	public InputOperations inputOperations;
	
	
	// Text boxes that will be used for password reset prompt
	public JTextField usrDefaultChange;
	public JTextField usrDefaultChangeRepeat;
	public JTextField passphraseInput;
	
	public PanelLogin(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		// Potentially remove login Ops and CSV OPS 
		// class
		this.administratorFunctions = administratorFunctions;
		
		this.csvOperations = this.administratorFunctions.csvOperations;
		this.loginOperations = this.administratorFunctions.loginOperations;
		this.panelCentral = panelCentral;
		this.programLogs = this.panelCentral.programLogs;
		
		this.panelAdminCentral = new PanelAdminCentral(this.administratorFunctions, 
				this.panelCentral);
		
		// Initialize instances that don't depend on other instances
		this.inputOperations = new InputOperations();
		
		this.setSize(600, 600);
		this.image = new ImageIcon("backgroundd.jpg").getImage();
		
		this.setLayout(new GridBagLayout());
		this.grid = new GridBagConstraints();
		
		this.passphraseInput = new JTextField();
		
		//-----------------------------------------------------------------------------------
		// NAME_USER: This whole sector deals with the label and textfield
		// For the username
		JLabel fLab = new JLabel("Username");
		this.grid.gridx = 0;
		this.grid.gridy = 0;
		this.grid.gridwidth = 1;
		fLab.setForeground(Color.white);
		this.add(fLab, grid);
		
		// Input User Name to login
		this.usrName = new JTextField(15);
		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridwidth = 2;
		this.add(this.usrName, grid);
		
		//-----------------------------------------------------------------------------------
		// PASSWORD_USER: This whole sector deals with the label and textfield
		// For the password
		JLabel lLab = new JLabel("Password");
		this.grid.gridx = 0;
		this.grid.gridy = 2;
		this.grid.gridwidth = 1;
		lLab.setForeground(Color.white);
		this.add(lLab, grid);
		
		this.usrPassword = new JPasswordField(15);
		this.grid.gridx = 0;
		this.grid.gridy = 3;
		this.grid.gridwidth = 2;
		this.add(this.usrPassword, grid);
		
		//-----------------------------------------------------------------------------------
		// LOGIN_BUTTON: This whole section deals with the login for both the user and
		// The Admin login
		
		JButton userLoginButton = new JButton("Login");
		userLoginButton.setBackground(Color.black);
		userLoginButton.setForeground(Color.gray);
		this.grid.gridx = 0;
		this.grid.gridy = 4;
		this.grid.gridwidth = 1;
		this.add(userLoginButton, grid);
		userLoginButton.addActionListener(e -> this.userLoginMethod());
		
		// Login Admin Button
		JButton adminLoginButton = new JButton("Admin");
		adminLoginButton.setBackground(Color.black);
		adminLoginButton.setForeground(Color.gray);
		this.grid.gridx = 1;
		this.grid.gridy = 4;
		this.grid.gridwidth = 1;
		this.add(adminLoginButton, grid);
		adminLoginButton.addActionListener(e -> this.adminLoginMethod());
		
		//Enter String (BOTH)
		this.pwdInput = new JPanel();
		
		this.grid.gridx = 0;
		this.grid.gridy = 0;
		this.grid.gridwidth = 1;
		this.pwdInput.add(new JLabel("Enter Password: "), grid);
		
		this.grid.gridx = 1;
		this.grid.gridy = 0;
		this.grid.gridwidth = 1;
		this.usrDefaultChange = new JTextField(16);
		this.pwdInput.add(this.usrDefaultChange, grid);
		
		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridwidth = 1;
		this.pwdInput.add(new JLabel("Repeat Password: "), grid);
		
		this.grid.gridx = 1;
		this.grid.gridy = 1;
		this.grid.gridwidth = 1;
		this.usrDefaultChangeRepeat = new JTextField(16);
		this.pwdInput.add(this.usrDefaultChangeRepeat, grid);
	}
	
	//-----------------------------------------------------------------------------------
	// The action method for the login button
	public void userLoginMethod() {
		this.completeLoginFunction(this.panelCentral.USER);
	}
	
	//-----------------------------------------------------------------------------------
	// The action method for the Administrator Login
	public void adminLoginMethod() {
		// Prompt the administrator to enter the valid passphrase to prove
		// That they are the administrator.
		String adminPassphrase = JOptionPane.showInputDialog(null, 
				"Enter Secret Passphrase", "Elevated Privilege Request", JOptionPane.INFORMATION_MESSAGE);
		
		// The boolean will result whether the password is entered correctly
		// Or not.
		boolean validPreAdminPassword = this.loginOperations.checkAdminPassphrase(adminPassphrase);
		this.passphraseInput.setText("");
		
		if(validPreAdminPassword) {
			completeLoginFunction(this.panelCentral.ADMIN);
		}
		
		else {	
			JOptionPane.showMessageDialog(null, "Passphrase Entered Incorrectly", "Wrong", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//-----------------------------------------------------------------------------------
	// Login Hierarchy Function
	public void completeLoginFunction(String typeOfUser) {
		
		// Get the username and password. The password user password is not
		// processed as a char array since all security is being implemented
		// from the password encrypted function
		String userName = this.usrName.getText();
		String userPassword = String.valueOf(this.usrPassword.getPassword());
		
		// Clear the text boxes
		this.usrName.setText("");
		this.usrPassword.setText("");
		
		//==== Split choice. USER will search through user accounts.
		//COUPLING
		boolean isUserOrAdminExists = this.loginOperations.searchUser(userName, typeOfUser);
		
		// Ensure user only contains legal characters
		boolean validUsernameCharacters = this.inputOperations.isOnlyLettersAndNumbers(userName);
		boolean validPasswordCharacters = this.inputOperations.isLegalCharactersEntered(userPassword);
		
		// If both the username and password contains all legal characters, process
		// The rest of the login attempt
		if (validUsernameCharacters && validPasswordCharacters) {
			
			// USER LOGIN PORTION
			// If the user exists, move on to the user login portion
			if(isUserOrAdminExists) {
				
				// Validation check to make sure the password entered matches
				// The password hashed in the database
				boolean isValidLogin = this.loginOperations.isValidLogin(
						userName, userPassword, typeOfUser);
				
				// If the login is valid, proceed
				if(isValidLogin) {
					this.panelCentral.programLogs.logCurrentEvent(typeOfUser, userName, 
							this.programLogs.getLOGIN_SUCCESS());
					this.usrPassword.setText("");
					
					boolean isDefaultPassword = this.loginOperations.checkDefaultPassword(userName, userPassword, typeOfUser);
					
					// If the default password is not detected, the password was previously
					// Changed. Take the user to the appropriate screen.
					if(!isDefaultPassword) {
						
						// Set the user login information
						this.loginOperations.setLogUserIn(userName, true, typeOfUser);
						
						// If the type of user is ADMIN, go to the admin central panel
						if(typeOfUser.equals(this.panelCentral.ADMIN)) {
							
							this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_ADMINCENTRAL);
						}
						
						// If the type of user is USER, go to the user central
						else if(typeOfUser.equals(this.panelCentral.USER)) {
							
							this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_ADMINCENTRAL);
						}
					}
					
					// If the default password is detected, prompt the user to enter
					else if(isDefaultPassword) {
						
						// Create a user option (numerical representation)
						int usrOption = 0;
						
						// Create a boolean to reflect whether the password change was succecssfull
						// Or failed. If successful, it will be changed to true
						boolean isPasswordChangeUnsuccessful = true;
						
						// Create a do/while loop that will break when either the user clicks cancel
						// or the password change is successful
						do {
							usrOption = JOptionPane.showConfirmDialog(null, this.pwdInput, 
									"Enter Passwords", JOptionPane.OK_CANCEL_OPTION);
							
							// The password must contain no illegal characters, a number, upper case, lower case,
							// and special characters which meet the proper requirements
							String changePasswordEntered = this.usrDefaultChange.getText();
							String changePasswordReEntered = this.usrDefaultChangeRepeat.getText();
							
							// Clear the user default text fields for security reasons
							this.usrDefaultChange.setText("");
							this.usrDefaultChangeRepeat.setText("");
							
							// Catch whether each password met requirements with boolean values
							boolean isPasswordRequirements = this.inputOperations.isMeetsPasswordRequirements(changePasswordEntered);
							boolean isPasswordRequirementsReentered = this.inputOperations.isMeetsPasswordRequirements(changePasswordReEntered);
							
							// Check to see if password meets requirements (character wise and
							// The proper strings)
							if(isPasswordRequirements && isPasswordRequirementsReentered) {
								
								// Match to ensure that the new passwords have been entered properly
								if(changePasswordEntered.equals(changePasswordReEntered)) {
									
									// UPDATE THE PASSWORD
									// TAKE THE USER TO THEIR LOGIN PAGE
									String passwordChangeMessage = userName + ": Password Updated Successfully";
									
									// If the type of user is admin, the administrator file will be overwritten
									if(typeOfUser.equals(this.panelCentral.ADMIN)) {
										this.loginOperations.updateAdminNewPassword(userName, changePasswordReEntered);
										this.csvOperations.overwriteAdminFile();
									}
									
									// If the type of user is the USER, the user file will be overwritten
									else if(typeOfUser.equals(this.panelCentral.USER)) {
										this.loginOperations.updateEmployeeNewPassword(userName, changePasswordReEntered);
										this.csvOperations.overwriteUserFile();
									}
									
									// Print out a message to the user that the password has been successfuly changed
									JOptionPane.showMessageDialog(null, passwordChangeMessage, "Password Updated", JOptionPane.INFORMATION_MESSAGE);
									isPasswordChangeUnsuccessful = false;
									
									// LOG THE EVENT
									this.panelCentral.programLogs.logCurrentEvent(typeOfUser, userName, 
											this.programLogs.getPASSWORD_CHANGE_SUCCESS());
								}
							}
							
							// If the option is equal to 2, this means the operation was cancelled by the end-user
							else if(usrOption == 2) {
								JOptionPane.showMessageDialog(null, "Operation Cancelled", "Relog In", JOptionPane.ERROR_MESSAGE);
								
								// LOG THE EVENT
								this.panelCentral.programLogs.logCurrentEvent(typeOfUser, userName, 
										this.programLogs.getPASSWORD_CHANGE_CANCELLED());
							}
							
							// This message will display if the user password doesn't meet requirements
							else {
								
								String feedbackMessage = "";
								
								// If the new passwords don't match, re-prompt the user to enter the
								// valid input
								
								feedbackMessage += this.inputOperations.passwordEnteredFeedback(changePasswordEntered, changePasswordReEntered);
									
								JOptionPane.showMessageDialog(null, feedbackMessage, 
										"Password Requirments Fail", JOptionPane.WARNING_MESSAGE);

								// LOG THE EVENT
								this.panelCentral.programLogs.logCurrentEvent(typeOfUser, userName, 
										this.programLogs.getPASSWORD_CHANGE_FAIL());
							}
							
						} while(isPasswordChangeUnsuccessful && usrOption == 0);
						
					}
				}
				
				// Invalid username showMessageDialog
				else {
					JOptionPane.showMessageDialog(null, "Invalid User Name / Password", "Invalid Attempt", JOptionPane.ERROR_MESSAGE);
					this.panelCentral.programLogs.logCurrentEvent(typeOfUser, userName, 
							this.programLogs.getLOGIN_FAIL());
				}
			}
			
			// If the user does not exist, Log the certain login fail (user does not exist)
			else {
				JOptionPane.showMessageDialog(null, "Invalid User Name / Password");
				this.panelCentral.programLogs.logCurrentEvent(typeOfUser, userName, 
						this.programLogs.getLOGIN_FAIL_INVALID_NAME());
				
				this.usrPassword.setText("");
			}
		}
		
		// If illegal characters are contained, inform the user which field they
		// Inserted illegal characters in
		else {
			String userMessage = "";
			
			if(validUsernameCharacters == false) {
				userMessage += "Username Field: No special characters allowed\n";
			}
			
			if(validPasswordCharacters == false) {
				userMessage += "Password Field: Can only contain letters, numbers,\n and characters @$!%*#?&";
			}
			
			this.panelCentral.programLogs.logCurrentEvent(typeOfUser, userName,
					this.programLogs.getPASSWORD_CHANGE_ILLEGAL_CHARACTER());
			
			JOptionPane.showMessageDialog(this, userMessage, "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//------------------------------------------------------------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2D = (Graphics) g;
		g2D.drawImage(image, 0, 0, null);
	}
}