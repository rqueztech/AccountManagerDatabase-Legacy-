package databaseproject;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class InputOperations {

	public Pattern pattern;
	public Matcher matcher;

	// ------------------------------------------------------------------------------------
	// legalCharactersEntered: Tests developed - passes all tests

	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	public boolean legalCharactersEntered(String loginString) {

		// Assume the entry is not legal by default. If all criteria match, set
		// legalCharacters to true.
		boolean legalCharacters = false;

		// Ensures that: only the included characters have been inserted. Return false
		// if
		// Any character that doesn't match is inserted by the end-user
		this.pattern = Pattern.compile("^[A-Za-z0-9@$!%*#?&]+$");
		this.matcher = pattern.matcher(loginString);
		legalCharacters = matcher.find();

		return legalCharacters;
	}
	
	// ------------------------------------------------------------------------------------
	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	public boolean onlyLettersAndNumbers(String loginString) {

		// Assume the entry is not legal by default. If all criteria match, set
		// legalCharacters to true.
		boolean legalCharacters = false;

		// Ensures that: only the included characters have been inserted. Return false
		// if
		// Any character that doesn't match is inserted by the end-user
		this.pattern = Pattern.compile("^[A-Za-z0-9]+$");
		this.matcher = pattern.matcher(loginString);
		legalCharacters = matcher.find();
		
		return legalCharacters;
	}
	
	// ------------------------------------------------------------------------------------
	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	public boolean onlyLetterCharacters(String loginString) {

		// Assume the entry is not legal by default. If all criteria match, set
		// legalCharacters to true.
		boolean legalCharacters = false;

		// Ensures that: only the included characters have been inserted. Return false
		// if
		// Any character that doesn't match is inserted by the end-user
		this.pattern = Pattern.compile("^[A-Za-z]+$");
		this.matcher = pattern.matcher(loginString);
		legalCharacters = matcher.find();

		return legalCharacters;
	}
	
	
	// ------------------------------------------------------------------------------------
	public boolean onlyNumberCharactersPassword(String passwordNumbersOnly) {

		// Assume that the number characters are only 
		boolean numberCharactersOnly = false;
		
		this.pattern = Pattern.compile("^[0-9]+$");
		this.matcher = pattern.matcher(passwordNumbersOnly);
		numberCharactersOnly = matcher.find();
		
		return numberCharactersOnly;
	}
	
	// ------------------------------------------------------------------------------------
	// These regular expressions may seem redundant, but they search the string of text for any
	// Negated characters (example, no 0's found, etc...). They are much more loose than the other
	// Regex, therefore should only be used for messages and nowhere else.
	public String passwordEnteredFeedback(String changePasswordEntered, String changePasswordReEntered) {
		String feedbackString = "";
		
		boolean containsCharacters = false;
		
		if(!changePasswordEntered.equals(changePasswordReEntered)) {
			feedbackString += "Error: Passwords Must Match\n";
		}
		
			
		// ********** MINIMUM LENGTH NOT MET **********
		if(changePasswordEntered.length() < 8 || changePasswordReEntered.length() < 8) {
			feedbackString += "Error: Minimum 8 Characters Required\n";
		}
		
		// ********** ILLEGAL CHARACTERS FOUND **********
		this.pattern = Pattern.compile("(?=.*[^@$!%*#?&])[^@$!%*#?&]+");
		this.matcher = pattern.matcher(changePasswordEntered + feedbackString);
		containsCharacters = matcher.find();
		
		if(containsCharacters) {
			feedbackString += "Error: Illegal Characters Entered\n";
		}
		
		// ********** NO UPPER CASE CHARACTERS FOUND **********
		this.pattern = Pattern.compile("(?=.*[A-Z])[A-Z]+");
		this.matcher = pattern.matcher(changePasswordEntered + feedbackString);
		containsCharacters = matcher.find();
		
		if(!containsCharacters) {
			feedbackString += "Error: Minimum One Uppercase Character A-Z Required\n";
		}
		
		// ********** NO LOWER CASE CHARACTERS FOUND **********
		this.pattern = Pattern.compile("(?=.*[a-z])[a-z]+");
		this.matcher = pattern.matcher(changePasswordEntered + feedbackString);
		containsCharacters = matcher.find();
		
		if(!containsCharacters) {
			feedbackString += "Error: Minimum One Lowercase Character a-z Required\n";
		}
		
				
		// ********** NO SPECIAL CHARACERS FOUND **********
		this.pattern = Pattern.compile("(?=.*[@$!%*#?&])[@$!%*#?&]+");
		this.matcher = pattern.matcher(changePasswordEntered + feedbackString);
		containsCharacters = matcher.find();
		
		if(!containsCharacters) {
			feedbackString += "Error: Minimum One Special Character @$!%*#?& Required\n";
		}
				
		// ********** NO NUMBERS FOUND **********
		// TEST TO SEE IF NO NUMBERS WERE FOUND
		this.pattern = Pattern.compile("(?=.*[0-9])[0-9]+");
		this.matcher = pattern.matcher(changePasswordEntered + feedbackString);
		containsCharacters = matcher.find();
		
		if(!containsCharacters) {
			feedbackString += "Error: Minimum One Digit 0-9 Required\n";
		}
				
		return feedbackString;
	}
	
	// MIGHT GET REMOVED
	// ------------------------------------------------------------------------------------
	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	public boolean onlyNumberCharacters(int empNo) {

		// Assume the entry is not legal by default. If all criteria match, set
		// legalCharacters to true.
		boolean legalCharacters = false;
		String empNoString = Integer.toString(empNo);

		// Ensures that: only the included characters have been inserted. Return false
		// if
		// Any character that doesn't match is inserted by the end-user
		this.pattern = Pattern.compile("[^@$!%*#?&]+$");
		this.matcher = pattern.matcher(empNoString);
		legalCharacters = matcher.find();

		return legalCharacters;
	}

	// -----------------------------------------------------------------------------------
	// passwordRequirements: Tests developed - passes all tests

	// Check to see if the minimum requirements for the password have been met.
	// If this is not the case, it will return false.
	public boolean passwordRequirements(String passwordString) {
		boolean passwordRequirementsMet = false;

		// Ensures that: The password contains one uppercase, one lowercase, and at
		// least 8 characters
		passwordRequirementsMet = Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}",
				passwordString);

		return passwordRequirementsMet;
	}
}
