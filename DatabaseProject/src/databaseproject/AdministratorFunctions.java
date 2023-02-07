package databaseproject;

import java.util.HashMap;

import javax.swing.JOptionPane;

public class AdministratorFunctions {
	public EmployeeFunctions empFunctions;
	public CSVOperations csvOperations;
	public InputOperations inputOperations;
	public ConfigurationOperations configurationOperations;
	public String configHeader = "empNoCounter,admNoCounter,adPhrase,adSalt\n";
	public LoginOperations loginOperations;
	public PanelCentral panelCentral;
	public PanelInitialConfiguration panelInitialConfiguration;
	
	public final String SUCCESS = "SUCCESS";
	
	// These two hashmaps will contain all of the employee and administrator
	// Information
	private HashMap<String,EmployeeNode> employeeHashMap;
	private HashMap<String, AdminNode> adminHashMap;
	
	//---NOT FUNCITONAL YET
	//private HashMap<String, EmployeeNode> administratorHashMap;
		
	//------------------------------------------------------------------------------------
	public AdministratorFunctions(PanelCentral panelCentral) {
		this.employeeHashMap = new HashMap<String, EmployeeNode>();
		this.adminHashMap = new HashMap<String, AdminNode>();
		
		this.inputOperations = new InputOperations();
		this.empFunctions = new EmployeeFunctions();
		
		this.configurationOperations = new ConfigurationOperations(this);
		this.csvOperations = new CSVOperations(this);
		this.panelCentral = panelCentral;
		this.loginOperations = new LoginOperations(this.panelCentral, this);
	}
	
	// ------------------------------------------------------------------------------------
	// USER HASHMAP
	// The hashmap acts as a data structure that holds all of the data being interacted with in the program currently.
	public HashMap <String, EmployeeNode> getEmployeeHashMap() {
		return this.employeeHashMap;
	}
	
	// ------------------------------------------------------------------------------------
	// DELETE USER
	public boolean isEmployeeExists(String userToSearch) {
		boolean result = false;
		
		if(this.getEmployeeHashMap().containsKey(userToSearch)) {
			result = true;
		}
		
		return result;
	}
	
	// ------------------------------------------------------------------------------------
	// ADMIN HASHMAP
	// The hashmap acts as a data structure that holds all of the data being interacted with in the program currently.
	public HashMap <String, AdminNode> getAdminHashMap() {
		return this.adminHashMap;
	}
	
	
	// ------------------------------------------------------------------------------------
	// CONFIG FILE HASHMAP
	// The hashmap stores all of the configurations that will be used to properly store the settings
	// That are set by the end-user in regards to the database
	
	// ------------------------------------------------------------------------------------
	public void readIntoEmployeeHashMap(String usrName, String fstName, String lstName, 
			String gender, String hashedPassword, String salt, int empNo) {
		
		this.getEmployeeHashMap().put(usrName, new EmployeeNode(usrName, fstName, 
				lstName, gender, hashedPassword, salt, empNo));
	}
	
	// ------------------------------------------------------------------------------------
	public void readIntoAdminHashMap(String usrName, String fstName, String lstName,
			String hashedPassword, String salt, int empNo) {
		
		this.getAdminHashMap().put(usrName, new AdminNode(usrName, fstName,
				lstName, hashedPassword, salt, empNo));
	}
	
	//-------------------------------------------------------------------------------------
	public boolean deleteUser(String userToDelete) {
		boolean isUserExists = this.isEmployeeExists(userToDelete);
			
			String mgrPassword = JOptionPane.showInputDialog("Enter Manager Password");
			boolean checkAdminPassword = this.loginOperations.checkAdminPassphrase(mgrPassword);
		
			StringBuilder sb = new StringBuilder();
			
			sb.append("Delete ");
			sb.append(userToDelete);
			
			String message = "";
			
			if(!isUserExists) {
				sb.append(" Fail: User Does Not Exist");
				message = sb.toString();
				JOptionPane.showMessageDialog(null, message, "Delete Fail", JOptionPane.ERROR_MESSAGE);
			}
			
			else if(isUserExists && !checkAdminPassword) {
				sb.append(" Fail: Password Incorrect");
				message = sb.toString();
				JOptionPane.showMessageDialog(null, message, "Delete Fail", JOptionPane.ERROR_MESSAGE);
			}
			
			else if(isUserExists && checkAdminPassword) {
				this.getEmployeeHashMap().remove(userToDelete);
				this.csvOperations.overwriteUserFile();
				this.csvOperations.readFromConfigurationFile();
				this.panelCentral.panelAdminDisplayUsers.updateTable();
			}
		
		return isUserExists;
	}
	
