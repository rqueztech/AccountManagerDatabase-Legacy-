package databaseproject;

import java.nio.CharBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InputOperations {

	private Pattern pattern;
	private Matcher matcher;

	private static final String PASSWORD_REQUIREMENTS_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}";
    private static final String LEGAL_CHARACTERS_REGEX = "^[A-Za-z0-9@$!%*#?&]+$";
    private static final String ONLY_LETTERS_AND_NUMBERS_REGEX = "^[A-Za-z0-9]+$";
    private static final String ONLY_LETTER_CHARACTERS_REGEX = "^[A-Za-z]+$";
    //private static final String ILLEGAL_CHARACTERS_REGEX = "[^A-Za-z0-9@$!%*#?&]";
    private static final String NO_UPPERCASE_CHARACTERS_REGEX = "[^A-Z]";
    private static final String NO_LOWERCASE_CHARACTERS_REGEX = "[^a-z]";
    private static final String NO_SPECIAL_CHARACTERS_REGEX = "[^@$!%*#?&]";
    private static final String NO_NUMBERS_REGEX = "[^0-9]";
	
	// ------------------------------------------------------------------------------------
	private boolean matchRegex(String input, String regex) {
		this.pattern = Pattern.compile(regex);
		this.matcher = pattern.matcher(input);
		
		return matcher.find();
	}
	
	// ------------------------------------------------------------------------------------
	boolean isMeetsPasswordRequirements(char[] passwordString) {
		return Pattern.matches(PASSWORD_REQUIREMENTS_REGEX, CharBuffer.wrap(passwordString));
	}
	
	// ------------------------------------------------------------------------------------
	boolean containsLegalCharacters(String loginString) {
		
		// Return true or false (dependent on if the regex matches)
		return matchRegex(loginString, LEGAL_CHARACTERS_REGEX);
	}
	
	// ------------------------------------------------------------------------------------
	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	boolean isOnlyLettersAndNumbers(String loginString) {
		
		return matchRegex(loginString, ONLY_LETTERS_AND_NUMBERS_REGEX);
	}
	
	// ------------------------------------------------------------------------------------
	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	boolean isOnlyLetterCharacters(String loginString) {

		return matchRegex(loginString, ONLY_LETTER_CHARACTERS_REGEX);
	}
	
	// ======== These Regular Expressions will give specific feedback for missing required fields
	// ------------------------------------------------------------------------------------
	//boolean isIllegalCharactersFound(String input) {
	//	return matchRegex(input, ILLEGAL_CHARACTERS_REGEX);
	//}
	
	// ------------------------------------------------------------------------------------
	private boolean isNoUpperCaseCharacters(String input) {
		return matchRegex(input, NO_UPPERCASE_CHARACTERS_REGEX);
	}
	
	// ------------------------------------------------------------------------------------
	private boolean isNoLowerCaseCharacters(String input) {
		return matchRegex(input, NO_LOWERCASE_CHARACTERS_REGEX);
	}
	
	// ------------------------------------------------------------------------------------
	private boolean isNoSpecialCharacters(String input) {
		return matchRegex(input, NO_SPECIAL_CHARACTERS_REGEX);
	}
	
	// ------------------------------------------------------------------------------------
	private boolean isNoNumbersFound(String input) {
		return matchRegex(input, NO_NUMBERS_REGEX);
	}
	
	// ------------------------------------------------------------------------------------
	// These regular expressions may seem redundant, but they search the string of text for any
	// Negated characters (example, no 0's found, etc...). They are much more loose than the other
	// Regex, therefore should only be used for messages and nowhere else.
	String passwordEnteredFeedback(String changePasswordEntered, String changePasswordReEntered) {
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
		// if(isIllegalCharactersFound(changePasswordEntered)) {
		//	feedbackString += "Error: Illegal Characters Entered\n";
		// }
		
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
