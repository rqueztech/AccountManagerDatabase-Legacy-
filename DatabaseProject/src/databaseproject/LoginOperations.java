package databaseproject;

class LoginOperations {
	private String currentUser;
	private String userType;
	private boolean loggedInStatus;
	
	private PanelCentral panelCentral;
	private ProgramLogs programLogs;
	
	public UserFunctions userFunctions;
	
	UserLoginOperations userLoginOperations;
	AdminLoginOperations adminLoginOperations;
	
	// -----------------------------------------------------------------------------------
	LoginOperations(PanelCentral panelCentral, AdministratorFunctions administratorFunctions) {
		this.panelCentral = panelCentral;
		this.programLogs = this.panelCentral.programLogs;
		this.userLoginOperations = new UserLoginOperations(administratorFunctions);
		this.adminLoginOperations = new AdminLoginOperations(administratorFunctions);
		this.userFunctions = new UserFunctions(administratorFunctions);
	}
	
	// -----------------------------------------------------------------------------------
	private void setLoggedInStatus(boolean loggedInStatus) {
		this.loggedInStatus = loggedInStatus;
	}
	
	// -----------------------------------------------------------------------------------
	// Dead code?
	boolean getLoggedInStatus() {
		return this.loggedInStatus;
	}
	
	// -----------------------------------------------------------------------------------
	void setLogUserIn(String currentUser, boolean loginUser, String userType) {
		this.setCurrentUser(currentUser);
		this.setLoggedInStatus(loginUser);
		this.setUserType(userType);
	}
	
	// -----------------------------------------------------------------------------------
	private void setUserType(String userType) {
		this.userType = userType;
	}
	
	// -----------------------------------------------------------------------------------
	// Dead code?
	String getUserType() {
		return this.userType;
	}
	
	// -----------------------------------------------------------------------------------
	void logOutuser() {
		String currentUserForLog = this.currentUser;
		this.currentUser = "";
		this.loggedInStatus = false;
		this.panelCentral.programLogs.logCurrentEvent(this.userType, currentUserForLog, this.programLogs.getLOGOUT_SUCCESS());
	}
	
	// -----------------------------------------------------------------------------------
	private void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	
	// -----------------------------------------------------------------------------------
	String getCurrentUser() {
		return this.currentUser;
	}
}
