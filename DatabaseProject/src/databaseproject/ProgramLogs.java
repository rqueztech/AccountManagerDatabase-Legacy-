package databaseproject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ProgramLogs {
	
	/*
	 * Account Logs: These logs annotate any events that occur in regards to account creation,
	 * deletion, and querying.
	 * */
	private final static String ACCOUNT_CREATION_FAILURE = "ACCOUNT CREATION FAILURE: Account creation failed";
	private final static String ACCOUNT_SEARCHED = "ACCOUNT SEARCH: User Searched";
	private final static String ACCOUNT_ADD_SUCCESS = "ACCOUNT ADD SUCCESS: Account added successfully";
	
	private final static String LOGIN_FAIL = "LOGIN FAIL: Login Attempt Fail";
	private final static String LOGIN_FAIL_INVALID_NAME = "LOGIN FAILED: Invalid Name Entered";
	private final static String LOGIN_SUCCESS = "LOGIN SUCCESS: Login Success";
	private final static String LOGOUT_SUCCESS = "LOGOUT SUCCESS: Logged Out Successfully";
	
	private final static String PASSWORD_CHANGE_CANCELLED = "PASSWORD CHANGE CANCELLED: Password change has been cancelled by end-user";
	private final static String PASSWORD_CHANGE_FAIL = "PASSWORD CHANGE FAILED: Password Change Failed";
	private final static String PASSWORD_CHANGE_ILLEGAL_CHARACTER = "PASSWORD CHANGE FAILED: Illegal Character Entered";
	private final static String PASSWORD_CHANGE_SUCCESS = "PASSWORD CHANGE SUCCESS: Password Changed Successfully";
	
	private final static String INITIAL_CONFIGUARTION_FAILED = "INITIAL CONFIGURATION: Initial Configuration Failed, No Changes Made/Saved...";
	private final static String INITIAL_CONFIGUARTION_PASSPHRASE_CHANGED = "INITIAL CONFIGURATION: Passphrase Changed";
	private final static String INITIAL_CONFIGUARTION_STARTED = "INITIAL CONFIGURATION: Begin Initial Configuration";
	private final static String INITIAL_CONFIGUARTION_SUCCESS = "INITIAL CONFIGURATION: Initial Configuration Success";

	//-----------------------------------------------------------------------------------
	ProgramLogs() {
		this.logNewSessionInitiated();
	}
	
	//-----------------------------------------------------------------------------------
	void logNewSessionInitiated() {
		String defaultLogString = String.format("*--------------START LOGGING--------------\n" + "*Separate Session For: " 
				+ this.getCurrentLocalDateTimeStamp());
		
		System.out.println(defaultLogString);
		this.writeToLog(defaultLogString);
	}
	
	//-----------------------------------------------------------------------------------
	String getCurrentLocalDateTimeStamp() {
	    return LocalDateTime.now()
	       .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}
	
	//-----------------------------------------------------------------------------------
	String printTimeStampUserName(String usrType, String usrName) {
		String logStringHead = String.format(this.getCurrentLocalDateTimeStamp() + " - " 
				+ "(" + usrType + ")" + usrName + " -> ");
		
		return logStringHead;
	}
	
	//-----------------------------------------------------------------------------------
	void logCurrentEvent(String usrType, String usrName, String eventLogged) {
		String logString = "";
		
		logString += String.format(
				this.printTimeStampUserName(usrType, usrName));
		
		if(eventLogged.equals(getLOGIN_SUCCESS())) {
			logString += getLOGIN_SUCCESS();
		}

		if(eventLogged.equals(getLOGIN_FAIL_INVALID_NAME())) {
			logString += getLOGIN_FAIL_INVALID_NAME();
		}

		if(eventLogged.equals(getLOGIN_FAIL())) {
			logString += getLOGIN_FAIL();
		}

		if(eventLogged.equals(getLOGOUT_SUCCESS())) {
			logString += getLOGOUT_SUCCESS();
		}

		if(eventLogged.equals(getPASSWORD_CHANGE_SUCCESS())) {
			logString += getPASSWORD_CHANGE_SUCCESS();
		}

		if(eventLogged.equals(getPASSWORD_CHANGE_FAIL())) {
			logString += getPASSWORD_CHANGE_FAIL();
		}

		if(eventLogged.equals(getPASSWORD_CHANGE_ILLEGAL_CHARACTER())) {
			logString += getPASSWORD_CHANGE_ILLEGAL_CHARACTER();
		}

		if(eventLogged.equals(getPASSWORD_CHANGE_CANCELLED())) {
			logString += getPASSWORD_CHANGE_CANCELLED();
		}
		
		if(eventLogged.equals(getACCOUNT_SEARCHED())) {
			logString += getACCOUNT_SEARCHED();
		}
		
		if(eventLogged.equals(getINITIAL_CONFIGUARTION_STARTED())) {
			logString += getINITIAL_CONFIGUARTION_STARTED();
		}
		
		if(eventLogged.equals(getINITIAL_CONFIGUARTION_FAILED())) {
			logString += getINITIAL_CONFIGUARTION_FAILED();
		}
		
		if(eventLogged.equals(getINITIAL_CONFIGUARTION_SUCCESS())) {
			logString += getINITIAL_CONFIGUARTION_SUCCESS();
		}
		
		if(eventLogged.equals(getINITIAL_CONFIGUARTION_SUCCESS())) {
			logString += getINITIAL_CONFIGUARTION_SUCCESS();
		}
		
		if(eventLogged.equals(getINITIAL_CONFIGUARTION_PASSPHRASE_CHANGED())) {
			logString += getINITIAL_CONFIGUARTION_PASSPHRASE_CHANGED();
		}
		
		System.out.println(logString);
		
		this.writeToLog(logString);
	}

	//-----------------------------------------------------------------------------------
	String getLOGIN_SUCCESS() {
		return LOGIN_SUCCESS;
	}

	String getLOGIN_FAIL_INVALID_NAME() {
		return LOGIN_FAIL_INVALID_NAME;
	}

	//-----------------------------------------------------------------------------------
	String getLOGIN_FAIL() {
		return LOGIN_FAIL;
	}

	//-----------------------------------------------------------------------------------
	String getLOGOUT_SUCCESS() {
		return LOGOUT_SUCCESS;
	}

	//-----------------------------------------------------------------------------------
	String getPASSWORD_CHANGE_SUCCESS() {
		return PASSWORD_CHANGE_SUCCESS;
	}

	//-----------------------------------------------------------------------------------
	String getPASSWORD_CHANGE_FAIL() {
		return PASSWORD_CHANGE_FAIL;
	}

	//-----------------------------------------------------------------------------------
	String getPASSWORD_CHANGE_ILLEGAL_CHARACTER() {
		return PASSWORD_CHANGE_ILLEGAL_CHARACTER;
	}

	//-----------------------------------------------------------------------------------
	String getPASSWORD_CHANGE_CANCELLED() {
		return PASSWORD_CHANGE_CANCELLED;
	}

	//-----------------------------------------------------------------------------------
	String getACCOUNT_ADD_SUCCESS() {
		return ACCOUNT_ADD_SUCCESS;
	}

	//-----------------------------------------------------------------------------------
	String getACCOUNT_CREATION_FAILURE() {
		return ACCOUNT_CREATION_FAILURE;
	}

	//-----------------------------------------------------------------------------------
	String getACCOUNT_SEARCHED() {
		return ACCOUNT_SEARCHED;
	}

	//-----------------------------------------------------------------------------------
	String getINITIAL_CONFIGUARTION_STARTED() {
		return INITIAL_CONFIGUARTION_STARTED;
	}

	//-----------------------------------------------------------------------------------
	String getINITIAL_CONFIGUARTION_FAILED() {
		return INITIAL_CONFIGUARTION_FAILED;
	}

	//-----------------------------------------------------------------------------------
	String getINITIAL_CONFIGUARTION_SUCCESS() {
		return INITIAL_CONFIGUARTION_SUCCESS;
	}

	//-----------------------------------------------------------------------------------
	String getINITIAL_CONFIGUARTION_PASSPHRASE_CHANGED() {
		return INITIAL_CONFIGUARTION_PASSPHRASE_CHANGED;
	}
	
	//-----------------------------------------------------------------------------------
	private void writeToLog(String logString) {
		try(FileWriter fw = new FileWriter("log.txt", true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(logString);
			} 
		
			catch (IOException e) {
				//exception handling left as an exercise for the reader
			}
	}
}
