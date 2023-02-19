package databaseproject;

public class ConfigurationOperations {
	
	public AdministratorFunctions administratorFunctions;
	//public ConfigurationOperations configurationOperations;
	
	public String configHeader;
	
	private String administratorPassphrase;
	private String admPassphraseSalt;
	private int empNo;
	private int admNo;
	
	//-------------------------------------------------------------------------------------
	public ConfigurationOperations(AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
		//this.configurationOperations = administratorFunctions.configurationOperations;
		this.configHeader = "empNoCounter,admNoCounter,adPhrase,adSalt\n";
	}
	
	//-------------------------------------------------------------------------------------
	public void updateConfiguration(int empNo, int admNo, String adminPassphrase, String salt) {
		this.empNo = empNo;
		this.admNo = admNo;
		this.administratorPassphrase = adminPassphrase;
		this.admPassphraseSalt = salt;
	}
	
	//-------------------------------------------------------------------------------------
	public String getAdminPassphrase() {
		return this.administratorPassphrase;
	}
	
	// ------------------------------------------------------------------------------------
	// CONFIGURE EMPLOYEE
	public void createAdministrativePassphrase(String passphrase) {
		String salt = this.administratorFunctions.panelCentral.passwordEncryption.generateSalt();
		String encryptedPassphrase = this.administratorFunctions.panelCentral.passwordEncryption.hashPassword(passphrase, salt);
		
		this.setAdministrativePassphrase(encryptedPassphrase);
		this.setSalt(salt);
		
		
		this.administratorFunctions.csvOperations.overwriteConfigFile();
	}
	
	//-------------------------------------------------------------------------------------
	public String getConfigurationString() {
		return String.format("%s%s,%s,%s,%s",
				this.configHeader,
				this.empNo,
				this.admNo,
				this.getAdminPassphrase(),
				this.getSalt()
			);
	}

	//-------------------------------------------------------------------------------------
	public void setSalt(String salt) {
		this.admPassphraseSalt = salt;
	}
	
	public void setAdministrativePassphrase(String passphrase) {
		this.administratorPassphrase = passphrase;
	}
	
	//-------------------------------------------------------------------------------------
	public String getSalt() {
		return this.admPassphraseSalt;
	}
	
	//-------------------------------------------------------------------------------------
	public int getEmpNo() {
		return this.empNo;
	}
	
	//-------------------------------------------------------------------------------------
	public void increaseEmpNo() {
		this.empNo++;
	}
	
	//-------------------------------------------------------------------------------------
	public int getAdmNo() {
		return this.admNo;
	}
	
	//-------------------------------------------------------------------------------------
	public void increaseAdmNo() {
		this.admNo++;
	}
}
