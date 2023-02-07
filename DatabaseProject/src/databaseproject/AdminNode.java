package databaseproject;

public class AdminNode {
	private String firstName;
	private String lastName;
	private String AdminName;
	private String AdminPassword;
	private String salt;
	private int empNo;
	
	public InputOperations inputOperations;
	
	//-----------------------------------------------------------------------------------
	public AdminNode() {
		this.inputOperations = new InputOperations();
	}
	
	//-----------------------------------------------------------------------------------
	// Specific constructor to hold all information particular to a given Admin.
	public AdminNode(String AdminName, String firstName, String lastName, 
			String AdminPassword, String salt, int empNo) {
		this.inputOperations = new InputOperations();
		
		this.setAdminName(AdminName);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setAdminPassword(AdminPassword);
		this.setSalt(salt);
		this.setEmpNo(empNo);
	}

	//-----------------------------------------------------------------------------------
	// Set the Admin password here. If encryption fails, an error message will be printed.
	// If the password meets all requirements, it will be accepted
	
	public void setAdminPassword(String AdminPassword) {
		
		if(AdminPassword.length() == 128) {
			this.AdminPassword = AdminPassword;
		}
	}
	
	//-----------------------------------------------------------------------------------
	// This method is going to increase the Admin number
	
	public void setEmpNo(int empNo) {
		if(inputOperations.onlyNumberCharacters(empNo)) {
			this.empNo = empNo;
		}
		
		else {
			System.out.println("NOT NUMBERS");
		}
	}
	
	//-----------------------------------------------------------------------------------
	public void setAdminName(String AdminName) {
		if(inputOperations.onlyLettersAndNumbers(AdminName)) {
			this.AdminName = AdminName;
		}
		
		else {
			System.out.println("Only letters and nubmers allowed");
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setFirstName(String firstName) {
		
		// Perform validation check to make sure only letter characters
		// Are used for the first name
		if(inputOperations.onlyLetterCharacters(firstName)
				&& firstName.length() > 1) {
			this.firstName = firstName;
		}
		
		else {
			System.out.println("First Name Illegal");
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setLastName(String lastName) {
		if(inputOperations.onlyLetterCharacters(lastName)
				&& lastName.length() > 1) {
			this.lastName = lastName;
		}
		
		else {
			System.out.println("Last Name Illegal");
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setSalt(String salt) {
		if(salt.length() == 32) {
			this.salt = salt;
		}
		
		else {
			System.out.println("Salt Illegeal");
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public String getAdminPassword() {
		return this.AdminPassword;
	}
	
	//-----------------------------------------------------------------------------------

	public String getAdminName() {
		return this.AdminName;
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
		this.setAdminPassword(changedPassword);
		this.setSalt(changedSalt);
	}
	
	//-----------------------------------------------------------------------------------
	
	//-----------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		String result = "";
		result = String.format("%s,%s,%s,%s,%s,%s\n", this.getAdminName(), this.getFirstName(), this.getLastName(),
				this.getAdminPassword(), this.getSalt(), this.getEmpNo());
		
		return result;
	}
}
