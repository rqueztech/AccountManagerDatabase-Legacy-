package databaseproject;

import java.util.HashMap;

import javax.swing.JOptionPane;

public class AdministratorFunctions {
	public String configHeader = "empNoCounter,admNoCounter,adPhrase,adSalt\n";
	
	public EmployeeFunctions empFunctions;
	public CSVOperations csvOperations;
	public InputOperations inputOperations;
	public ConfigurationOperations configurationOperations;
	public LoginOperations loginOperations;
	public PanelCentral panelCentral;
	public ConfigurationPanel panelInitialConfiguration;
	public PasswordEncryption passwordEncryption;
	
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
		this.passwordEncryption = this.panelCentral.passwordEncryption;
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
			
			char[] mgrPassword = JOptionPane.showInputDialog("Enter Manager Password").toCharArray();
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
	
	public void updateNewUserPassword(String userName, char[] userPassword) {
		String salt = this.passwordEncryption.generateSalt();
		String hashedNewUserPassword = this.passwordEncryption.hashPassword(userPassword, salt);
		
		EmployeeNode employeeMap = this.getEmployeeHashMap().get(userName);
		employeeMap.setChangedPassword(hashedNewUserPassword, salt);
		this.csvOperations.overwriteUserFile();
	}
	
	public void updateNewAdminPassword(String adminName, char[] adminPassword) {
		String salt = this.panelCentral.passwordEncryption.generateSalt();
		String hashedNewUserPassword = this.panelCentral.passwordEncryption.hashPassword(adminPassword, salt);
		
		this.getAdminHashMap().get(adminName).setChangedPassword(hashedNewUserPassword, salt);
		this.csvOperations.overwriteAdminFile();
	}
	
	
	
	//-------------------------------------------------------------------------------------
	public String generateNewUserName(String fstName, String lastName, String typeOfUser) {
		
		int uniqueUsernameCounter = 0;
		
		// Create a new username for the username
		StringBuilder userName = new StringBuilder(); 
		
		// Append the first character of the first name, first four of the
		// last name
		userName.append(fstName.toLowerCase().charAt(0));
		userName.append(lastName.toLowerCase().substring(0,4));
		
		// Iterate through the hashmap until the username is not found
		// In the hashmap
		while(this.getEmployeeHashMap().get(userName.toString()) != null) {
			if(uniqueUsernameCounter != 0) {
				userName.setLength(0);
				userName.append(fstName.toLowerCase().charAt(0));
				userName.append(lastName.toLowerCase().substring(0,4));
			}
			
			userName.append(++uniqueUsernameCounter);
		}
				
		return userName.toString();
	}

	//-------------------------------------------------------------------------------------
	public String generateNewAdminName(String fstName, String lastName, String typeOfUser) {
		
		int uniqueUsernameCounter = 0;
		
		// Create a new username for the username
		StringBuilder userName = new StringBuilder(); 
		
		// Append the first character of the first name, first four of the
		// last name
		userName.append(fstName.toLowerCase().charAt(0));
		userName.append(lastName.toLowerCase().substring(0,4));
		
		while(this.getAdminHashMap().get(userName.toString()) != null) {
			if(uniqueUsernameCounter != 0) {
				userName.setLength(0);
				userName.append(fstName.toLowerCase().charAt(0));
				userName.append(lastName.toLowerCase().substring(0,4));
			}
			
			userName.append(++uniqueUsernameCounter);
		}
		
		return userName.toString();
	}
		
	//-------------------------------------------------------------------------------------
	public String generateDefaultUserPassword(String fName) {
		StringBuilder sbNewUserPasswored = new StringBuilder();
		
		// The default password will be "abc" followed by the first letter of 
		sbNewUserPasswored.append("abc" + fName.charAt(0));
		
		return sbNewUserPasswored.toString();
	}
	
	public String validateNewUser(String firstName, String lastName) {
		StringBuilder sb = new StringBuilder();
		
		// If only letters were entered for the first name, pass validation
		if(!this.inputOperations.isOnlyLetterCharacters(firstName)) {
			sb.append("First Name: Only Alphabet Allowed\n");
		}
		
		if(!this.inputOperations.isOnlyLetterCharacters(lastName)) {
			sb.append("Last Name: Only Alphabet Allowed\n");
		}
		
		return sb.toString();
	}
	
