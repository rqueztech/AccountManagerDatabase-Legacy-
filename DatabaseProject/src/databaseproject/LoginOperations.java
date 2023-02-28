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
	public boolean isValidAdminLogin(String userName, char[] attemptedAdminPassword) {
		boolean result = false;
		
		String hashedStoredPassword = this.administratorFunctions.getAdminHashMap().get(userName).getHashedAdminPassword();
		String salt = this.administratorFunctions.getAdminHashMap().get(userName).getSalt();
		String hashedAttemptedPassword = this.panelCentral.passwordEncryption.hashPassword(attemptedAdminPassword, salt);
		
		// If both hashes line up, 
		if(hashedStoredPassword.equals(hashedAttemptedPassword)) {
			result = true;
		}
		
		return result;
	}
	
	// -----------------------------------------------------------------------------------
	public boolean checkAdminPasswordWhileLoggedIn(char[] mgrPassword) {
		boolean validAdminPassword = false;
		
		String currentAdminSalt = this.administratorFunctions.getAdminHashMap()
				.get(this.getCurrentUser())
				.getSalt();
		
		String currentStoredPassword = this.administratorFunctions.getAdminHashMap()
				.get(this.getCurrentUser())
				.getHashedAdminPassword();
		
		String currentHashedPassword = this.panelCentral.passwordEncryption.hashPassword(mgrPassword, currentAdminSalt);
		
		if(currentStoredPassword.equals(currentHashedPassword)) {
			validAdminPassword = true;
		}
		
		return validAdminPassword;
	}
	
	// -----------------------------------------------------------------------------------
	public boolean checkAdminPassphrase(char[] passphrase) {
		
		boolean isAdminPassphrase = false;
		
		String adminPassphrase = this.administratorFunctions.configurationOperations.getAdminPassphrase();
		String salt = this.administratorFunctions.configurationOperations.getSalt();
						
		String newHashedPassword = this.panelCentral.passwordEncryption.hashPassword(passphrase, salt); 
		
		// If the two match up, then the passphrase was set properly.
		if(adminPassphrase.equals(newHashedPassword)) {
			isAdminPassphrase = true;
		}
						
		return isAdminPassphrase;
	}
	
	// -----------------------------------------------------------------------------------
	public boolean checkDefaultAdminPassword(String userName, char[] adminAttemptedPassword) {
		String salt = this.administratorFunctions.getAdminHashMap().get(userName).getSalt();
		
		char[] defaultPlainAdminPassword = {'a','b','c',Character.toUpperCase(adminAttemptedPassword[0])};
		String hashedDefault = this.panelCentral.passwordEncryption.hashPassword(defaultPlainAdminPassword, salt);
		String currentAdminPasswordHash = this.administratorFunctions.getAdminHashMap().get(userName).getHashedAdminPassword();
		
		return hashedDefault.equals(currentAdminPasswordHash);
	}
	
	// -----------------------------------------------------------------------------------
	public void updateAdminNewPassword(String userName, char[] changeAdminPasswordReentered) {
		String newSaltString = this.panelCentral.passwordEncryption.generateSalt();
		String hashedAdminNewPassword = this.panelCentral.passwordEncryption.hashPassword(changeAdminPasswordReentered, newSaltString);
		
		if(this.administratorFunctions.getAdminHashMap().isEmpty()) {
			System.out.println("Hash Map is Empty");
		}
		
		this.administratorFunctions.getAdminHashMap().get(userName)
			.setChangedPassword(hashedAdminNewPassword, newSaltString);
		
		this.csvOperations.overwriteAdminFile();
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
	
	// -----------------------------------------------------------------------------------
	public boolean isValidUserLogin(String userName, char[] attemptedAdminPassword) {
		String hashedStoredPassword = this.administratorFunctions.getEmployeeHashMap().get(userName).getHashedUserPassword();
		String salt = this.administratorFunctions.getEmployeeHashMap().get(userName).getSalt();
		String hashedAttemptedPassword = this.panelCentral.passwordEncryption.hashPassword(attemptedAdminPassword, salt);		
		// If both hashes line up, 
		if(hashedStoredPassword.equals(hashedAttemptedPassword)) {
			return true;
		}
		
		return false;
	}
	
	// -----------------------------------------------------------------------------------
	public boolean isDefaultUserPassword(String userName, char[] userAttemptedPassword) {
		String salt = this.administratorFunctions.getEmployeeHashMap().get(userName).getSalt();
		
		char[] defaultPlainUserPassword = {'a','b','c',Character.toUpperCase(userAttemptedPassword[0])};
		String hashedDefault = this.panelCentral.passwordEncryption.hashPassword(defaultPlainUserPassword, salt);
		String currentUserPasswordHash = this.administratorFunctions.getEmployeeHashMap().get(userName).getHashedUserPassword();
		
		return hashedDefault.equals(currentUserPasswordHash);
	}
	
	// -----------------------------------------------------------------------------------
	public void updateEmployeeNewPassword(String userName, char[] changeUserPasswordReentered) {
		String newSaltString = this.panelCentral.passwordEncryption.generateSalt();
		String hashedNewPassword = this.panelCentral.passwordEncryption.hashPassword(changeUserPasswordReentered, newSaltString);
		
		this.administratorFunctions.getEmployeeHashMap().get(userName).setChangedPassword(hashedNewPassword, newSaltString);
		this.csvOperations.overwriteUserFile();
	}
	
	// -----------------------------------------------------------------------------------
		public boolean searchUser(String userName) {
		boolean result = false;
		
		// **LOG WILL BE PUT IN THIS FUNCTION
		if(this.administratorFunctions.getEmployeeHashMap() != null 
		&& this.administratorFunctions.getEmployeeHashMap().get(userName) != null) {
			result = true;
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
