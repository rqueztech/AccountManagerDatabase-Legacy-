package databaseproject;

import javax.swing.JOptionPane;

public class EmployeeNode {
	private String firstName;
	private String lastName;
	private String userName;
	private String userPassword;
	private String salt;
	private String gender;
	private int empNo;
	
	public InputOperations inputOperations;
	
	//-----------------------------------------------------------------------------------
	public EmployeeNode() {
		this.inputOperations = new InputOperations();
	}
	
	//-----------------------------------------------------------------------------------
	// Specific constructor to hold all information particular to a given employee.
	public EmployeeNode(String userName, String firstName, String lastName, 
			String gender, String userPassword, String salt, int empNo) {
		this.inputOperations = new InputOperations();
		
		this.setUserName(userName);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setGender(gender);
		this.setUserPassword(userPassword);
		this.setSalt(salt);
		this.setEmpNo(empNo);
	}

	//-----------------------------------------------------------------------------------
	// Set the user password here. If encryption fails, an error message will be printed.
	// If the password meets all requirements, it will be accepted
	
	public void setUserPassword(String userPassword) {
		
		if(userPassword.length() == 128) {
			this.userPassword = userPassword;
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Password Hash Error: In Password", "SETTER TEST FAILED", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//-----------------------------------------------------------------------------------
	// This method is going to increase the employee number
	
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
		
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setUserName(String userName) {
		if(inputOperations.isOnlyLettersAndNumbers(userName)) {
			this.userName = userName;
		}
		
		else {
			JOptionPane.showMessageDialog(null, "User Name Allowed Special Characters", "SETTER TEST FAILED", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "First Name Allowed Non-Numbers", "SETTER TEST FAILED", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setLastName(String lastName) {
		if(this.inputOperations.isOnlyLetterCharacters(lastName)
				&& lastName.length() > 1) {
			this.lastName = lastName;
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Last Name Allowed Non-Numbers", "SETTER TEST FAILED", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setSalt(String salt) {
		if(salt.length() == 128) {
			this.salt = salt;
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Salt not Valid!!!", "SETTER TEST FAILED", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public void setGender(String gender) {
		if(!gender.equals("Select")) { 
			this.gender = gender;
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Male/Female Not Entered", "SETTER TEST FAILED", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//-----------------------------------------------------------------------------------
	
	public String getHashedUserPassword() {
		return this.userPassword;
	}
	
	//-----------------------------------------------------------------------------------
	
	public String getGender() {
		return this.gender;
	}
	
	//-----------------------------------------------------------------------------------

	public String getUserName() {
		return this.userName;
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
		this.setUserPassword(changedPassword);
		this.setSalt(changedSalt);
	}
	
	@Override
	public String toString() {
		String result = "";
		result = String.format("%s,%s,%s,%s,%s,%s,%s\n", this.getUserName(), this.getFirstName(), this.getLastName(),
				this.getGender(), this.getHashedUserPassword(), this.getSalt(), this.getEmpNo());
		
		return result;
	}
}
