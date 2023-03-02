package databaseproject;

class ConfigurationOperations {
	
	public AdministratorFunctions administratorFunctions;
	//public ConfigurationOperations configurationOperations;
	
	public String configHeader;
	
	private String administratorPassphrase;
	private String admPassphraseSalt;
	private int userNo;
	private int admNo;
	
	//-------------------------------------------------------------------------------------
	ConfigurationOperations(AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
		//this.configurationOperations = administratorFunctions.configurationOperations;
		this.configHeader = "userNoCounter,admNoCounter,adPhrase,adSalt\n";
	}
	
	//-------------------------------------------------------------------------------------
	void updateConfiguration(int userNo, int admNo, String adminPassphrase, String salt) {
		this.userNo = userNo;
		this.admNo = admNo;
		this.administratorPassphrase = adminPassphrase;
		this.admPassphraseSalt = salt;
	}
	
	//-------------------------------------------------------------------------------------
	String getAdminPassphrase() {
		return this.administratorPassphrase;
	}
	
	// ------------------------------------------------------------------------------------
	// CONFIGURE ADMIN
	void createAdministrativePassphrase(char[] passphrase) {
		String salt = this.administratorFunctions.panelCentral.passwordEncryption.generateSalt();
		String encryptedPassphrase = this.administratorFunctions.panelCentral.passwordEncryption.hashPassword(passphrase, salt);
		
		this.setAdministrativePassphrase(encryptedPassphrase);
		this.setSalt(salt);
		
		this.administratorFunctions.csvOperations.configurationCSVOperations.overwriteConfigFile();
	}
	
	
	//-------------------------------------------------------------------------------------
	String getConfigurationString() {
		return String.format("%s%s,%s,%s,%s",
				this.configHeader,
				this.userNo,
				this.admNo,
				this.getAdminPassphrase(),
				this.getSalt()
			);
	}

	//-------------------------------------------------------------------------------------
	void setSalt(String salt) {
		if(salt.length() == 128) {
			this.admPassphraseSalt = salt;
		}
	}

	//-----------------------------------------------------------------------------------
	void setAdministrativePassphrase(String passphrase) {
		this.administratorPassphrase = passphrase;
	}
	
	//-------------------------------------------------------------------------------------
	String getSalt() {
		return this.admPassphraseSalt;
	}
	
	//-------------------------------------------------------------------------------------
	int getUserNo() {
		return this.userNo;
	}
	
	//-------------------------------------------------------------------------------------
	void increaseUserNo() {
		this.userNo++;
	}
	
	//-------------------------------------------------------------------------------------
	int getAdmNo() {
		return this.admNo;
	}
	
	//-------------------------------------------------------------------------------------
	void increaseAdmNo() {
		this.admNo++;
	}
}
