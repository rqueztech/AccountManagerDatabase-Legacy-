package databaseproject;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

class AdministratorFunctions {
	public String configHeader = "userNoCounter,admNoCounter,adPhrase,adSalt\n";
	
	public UserFunctions userFunctions;
	public CSVOperations csvOperations;
	public InputOperations inputOperations;
	public ConfigurationOperations configurationOperations;
	public LoginOperations loginOperations;
	public PanelCentral panelCentral;
	public InitialConfigurationAgreementPanel panelInitialConfiguration;
	public PasswordEncryption passwordEncryption;
	public DatabaseHashMaps databaseHashMaps;
	
	public final String SUCCESS = "SUCCESS";
	
	//---NOT FUNCITONAL YET
	//private HashMap<String, UserNode> administratorHashMap;
	
	//------------------------------------------------------------------------------------
	AdministratorFunctions(PanelCentral panelCentral) {
		this.inputOperations = new InputOperations();
		this.userFunctions = new UserFunctions(this);
		this.configurationOperations = new ConfigurationOperations(this);
		this.csvOperations = new CSVOperations(this);
		this.panelCentral = panelCentral;
		this.loginOperations = new LoginOperations(this.panelCentral, this);
		this.passwordEncryption = this.panelCentral.passwordEncryption;
		this.databaseHashMaps = new DatabaseHashMaps();
	}
	
	// ------------------------------------------------------------------------------------
	// DELETE USER
	boolean isUserExists(String userToSearch) {
		boolean result = false;
		
		if(this.databaseHashMaps.getUserHashMap().containsKey(userToSearch)) {
			result = true;
		}
		
		return result;
	}
	
	// ------------------------------------------------------------------------------------
	// CONFIG FILE HASHMAP
	// The hashmap stores all of the configurations that will be used to properly store the settings
	// That are set by the end-user in regards to the database
	
	//-------------------------------------------------------------------------------------
	boolean deleteUser(String userToDelete) {
		boolean isUserExists = this.isUserExists(userToDelete);
			
			char[] mgrPassword = JOptionPane.showInputDialog("Enter Manager Password").toCharArray();
			boolean checkAdminPassword = this.loginOperations.adminLoginOperations.isAdminPassphrase(mgrPassword);
		
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
				this.databaseHashMaps.getUserHashMap().remove(userToDelete);
				this.csvOperations.userCSVOperations.overwriteUserFile();
				this.csvOperations.configurationCSVOperations.readConfigurationFile();
				SwingUtilities.invokeLater(() -> {
					this.panelCentral.panelAdminDisplayUsers.updateTable();
				});
			}
		
