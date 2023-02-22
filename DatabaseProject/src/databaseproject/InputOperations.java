package databaseproject;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class InputOperations {

	public Pattern pattern;
	public Matcher matcher;

	// ------------------------------------------------------------------------------------
	public boolean matchRegex(String input, String regex) {
		this.pattern = Pattern.compile(regex);
		this.matcher = pattern.matcher(input);
		
		return matcher.find();
	}
	
	// ------------------------------------------------------------------------------------
	public boolean isMeetsPasswordRequirements(String passwordString) {

		return matchRegex(passwordString, "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}");
	}
	
	// ------------------------------------------------------------------------------------
	public boolean isLegalCharactersEntered(String loginString) {
		
		// Return true or false (dependent on if the regex matches)
		return matchRegex(loginString, "^[A-Za-z0-9@$!%*#?&]+$");
	}
	
	// ------------------------------------------------------------------------------------
	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	public boolean isOnlyLettersAndNumbers(String loginString) {
		
		return matchRegex(loginString, "^[A-Za-z0-9]+$");
	}
	
	// ------------------------------------------------------------------------------------
	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	public boolean isOnlyLetterCharacters(String loginString) {

		return matchRegex(loginString, "^[A-Za-z]+$");
	}
	
	// ======== These Regular Expressions will give specific feedback for missing required fields
	// ------------------------------------------------------------------------------------
	public boolean isIllegalCharactersFound(String input) {
		return matchRegex(input, "[^A-Za-z0-9@$!%*#?&]");
	}
	
	// ------------------------------------------------------------------------------------
	public boolean isNoUpperCaseCharacters(String input) {
		return matchRegex(input, "[^A-Z]");
	}
	
	// ------------------------------------------------------------------------------------
	public boolean isNoLowerCaseCharacters(String input) {
		return matchRegex(input, "[^A-Z]");
	}
	
	// ------------------------------------------------------------------------------------
	public boolean isNoSpecialCharacters(String input) {
		return matchRegex(input, "[^@$!%*#?&]");
	}
	
	// ------------------------------------------------------------------------------------
	public boolean isNoNumbersFound(String input) {
		return matchRegex(input, "[^0-9]");
	}
	
	// ------------------------------------------------------------------------------------
	// These regular expressions may seem redundant, but they search the string of text for any
	// Negated characters (example, no 0's found, etc...). They are much more loose than the other
	// Regex, therefore should only be used for messages and nowhere else.
	public String passwordEnteredFeedback(String changePasswordEntered, String changePasswordReEntered) {
		String feedbackString = "";
		
		// ********** PASSWORDS DON'T MATCH **********
		if(!changePasswordEntered.equals(changePasswordReEntered)) {
			feedbackString += "Error: Passwords Must Match\n";
		}
		
		
		// ********** MINIMUM LENGTH NOT MET **********
		if(changePasswordEntered.length() < 8 || changePasswordReEntered.length() < 8) {
			feedbackString += "Error: Minimum 8 Characters Required\n";
		}
		
		// ********** ILLEGAL CHARACTERS FOUND **********
		if(isIllegalCharactersFound(changePasswordEntered)) {
			feedbackString += "Error: Illegal Characters Entered\n";
		}
		
		// ********** NO UPPER CASE CHARACTERS FOUND **********
		if(isNoUpperCaseCharacters(changePasswordEntered)) {
			feedbackString += "Error: Minimum One Uppercase Character A-Z Required\n";
		}
		
		// ********** NO LOWER CASE CHARACTERS FOUND **********
		if(isNoLowerCaseCharacters(changePasswordEntered)) {
			feedbackString += "Error: Minimum One Lowercase Character a-z Required\n";
		}
		
				
		// ********** NO SPECIAL CHARACERS FOUND **********
		if(isNoSpecialCharacters(changePasswordEntered)) {
			feedbackString += "Error: Minimum One Special Character @$!%*#?& Required\n";
		}
				
		// ********** NO NUMBERS FOUND **********
		// TEST TO SEE IF NO NUMBERS WERE FOUND
		if(isNoNumbersFound(changePasswordEntered)) {
			feedbackString += "Error: Minimum One Digit 0-9 Required\n";
		}
				
		return feedbackString;
	}
}
