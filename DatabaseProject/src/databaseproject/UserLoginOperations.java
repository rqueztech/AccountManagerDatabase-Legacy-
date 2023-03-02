package databaseproject;

class UserLoginOperations {
	private AdministratorFunctions administratorFunctions;
	private PanelCentral panelCentral;
	
	// -----------------------------------------------------------------------------------
	UserLoginOperations(AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
		this.panelCentral = administratorFunctions.panelCentral;
	}
	
	// -----------------------------------------------------------------------------------
	boolean isValidUserLogin(String userName, char[] attemptedAdminPassword) {
		String hashedStoredPassword = this.administratorFunctions.databaseHashMaps.getUserHashMap().get(userName).getHashedUserPassword();
		String userStoredSalt = this.administratorFunctions.databaseHashMaps.getUserHashMap().get(userName).getSalt();
		String hashedAttemptedPassword = this.panelCentral.passwordEncryption.hashPassword(attemptedAdminPassword, userStoredSalt);		
		
		return hashedStoredPassword.equals(hashedAttemptedPassword);
	}
	
	// -----------------------------------------------------------------------------------
	boolean isDefaultUserPassword(String userName, char[] userAttemptedPassword) {
		String salt = this.administratorFunctions.databaseHashMaps.getUserHashMap().get(userName).getSalt();
		
		char[] defaultPlainUserPassword = {'a','b','c',Character.toUpperCase(userAttemptedPassword[0])};
		String hashedDefault = this.panelCentral.passwordEncryption.hashPassword(defaultPlainUserPassword, salt);
		String currentUserPasswordHash = this.administratorFunctions.databaseHashMaps.getUserHashMap().get(userName).getHashedUserPassword();
		
		return hashedDefault.equals(currentUserPasswordHash);
	}
	
	// -----------------------------------------------------------------------------------
	// Dead code?
	void updateUserNewPassword(String userName, char[] changeUserPasswordReentered) {
		String newSaltString = this.panelCentral.passwordEncryption.generateSalt();
		String hashedNewPassword = this.panelCentral.passwordEncryption.hashPassword(changeUserPasswordReentered, newSaltString);
		
		this.administratorFunctions.databaseHashMaps.getUserHashMap().get(userName).setChangedPassword(hashedNewPassword, newSaltString);
		this.administratorFunctions.csvOperations.userCSVOperations.overwriteUserFile();
	}
}
