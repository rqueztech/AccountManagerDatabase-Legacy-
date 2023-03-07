package databaseproject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum ProgramLogs {
	ACCOUNT_CREATION_AGREEMENT("ACCOUNT CREATION AGREEMENT: Administrator has Agreed"),
    ACCOUNT_CREATION_FAILURE("ACCOUNT CREATION FAILURE: Account creation failed"),
    ACCOUNT_SEARCHED("ACCOUNT SEARCH: User Searched"),
    ACCOUNT_ADD_SUCCESS("ACCOUNT ADD SUCCESS: Account added successfully"),

    LOGIN_FAIL("LOGIN FAIL: Login Attempt Fail"),
    LOGIN_FAIL_INVALID_NAME("LOGIN FAILED: Invalid Name Entered"),
    LOGIN_SUCCESS("LOGIN SUCCESS: Login Success"),
    LOGOUT_SUCCESS("LOGOUT SUCCESS: Logged Out Successfully"),

    PASSWORD_CHANGE_CANCELLED("PASSWORD CHANGE CANCELLED: Password change has been cancelled by end-user"),
    PASSWORD_CHANGE_FAIL("PASSWORD CHANGE FAILED: Password Change Failed"),
    PASSWORD_CHANGE_ILLEGAL_CHARACTER("PASSWORD CHANGE FAILED: Illegal Character Entered"),
    PASSWORD_CHANGE_SUCCESS("PASSWORD CHANGE SUCCESS: Password Changed Successfully"),

    INITIAL_CONFIGURATION_FAILED("INITIAL CONFIGURATION: Initial Configuration Failed, No Changes Made/Saved..."),
    INITIAL_CONFIGURATION_PASSPHRASE_CHANGED("INITIAL CONFIGURATION: Passphrase Changed"),
    INITIAL_CONFIGURATION_STARTED("INITIAL CONFIGURATION: Begin Initial Configuration"),
    INITIAL_CONFIGURATION_SUCCESS("INITIAL CONFIGURATION: Initial Configuration Success");

    private final String logMessage;

    //-----------------------------------------------------------------------------------
    ProgramLogs(String logMessage) {
        this.logMessage = logMessage;
    }

    //-----------------------------------------------------------------------------------
    public String getLogMessage() {
        return logMessage;
    }

    //-----------------------------------------------------------------------------------
    public String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    
    //-----------------------------------------------------------------------------------
    /*
	    Function overloading to account for logs that are general (logged out) and specific
	    (currently logged in)
    */
    
    // Print time stamp (not currently logged in) : excludes usrName
    public String printTimeStampUserName(String usrType) {
        return String.format(getCurrentLocalDateTimeStamp() + " - (%s) -> ", usrType);
    }
    
    //-----------------------------------------------------------------------------------
    // Print time stamp (currently logged in to system) : includes usrName
    public String printTimeStampUserName(String usrType, String usrName) {
        return String.format(getCurrentLocalDateTimeStamp() + " - (%s)%s -> ", usrType, usrName);
    }
    
    //-----------------------------------------------------------------------------------
    /*
    	Function overloading to account for logs that are general (logged out) and specific
	    (currently logged in)
	*/
    
    // Log current event and print to console (not currently logged in) : excludes usrName
    public void logCurrentEvent(String usrType, ProgramLogs eventLogged) {
        String logString = printTimeStampUserName(usrType) + eventLogged.getLogMessage();
        System.out.println(logString);
        writeToLog(logString);
    }
    
    //-----------------------------------------------------------------------------------
    // Log current event and print to console (currently logged in to system) : includes usrName
    public void logCurrentEvent(String usrType, String usrName, ProgramLogs eventLogged) {
        String logString = printTimeStampUserName(usrType, usrName) + eventLogged.getLogMessage();
        System.out.println(logString);
        writeToLog(logString);
    }

    //-----------------------------------------------------------------------------------
    // Log when a new session is initiated. This is only called when the session begins.
    public void logNewSessionInitiated() {
        String defaultLogString = 
        	String.format(
        			"*--------------START LOGGING--------------\n"
        			+ "*Separate Session For: " 
        			+ getCurrentLocalDateTimeStamp()
        	);
        
        System.out.println(defaultLogString);
        writeToLog(defaultLogString);
    }

    //-----------------------------------------------------------------------------------
    // This portion will actually write it to the log.txt file. This is where the 
    // Real logging occurs.
    private void writeToLog(String message) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)))) {
            out.println(message);
        } 
        
        catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