		return isUserExists;
	}
	
	//-------------------------------------------------------------------------------------
	void updateNewUserPassword(String userName, char[] userPassword) {
		String salt = this.passwordEncryption.generateSalt();
		String hashedNewUserPassword = this.passwordEncryption.hashPassword(userPassword, salt);
		
		UserNode userMap = this.databaseHashMaps.getUserHashMap().get(userName);
		userMap.setChangedPassword(hashedNewUserPassword, salt);
		this.csvOperations.userCSVOperations.overwriteUserFile();
	}
	
	//-------------------------------------------------------------------------------------
	void updateNewAdminPassword(String adminName, char[] adminPassword) {
		String salt = this.panelCentral.passwordEncryption.generateSalt();
		String hashedNewUserPassword = this.panelCentral.passwordEncryption.hashPassword(adminPassword, salt);
		
		this.databaseHashMaps.getAdminHashMap().get(adminName).setChangedPassword(hashedNewUserPassword, salt);
		this.csvOperations.adminCSVOperations.overwriteAdminFile();
	}
	
	//-------------------------------------------------------------------------------------
	String generateNewUserName(String fstName, String lastName, String typeOfUser) {
		
		int uniqueUsernameCounter = 0;
		
		// Create a new username for the username
		StringBuilder userName = new StringBuilder(); 
		
		// Append the first character of the first name, first four of the
		// last name
		userName.append(fstName.toLowerCase().charAt(0));
		userName.append(lastName.toLowerCase().substring(0,4));
		
		// Iterate through the hashmap until the username is not found
		// In the hashmap
		while(this.databaseHashMaps.getUserHashMap().containsKey(userName.toString())) {
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
	String generateNewAdminName(String fstName, String lastName, String typeOfUser) {
		
		int uniqueUsernameCounter = 0;
		
		// Create a new username for the username
		StringBuilder userName = new StringBuilder(); 
		
		// Append the first character of the first name, first four of the
		// last name
		userName.append(fstName.toLowerCase().charAt(0));
		userName.append(lastName.toLowerCase().substring(0,4));
		
		while(this.databaseHashMaps.getAdminHashMap().get(userName.toString()) != null) {
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
	String generateDefaultUserPassword(String fName) {
		StringBuilder sbNewUserPasswored = new StringBuilder();
		
		// The default password will be "abc" followed by the first letter of 
		sbNewUserPasswored.append("abc" + fName.charAt(0));
		
		return sbNewUserPasswored.toString();
	}
	
	//-------------------------------------------------------------------------------------
	UserNode generateUserCredentials(String firstName, String lastName, String gender) {
		String newUserUserName = this.generateNewUserName(firstName, lastName, panelCentral.USER);
		
		char[] newUserPassword = this.generateDefaultUserPassword(firstName).toCharArray();
		String newUserSalt = this.panelCentral.passwordEncryption.generateSalt();
		String encryptedPasswordHash = this.panelCentral.passwordEncryption.hashPassword(newUserPassword, newUserSalt);
		this.configurationOperations.increaseAdmNo();
		int userIDNumber = this.configurationOperations.getUserNo();
		
		return new UserNode(newUserUserName, firstName, lastName, gender, encryptedPasswordHash, newUserSalt, userIDNumber);
	}
	
	
	//-------------------------------------------------------------------------------------
	// This will add the userName with a brand new profile into the database
	boolean createNewUser(String firstName, String lastName, String gender) {
		boolean isLegalFirstName = this.inputOperations.isOnlyLetterCharacters(firstName).isEmpty();
		boolean isLegalLastName = this.inputOperations.isOnlyLetterCharacters(lastName).isEmpty();
		
		if(isLegalFirstName && isLegalLastName) {
			UserNode newUser = this.generateUserCredentials(firstName, lastName, gender);
			this.databaseHashMaps.addNewUserToHashMap(newUser.getUserName(), newUser);
			updateUserOutbound();
			return true;
		}
		
		return false;
	}
	
	//-------------------------------------------------------------------------------------
	AdminNode generateAdminCredentials(String firstName, String lastName) {
		String newAdminUserName = this.generateNewAdminName(firstName, lastName, panelCentral.ADMIN);
		char[] newAdminPassword = this.generateDefaultUserPassword(firstName).toCharArray();
		String newAdminSalt = this.panelCentral.passwordEncryption.generateSalt();
		String encryptedPasswordHash = this.passwordEncryption.hashPassword(newAdminPassword, newAdminSalt);
		this.configurationOperations.increaseAdmNo();
		int adminIDNumber = this.configurationOperations.getAdmNo();
		
		return new AdminNode(newAdminUserName, firstName, lastName, encryptedPasswordHash, newAdminSalt, adminIDNumber);
	}
	
	//-------------------------------------------------------------------------------------
	boolean createNewAdmin(String firstName, String lastName) {
		boolean isValidFirstName = this.inputOperations.containsLegalCharacters(firstName).isEmpty();
		boolean isValidLastName = this.inputOperations.containsLegalCharacters(lastName).isEmpty();
		
		if(isValidFirstName && isValidLastName) {
			AdminNode newAdmin = generateAdminCredentials(firstName, lastName);
			this.databaseHashMaps.addNewAdminToHashMap(newAdmin.getAdminName(), newAdmin);
			this.csvOperations.adminCSVOperations.overwriteAdminFile();
			this.csvOperations.adminCSVOperations.readAdminFile();
			
			return true;
		}
		
		return false;
	}
	
	//-------------------------------------------------------------------------------------
	boolean createInitialAdmin(String firstName, String lastName, char[] newAdminPassword) {
		boolean isLegalFirstName = this.inputOperations.containsLegalCharacters(firstName).isEmpty();
		boolean isLegalLastName = this.inputOperations.containsLegalCharacters(lastName).isEmpty();
		
		if(isLegalFirstName && isLegalLastName) {
			String newAdminUserName = this.generateNewAdminName(firstName, lastName, panelCentral.ADMIN);
			
			// While there seems like no reason to generate this in another function, password
			// Complexity may be increased in the future. By using a separate function, it will
			// be much easier to do so in the future
			String newAdminSalt = this.passwordEncryption.generateSalt();
			String encryptedPasswordHash = this.passwordEncryption.hashPassword(newAdminPassword, newAdminSalt);
			
			// Increase the user number by one to prevent a duplicate user
			// number from being used
			this.configurationOperations.increaseAdmNo();
			
			// Add the new created user into the hashmap
			this.databaseHashMaps.getAdminHashMap().put(newAdminUserName, new AdminNode(newAdminUserName, firstName, lastName, 
					encryptedPasswordHash, newAdminSalt, this.configurationOperations.getAdmNo()));
			
			return true;
		}
		
		updateAdminOutbound();
		
		return false;
	}
	
	//-------------------------------------------------------------------------------------
	void updateAdminOutbound() {
		this.csvOperations.adminCSVOperations.overwriteAdminFile();
		this.csvOperations.adminCSVOperations.readAdminFile();
	}
	
	void updateUserOutbound() {
		this.csvOperations.userCSVOperations.overwriteUserFile();
		this.csvOperations.userCSVOperations.readUserFile();
	}
}
