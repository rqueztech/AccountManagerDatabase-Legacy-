package databaseproject;

public class LoginOperations {
	private String currentUser;
	private String userType;
	private boolean loggedInStatus;
	
	public AdministratorFunctions administratorFunctions;
	public CSVOperations csvOperations;
	public PanelCentral panelCentral;
	public ProgramLogs programLogs;
	
	// -----------------------------------------------------------------------------------
	public LoginOperations(PanelCentral panelCentral, AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
		this.csvOperations = administratorFunctions.csvOperations;
		this.panelCentral = panelCentral;
		this.programLogs = this.panelCentral.programLogs;
	}
	
	// -----------------------------------------------------------------------------------
	// Comes after: SearchUser()
	// Preconditions: SeachUser has returned true
	public boolean isValidLogin(String userName, String attemptedPassword, String typeOfUser) {
		boolean result = false;
		String hashedStoredPassword = "";
		String hashedAttemptedPassword = "";
		String salt = "";
		
		if(typeOfUser.equals("USER")) {
			hashedStoredPassword = this.administratorFunctions.getEmployeeHashMap().get(userName).getHashedUserPassword();
			salt = this.administratorFunctions.getEmployeeHashMap().get(userName).getSalt();
			hashedAttemptedPassword = this.panelCentral.passwordEncryption.hashPassword(attemptedPassword, salt);
		}
		
		else if(typeOfUser.equals("ADMIN")) {
			hashedStoredPassword = this.administratorFunctions.getAdminHashMap().get(userName).getAdminPassword();
			salt = this.administratorFunctions.getAdminHashMap().get(userName).getSalt();
			/*
			System.out.println("Plain Password: " + attemptedPassword);
			System.out.println("Current Salt: " + salt);
			//System.out.println("First: " + hashedStoredPassword + " :: \nSecond: " + hashedAttemptedPassword);
			*/
			hashedAttemptedPassword = this.panelCentral.passwordEncryption.hashPassword(attemptedPassword, salt);
		}
		
		// If both hashes line up, 
		if(hashedStoredPassword.equals(hashedAttemptedPassword)) {
			result = true;
		}
		
		return result;
	}
	
	// -----------------------------------------------------------------------------------
	public boolean checkAdminPasswordWhileLoggedIn(String mgrPassword) {
		boolean validAdminPassword = false;
		
		String currentAdminSalt = this.administratorFunctions.getAdminHashMap()
				.get(this.getCurrentUser())
				.getSalt();
		
		String currentStoredPassword = this.administratorFunctions.getAdminHashMap()
				.get(this.getCurrentUser())
				.getAdminPassword();
		
		String currentHashedPassword = this.panelCentral.passwordEncryption.hashPassword(mgrPassword, currentAdminSalt);
		
		if(currentStoredPassword.equals(currentHashedPassword)) {
			validAdminPassword = true;
		}
		
		return validAdminPassword;
	}
	
	// -----------------------------------------------------------------------------------
	// The function below will check to see if the passphrase was entered properly or not.
	public boolean checkAdminPassphrase(String passphrase) {
		
		//Be default, set the admin password to false. This will prevent
		//Any false positives.
		boolean result = false;
		
		String adminPassphrase = this.administratorFunctions.configurationOperations.getAdminPassphrase();
		String salt = this.administratorFunctions.configurationOperations.getSalt();
						
		String newHashedPassword = this.panelCentral.passwordEncryption.hashPassword(passphrase, salt); 
		
		// If the two match up, then the passphrase was set properly.
		if(adminPassphrase.equals(newHashedPassword)) {
			result = true;
		}
		
						
		return result;
	}
	