	public EmployeeNode generateEmployeeCredentials(String firstName, String lastName, String gender) {
		String newEmployeeUserName = this.generateNewUserName(firstName, lastName, panelCentral.USER);
		
		char[] newEmployeePassword = this.generateDefaultUserPassword(firstName).toCharArray();
		String newEmployeeSalt = this.panelCentral.passwordEncryption.generateSalt();
		String encryptedPasswordHash = this.panelCentral.passwordEncryption.hashPassword(newEmployeePassword, newEmployeeSalt);
		this.configurationOperations.increaseAdmNo();
		
		return new EmployeeNode(newEmployeeUserName, firstName, lastName, gender, encryptedPasswordHash, newEmployeeSalt, 0);
	}
	
	public void addNewEmployeeToHashMap(String newEmployeeName, EmployeeNode newEmployee) {
		// Add the new created user into the hashmap
		this.getEmployeeHashMap().put(newEmployeeName, newEmployee);
	}
	
	//-------------------------------------------------------------------------------------
	// This will add the userName with a brand new profile into the database
	public boolean createNewEmployee(String firstName, String lastName, String gender) {
		String message = validateNewUser(firstName, lastName);
		
		if(message.equals("")) {
			EmployeeNode newEmployee = generateEmployeeCredentials(firstName, lastName, gender);
			
			this.addNewEmployeeToHashMap(newEmployee.getUserName(), newEmployee);
			
			this.csvOperations.overwriteUserFile();
			this.csvOperations.readFromUserFile();
			
			return true;
		}
		
		return false;
	}
	
	public AdminNode generateAdminCredentials(String firstName, String lastName) {
		String newAdminUserName = this.generateNewAdminName(firstName, lastName, panelCentral.ADMIN);
		char[] newAdminPassword = this.generateDefaultUserPassword(firstName).toCharArray();
		String newAdminSalt = this.panelCentral.passwordEncryption.generateSalt();
		String encryptedPasswordHash = this.passwordEncryption.hashPassword(newAdminPassword, newAdminSalt);
		this.configurationOperations.increaseAdmNo();
		
		return new AdminNode(newAdminUserName, firstName, lastName, encryptedPasswordHash, newAdminSalt, 0);
	}
	
	public void addNewAdminToHashMap(String newAdminName, AdminNode newAdmin) {
		// Add the new created user into the hashmap
		this.getAdminHashMap().put(newAdminName, newAdmin);
	}
	
	public boolean createNewAdmin(String firstName, String lastName) {
		String message = validateNewUser(firstName, lastName);
		
		if(message.equals("")) {
			AdminNode newAdmin = generateAdminCredentials(firstName, lastName);
			
			this.addNewAdminToHashMap(newAdmin.getAdminName(), newAdmin);
			
			this.csvOperations.overwriteAdminFile();
			this.csvOperations.readFromAdminFile();
			
			return true;
		}
		
		return false;
	}
	
	
	public boolean createInitialAdmin(String firstName, String lastName, char[] newAdminPassword) {
		String message = validateNewUser(firstName, lastName);
		
		if(message.equals("")) {
			String newAdminUserName = this.generateNewAdminName(firstName, lastName, panelCentral.ADMIN);
			
			// While there seems like no reason to generate this in another function, password
			// Complexity may be increased in the future. By using a separate function, it will
			// be much easier to do so in the future
			String newAdminSalt = this.passwordEncryption.generateSalt();
			String encryptedPasswordHash = this.passwordEncryption.hashPassword(newAdminPassword, newAdminSalt);
			
			// Increase the employee number by one to prevent a duplicate employee
			// number from being used
			this.configurationOperations.increaseAdmNo();
			
			// Add the new created user into the hashmap
			this.getAdminHashMap().put(newAdminUserName, new AdminNode(newAdminUserName, firstName, lastName, 
					encryptedPasswordHash, newAdminSalt, this.configurationOperations.getAdmNo()));
			
			this.csvOperations.overwriteAdminFile();
			this.csvOperations.readFromAdminFile();
			
			return true;
		}
		
		return false;
	}
}
