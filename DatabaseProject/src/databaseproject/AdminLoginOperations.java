package databaseproject;

class AdminLoginOperations {
	
	AdministratorFunctions administratorFunctions;
	
	// -----------------------------------------------------------------------------------
	AdminLoginOperations(AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
	}
	
	// -----------------------------------------------------------------------------------
	boolean isValidAdminLogin(String userName, char[] attemptedAdminPassword) {
		boolean result = false;
		
		String hashedStoredPassword = this.administratorFunctions.databaseHashMaps.getAdminHashMap().get(userName).getHashedAdminPassword();
		String salt = this.administratorFunctions.databaseHashMaps.getAdminHashMap().get(userName).getSalt();
		String hashedAttemptedPassword = this.administratorFunctions.panelCentral.passwordEncryption.hashPassword(attemptedAdminPassword, salt);
		
		// If both hashes line up, 
		if(hashedStoredPassword.equals(hashedAttemptedPassword)) {
			result = true;
		}
		
		return result;
	}
	
	// -----------------------------------------------------------------------------------
	boolean checkAdminPasswordWhileLoggedIn(char[] mgrPassword) {
		boolean validAdminPassword = false;
		
		String currentAdminSalt = this.administratorFunctions.databaseHashMaps.getAdminHashMap()
				.get(this.administratorFunctions.loginOperations.getCurrentUser())
				.getSalt();
		
		String currentStoredPassword = this.administratorFunctions.databaseHashMaps.getAdminHashMap()
				.get(this.administratorFunctions.loginOperations.getCurrentUser())
				.getHashedAdminPassword();
		
		String currentHashedPassword = this.administratorFunctions.panelCentral.passwordEncryption.hashPassword(mgrPassword, currentAdminSalt);
		
		if(currentStoredPassword.equals(currentHashedPassword)) {
			validAdminPassword = true;
		}
		
		return validAdminPassword;
	}
	
	// -----------------------------------------------------------------------------------
	boolean isAdminPassphrase(char[] passphrase) {
		
		// Set the administrative password 
		String hashedAdminPassphrase = this.administratorFunctions.configurationOperations.getAdminPassphrase();
		String salt = this.administratorFunctions.configurationOperations.getSalt();
						
		String newHashedPassword = this.administratorFunctions.panelCentral.passwordEncryption.hashPassword(passphrase, salt); 
		
		
		if(hashedAdminPassphrase.equals(newHashedPassword)) {
			return true;
		}
						
		return false;
	}
	
	// -----------------------------------------------------------------------------------
	boolean checkDefaultAdminPassword(String userName, char[] adminAttemptedPassword) {
		String salt = this.administratorFunctions.databaseHashMaps.getAdminHashMap().get(userName).getSalt();
		
		char[] defaultPlainAdminPassword = {'a','b','c',Character.toUpperCase(adminAttemptedPassword[0])};
		String hashedDefault = this.administratorFunctions.panelCentral.passwordEncryption.hashPassword(defaultPlainAdminPassword, salt);
		String currentAdminPasswordHash = this.administratorFunctions.databaseHashMaps.getAdminHashMap().get(userName).getHashedAdminPassword();
		
		return hashedDefault.equals(currentAdminPasswordHash);
	}
	
	// -----------------------------------------------------------------------------------
	void updateAdminNewPassword(String userName, char[] changeAdminPasswordReentered) {
		String newSaltString = this.administratorFunctions.panelCentral.passwordEncryption.generateSalt();
		String hashedAdminNewPassword = this.administratorFunctions.panelCentral.passwordEncryption.hashPassword(changeAdminPasswordReentered, newSaltString);
		
		if(this.administratorFunctions.databaseHashMaps.getAdminHashMap().isEmpty()) {
			System.out.println("Hash Map is Empty");
		}
		
		this.administratorFunctions.databaseHashMaps.getAdminHashMap().get(userName)
			.setChangedPassword(hashedAdminNewPassword, newSaltString);
		
		this.administratorFunctions.csvOperations.adminCSVOperations.overwriteAdminFile();
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
}