	// -----------------------------------------------------------------------------------
	public boolean checkDefaultPassword(String userName, String userAttemptedPassword, String typeOfUser) {
		boolean result = false;
		
		// Salt / Hashed Attempted Password For Current Attempted Password
		String salt = "";
		
		if(typeOfUser.equals(this.panelCentral.USER)) {
			salt = this.administratorFunctions.getEmployeeHashMap().get(userName).getSalt();
		}
		
		else if(typeOfUser.equals(this.panelCentral.ADMIN)) {
			salt = this.administratorFunctions.getAdminHashMap().get(userName).getSalt();
		}
			
		String hashedAttemptedPassword = this.panelCentral.passwordEncryption.hashPassword(userAttemptedPassword, salt);
		
		// Salt / Hashed Default User Password Combination
		String defaultPasswordPlaintext = "abc" + userName.substring(0, 1).toUpperCase();
		String hashedDefault = this.panelCentral.passwordEncryption.hashPassword(defaultPasswordPlaintext, salt);

		
		if(hashedAttemptedPassword.equals(hashedDefault)) {
			result = true;
		}
		
		return result;
	}
	
	// -----------------------------------------------------------------------------------
	public void updateEmployeeNewPassword(String userName, String changePasswordReentered) {
		String newSaltString = this.panelCentral.passwordEncryption.generateSalt();
		String hashedNewPassword = this.panelCentral.passwordEncryption.hashPassword(changePasswordReentered, newSaltString);
		
		this.administratorFunctions.getEmployeeHashMap().get(userName).setChangedPassword(hashedNewPassword, newSaltString);
		this.csvOperations.overwriteUserFile();
	}
	
	// -----------------------------------------------------------------------------------
	public void updateAdminNewPassword(String userName, String changePasswordReentered) {
		String newSaltString = this.panelCentral.passwordEncryption.generateSalt();
		String hashedNewPassword = this.panelCentral.passwordEncryption.hashPassword(changePasswordReentered, newSaltString);
		
		if(this.administratorFunctions.getAdminHashMap().isEmpty()) {
			System.out.println("Hash Map is Empty");
		}
		
		this.administratorFunctions.getAdminHashMap().get(userName)
			.setChangedPassword(hashedNewPassword, newSaltString);
		
		this.csvOperations.overwriteAdminFile();
	}
	
	// -----------------------------------------------------------------------------------
	public boolean searchUser(String userName, String typeOfuser) {
		boolean result = false;
		
		if(typeOfuser.equals("USER")) {
			// **LOG WILL BE PUT IN THIS FUNCTION
			if(this.administratorFunctions.getEmployeeHashMap() != null 
			&& this.administratorFunctions.getEmployeeHashMap().get(userName) != null) {
				result = true;
			}
		}
		
		else if (typeOfuser.equals("ADMIN")) {
			// **LOG WILL BE PUT IN THIS FUNCTION
			if(this.administratorFunctions.getAdminHashMap() != null 
			&& this.administratorFunctions.getAdminHashMap().get(userName) != null) {
				result = true;
			}
		}
		
		else {
			System.out.println("MASSIVE ERROR: NO USER ENTERED");
		}
			
		return result;
	}
	
	// -----------------------------------------------------------------------------------
	public void setLogUserIn(String currentUser, boolean loginUser, String userType) {
		this.setCurrentUser(currentUser);
		this.setLoggedInStatus(loginUser);
		this.setUserType(userType);
	}
	
	// -----------------------------------------------------------------------------------
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	// -----------------------------------------------------------------------------------
	public String getUserType() {
		return this.userType;
	}
	
	// -----------------------------------------------------------------------------------
	public void logOutuser() {
		String currentUserForLog = this.currentUser;
		this.currentUser = "";
		this.loggedInStatus = false;
		this.panelCentral.programLogs.logCurrentEvent(this.userType, currentUserForLog, this.programLogs.getLOGOUT_SUCCESS());
	}
	
	// -----------------------------------------------------------------------------------
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	
	// -----------------------------------------------------------------------------------
	public String getCurrentUser() {
		return this.currentUser;
	}
	
	// -----------------------------------------------------------------------------------
	public void setLoggedInStatus(boolean loggedInStatus) {
		this.loggedInStatus = loggedInStatus;
	}
	
	// -----------------------------------------------------------------------------------
	public boolean getLoggedInStatus() {
		return this.loggedInStatus;
	}
	
}