	//-------------------------------------------------------------------------------------
	/*
		This function will create a new employee given first name and last name.
		It will check to see if the user exists in the hashmap. If the user exists,
		it will retry the same username with a different number at the end. This wil
		prevent duplicates.
	*/
	public String generateNewEmployeeUsername(String fstName, String lastName, String typeOfUser) {
		
		//=============================
		// 1. USERNAME PORTION
		// =============================
		// Counter that will increment for the new username
		int uniqueUsernameCounter = 0;
		
		// Create a new username for the username
		StringBuilder userName = new StringBuilder(); 
		
		// Append the first character of the first name, first four of the
		// last name
		userName.append(fstName.toLowerCase().charAt(0));
		userName.append(lastName.toLowerCase().substring(0,4));
		
		// Iterate through the hashmap until the username is not found
		// In the hashmap
		if(typeOfUser.equals("USER")) {
			while(this.getEmployeeHashMap().get(userName.toString()) != null) {
				if(uniqueUsernameCounter != 0) {
					userName.setLength(0);
					userName.append(fstName.toLowerCase().charAt(0));
					userName.append(lastName.toLowerCase().substring(0,4));
				}
				
				userName.append(++uniqueUsernameCounter);
			}
		}
		
		else if(typeOfUser.equals("ADMIN")) {
			while(this.getAdminHashMap().get(userName.toString()) != null) {
				if(uniqueUsernameCounter != 0) {
					userName.setLength(0);
					userName.append(fstName.toLowerCase().charAt(0));
					userName.append(lastName.toLowerCase().substring(0,4));
				}
				
				userName.append(++uniqueUsernameCounter);
			}
		}
		
		return userName.toString();
	}
	
	//-------------------------------------------------------------------------------------
	public String generateNewUserPassword(String fName) {
		StringBuilder sb = new StringBuilder();
		
		// The default password will be "abc" followed by the first letter of 
		//
		sb.append("abc" + fName.charAt(0));
		
		return sb.toString();
	}
	
	//-------------------------------------------------------------------------------------
	// This will add the userName with a brand new profile into the database
	public String createNewEmployee(String firstName, String lastName, String typeOfUser, String gender) {
		StringBuilder sb = new StringBuilder(this.SUCCESS);
		
		// If only letters were entered for the first name, pass validation
		if(!this.inputOperations.onlyLetterCharacters(firstName)) {
			
			// If the stringbuilder is the default success string, clear the success
			// Message
			if(sb.toString().equals(this.SUCCESS)) {
				sb.setLength(0);
			}
			
			// Append the error message to the string builder
			sb.append("First Name: Non-Alphabetic Input Not Allowed\n");
		}
		
		// If only letters were entered for the last name, pass validation
		if(!this.inputOperations.onlyLetterCharacters(lastName)) {
			
			// If the stringbuilder is the default success string, clear the success
			// Message
			if(sb.toString().equals(this.SUCCESS)) {
				sb.setLength(0);
			}
			sb.append("Last Name: Non-Alphabetic Input Not Allowed\n");
		}
		
		// If gender is not entered
		if(gender.equals("Select")) {
			
			// If the stringbuilder is the default success string, clear the success
			// Message
			if(sb.toString().equals(this.SUCCESS)) {
				sb.setLength(0);
			}
			sb.append("Must Select Gender");
		}
		
		if(sb.toString().equals(this.SUCCESS)) {
			String newEmployeeUserName = this.generateNewEmployeeUsername(firstName, lastName, typeOfUser);
			
			// While there seems like no reason to generate this in another function, password
			// Complexity may be increased in the future. By using a separate function, it will
			// be much easier to do so in the future
			String newEmployeePassword = this.generateNewUserPassword(firstName);
			String newEmployeeSalt = this.panelCentral.passwordEncryption.generateSalt();
			String encryptedPasswordHash = this.panelCentral.passwordEncryption.hashPassword(newEmployeePassword, newEmployeeSalt);
			
			
			// Get the new user salt and encrypted password byte
			
			if(typeOfUser.equals("USER")) {
				// Increase the employee number by one to prevent a duplicate employee
				// number from being used
				this.configurationOperations.increaseEmpNo();
				
				// Add the new created user into the hashmap
				this.getEmployeeHashMap().put(newEmployeeUserName, new EmployeeNode(newEmployeeUserName, firstName, lastName, 
						gender, encryptedPasswordHash, newEmployeeSalt, this.configurationOperations.getEmpNo()));
				this.csvOperations.overwriteUserFile();
				this.csvOperations.readFromUserFile();
			}
			
			else if(typeOfUser.equals("ADMIN")) {
				// Increase the employee number by one to prevent a duplicate employee
				// number from being used
				this.configurationOperations.increaseAdmNo();
				
				// Add the new created user into the hashmap
				this.getAdminHashMap().put(newEmployeeUserName, new AdminNode(newEmployeeUserName, firstName, lastName, 
						encryptedPasswordHash, newEmployeeSalt, this.configurationOperations.getAdmNo()));
				
				this.csvOperations.overwriteAdminFile();
				this.csvOperations.readFromAdminFile();
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Massive Error In: admin.createNewEmployee()");
			}
			
			this.csvOperations.overwriteConfigFile();
			
		}
		
		else {
			sb.insert(0, "CREATE USER FAILED\n");
			JOptionPane.showMessageDialog(null, sb.toString(), "User Not Created", JOptionPane.ERROR_MESSAGE);
		}
	
		return sb.toString();
	}
}
