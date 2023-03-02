package databaseproject;

class UserFunctions {
	private AdministratorFunctions administratorFunctions;

	//-----------------------------------------------------------------------------------
	UserFunctions(AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
	}
	
	// -----------------------------------------------------------------------------------
	boolean searchUser(String userName) {
		boolean result = false;
		
		// **LOG WILL BE PUT IN THIS FUNCTION
		if(this.administratorFunctions.databaseHashMaps.getUserHashMap() != null 
		&& this.administratorFunctions.databaseHashMaps.getUserHashMap().get(userName) != null) {
			result = true;
		}		
		
		else {
			System.out.println("MASSIVE ERROR: NO USER ENTERED");
		}
			
		return result;
	}
}
