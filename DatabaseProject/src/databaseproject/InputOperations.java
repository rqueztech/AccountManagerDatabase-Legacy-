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
	String isMeetsPasswordRequirements(char[] passwordString) {
		String message = "";
		
		if(!Pattern.matches(PASSWORD_REQUIREMENTS_REGEX, CharBuffer.wrap(passwordString))) {
			message += "Error: Does Not Meet Password Requirements";
		}

		return message;
	}

	// ------------------------------------------------------------------------------------
	String containsLegalCharacters(String loginString) {
		String message = "";
		
		// Return true or false (dependent on if the regex matches)
		if(!matchRegex(loginString, LEGAL_CHARACTERS_REGEX)) {
			message += "Error: Illegal Characters Detected";
		}

		return message;
	}

	// ------------------------------------------------------------------------------------
	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	String isOnlyLettersAndNumbers(String loginString) {
		String message = "";
		
		if(!matchRegex(loginString, ONLY_LETTERS_AND_NUMBERS_REGEX)) {
			message += "Error: No Symbols Allowed";
		}

		return message;
	}

	// ------------------------------------------------------------------------------------
	// Check so see if input entered by the user is legal. If all characters
	// Entered are legal, then it will be return true. Otherwise, returns false.
	String isOnlyLetterCharacters(String loginString) {
		String message = "";
		
		if(!matchRegex(loginString, ONLY_LETTER_CHARACTERS_REGEX)) {
			message += "Error: No Numbers And Symbols Allowed";
		}

		return message;
	}

	// ------------------------------------------------------------------------------------
	String isNoUpperCaseCharacters(String input) {
		String message = "";
		
		if(matchRegex(input, NO_UPPERCASE_CHARACTERS_REGEX)) {
			message += "Error: Please Enter UpperCase Character";
		}

		return message;
	}

	// ------------------------------------------------------------------------------------
	String isNoLowerCaseCharacters(String input) {
		String message = "";
		
		if(matchRegex(input, NO_LOWERCASE_CHARACTERS_REGEX)) {
			message += "Error: Please Enter LowerCase Character";
		}

		return message;
	}

	// ------------------------------------------------------------------------------------
	String isNoSpecialCharacters(String input) {
		String message = "";
		
		if(matchRegex(input, NO_SPECIAL_CHARACTERS_REGEX)) {
			message += "Error: Please Enter Special Character";
		}

		return message;
	}

	// ------------------------------------------------------------------------------------
	String isNoNumbersFound(String input) {
		String message = "";
		
		if(matchRegex(input, NO_NUMBERS_REGEX)) {
			message += "Error: Please Enter Number";
		}

		return message;
	}
}
