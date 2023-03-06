package databaseproject;

class AdminNode {
	private String firstName;
	private String lastName;
	private String adminName;
	private String adminPassword;
	private String salt;
	private int userNo;
	
	public InputOperations inputOperations;
	
	//-----------------------------------------------------------------------------------
	AdminNode() {
		this.inputOperations = new InputOperations();
	}
	
	//-----------------------------------------------------------------------------------
	// Specific constructor to hold all information particular to a given Admin.
	AdminNode(String adminName, String firstName, String lastName, 
			String adminPassword, String salt, int userNo) {
		this.inputOperations = new InputOperations();
		
		this.setAdminName(adminName);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setHashedAdminPassword(adminPassword);
		this.setSalt(salt);
		this.setUserNo(userNo);
	}

	//-----------------------------------------------------------------------------------
	// Set the Admin password here. If encryption fails, an error message will be printed.
	// If the password meets all requirements, it will be accepted
	
	void setHashedAdminPassword(String adminPassword) {
		
		if(new String(adminPassword).length() == 128) {
			this.adminPassword = adminPassword;
		}
	}
	
	//-----------------------------------------------------------------------------------
	// This method is going to increase the Admin number
	void setUserNo(int userNo) {
		if(Character.isDigit(userNo)) {
			this.userNo = userNo;
		}
	}
	
	//-----------------------------------------------------------------------------------
	void setAdminName(String adminName) {
		if(inputOperations.isOnlyLettersAndNumbers(adminName).isEmpty()) {
			this.adminName = adminName;
		}
		
		else {
			System.out.println("Only letters and nubmers allowed");
		}
	}
	
	//-----------------------------------------------------------------------------------	
	void setFirstName(String firstName) {
		
		// Perform validation check to make sure only letter characters
		// Are used for the first name
		if(inputOperations.isOnlyLetterCharacters(firstName).isEmpty()
				&& firstName.length() > 1) {
			this.firstName = firstName;
		}
		
		else {
			System.out.println("First Name Illegal");
		}
	}
	
	//-----------------------------------------------------------------------------------
	void setLastName(String lastName) {
		if(inputOperations.isOnlyLetterCharacters(lastName).isEmpty()
				&& lastName.length() > 1) {
			this.lastName = lastName;
		}
		
		else {
			System.out.println("Last Name Illegal");
		}
	}
	
	//-----------------------------------------------------------------------------------
	void setSalt(String salt) {
		if(salt.length() == 128) {
			this.salt = salt;
		}
		
		else {
			System.out.println("Salt Illegeal");
		}
	}
	
	//-----------------------------------------------------------------------------------
	String getHashedAdminPassword() {
		return this.adminPassword;
	}
	
	//-----------------------------------------------------------------------------------
	String getAdminName() {
		return this.adminName;
	}
	
	//-----------------------------------------------------------------------------------
	int getUserNo() {
		return this.userNo;
	}
	
	//-----------------------------------------------------------------------------------
	String getFirstName() {
		return this.firstName;
	}
	
	//-----------------------------------------------------------------------------------
	String getLastName() {
		return this.lastName;
	}
	
	//-----------------------------------------------------------------------------------
	String getSalt() {
		return this.salt;
	}
	
	//-----------------------------------------------------------------------------------
	void setChangedPassword(String changedPassword, String changedSalt) {
		this.setHashedAdminPassword(changedPassword);
		this.setSalt(changedSalt);
	}
	
	//-----------------------------------------------------------------------------------
	@Override
	public String toString() {
		String result = "";
		result = String.format("%s,%s,%s,%s,%s,%s\n", this.getAdminName(), this.getFirstName(), this.getLastName(),
				this.getHashedAdminPassword(), this.getSalt(), this.getUserNo());
		
		return result;
	}
}
