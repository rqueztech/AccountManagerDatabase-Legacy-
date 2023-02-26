package databaseproject;

public class AdminNode {
	private String firstName;
	private String lastName;
	private String adminName;
	private String adminPassword;
	private String salt;
	private int empNo;
	
	public InputOperations inputOperations;
	
	//-----------------------------------------------------------------------------------
	public AdminNode() {
		this.inputOperations = new InputOperations();
	}
	
	//-----------------------------------------------------------------------------------
	// Specific constructor to hold all information particular to a given Admin.
	public AdminNode(String adminName, String firstName, String lastName, 
			String adminPassword, String salt, int empNo) {
		this.inputOperations = new InputOperations();
		
		this.setAdminName(adminName);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setHashedAdminPassword(adminPassword);
		this.setSalt(salt);
		this.setEmpNo(empNo);
	}

	//-----------------------------------------------------------------------------------
	// Set the Admin password here. If encryption fails, an error message will be printed.
	// If the password meets all requirements, it will be accepted
	
	public void setHashedAdminPassword(String adminPassword) {
		
		if(new String(adminPassword).length() == 128) {
			this.adminPassword = adminPassword;
		}
	}
	
	//-----------------------------------------------------------------------------------
	// This method is going to increase the Admin number
	
	public void setEmpNo(int empNo) {
		if(Character.isDigit(empNo)) {
			this.empNo = empNo;
		}
	}
	
	//-----------------------------------------------------------------------------------
	public void setAdminName(String adminName) {
		if(inputOperations.isOnlyLettersAndNumbers(adminName)) {
			this.adminName = adminName;
		}
		
		else {
			System.out.println("Only letters and nubmers allowed");
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setFirstName(String firstName) {
		
		// Perform validation check to make sure only letter characters
		// Are used for the first name
		if(inputOperations.isOnlyLetterCharacters(firstName)
				&& firstName.length() > 1) {
			this.firstName = firstName;
		}
		
		else {
			System.out.println("First Name Illegal");
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setLastName(String lastName) {
		if(inputOperations.isOnlyLetterCharacters(lastName)
				&& lastName.length() > 1) {
			this.lastName = lastName;
		}
		
		else {
			System.out.println("Last Name Illegal");
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setSalt(String salt) {
		if(salt.length() == 128) {
			this.salt = salt;
		}
		
		else {
			System.out.println("Salt Illegeal");
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public String getHashedAdminPassword() {
		return this.adminPassword;
	}
	
	//-----------------------------------------------------------------------------------

	public String getAdminName() {
		return this.adminName;
	}
	
	//-----------------------------------------------------------------------------------
		
	public int getEmpNo() {
		return this.empNo;
	}
	
	//-----------------------------------------------------------------------------------
	
	public String getFirstName() {
		return this.firstName;
	}
	
	//-----------------------------------------------------------------------------------

	public String getLastName() {
		return this.lastName;
	}
	
	//-----------------------------------------------------------------------------------
	
	public String getSalt() {
		return this.salt;
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setChangedPassword(String changedPassword, String changedSalt) {
		this.setHashedAdminPassword(changedPassword);
		this.setSalt(changedSalt);
	}
	
	//-----------------------------------------------------------------------------------
	
	//-----------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		String result = "";
		result = String.format("%s,%s,%s,%s,%s,%s\n", this.getAdminName(), this.getFirstName(), this.getLastName(),
				this.getHashedAdminPassword(), this.getSalt(), this.getEmpNo());
		
		return result;
	}
}
