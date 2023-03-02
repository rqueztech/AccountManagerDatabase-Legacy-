package databaseproject;

class CSVOperations {
	//private AdministratorFunctions administratorFunctions;
	//private ConfigurationOperations configurationOperations;
	
	public AdminCSVOperations adminCSVOperations;
	public ConfigurationCSVOperations configurationCSVOperations;
	public UserCSVOperations userCSVOperations;
	
	// ------------------------------------------------------------------------------------
	CSVOperations(AdministratorFunctions administratorFunctions) {
		//this.administratorFunctions = administratorFunctions;
		//this.configurationOperations = administratorFunctions.configurationOperations;
		this.adminCSVOperations = new AdminCSVOperations(administratorFunctions);
		this.configurationCSVOperations = new ConfigurationCSVOperations(administratorFunctions);
		this.userCSVOperations = new UserCSVOperations(administratorFunctions);
	}
	
	// ------------------------------------------------------------------------------------
	// If no database exists, initiate a database file with the
	// Default header
	void initializeEssentialFiles() {
		this.adminCSVOperations.initializeAdminFile();
		this.userCSVOperations.initializeUserFile();
		this.configurationCSVOperations.initializeConfigFile();
	}
	
	// ------------------------------------------------------------------------------------
	// Initial file read operation
	void initializeInitialFileRead() {
		this.adminCSVOperations.readAdminFile();
		this.userCSVOperations.readUserFile();
		this.configurationCSVOperations.readConfigurationFile();
	}
}
