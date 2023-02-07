package databaseproject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProgramLogs {
	public final String LOGIN_SUCCESS = "LOGIN SUCCESS: Login Success";
	public final String LOGIN_FAIL_INVALID_NAME = "LOGIN FAILED: Invalid Name Entered";
	public final String LOGIN_FAIL = "LOGIN FAIL: Login Attempt Fail";
	public final String LOGOUT_SUCCESS = "LOGOUT SUCCESS: Logged Out Successfully";
	public final String PASSWORD_CHANGE_SUCCESS = "PASSWORD CHANGE SUCCESS: Password Changed Successfully";
	public final String PASSWORD_CHANGE_FAIL = "PASSWORD CHANGE FAILED: Password Change Failed";
	public final String PASSWORD_CHANGE_ILLEGAL_CHARACTER = "PASSWORD CHANGE FAILED: Illegal Character Entered";
	public final String PASSWORD_CHANGE_CANCELLED = "PASSWORD CHANGE CANCELLED: Password change has been cancelled by end-user";
	public final String ACCOUNT_ADD_SUCCESS = "ACCOUNT ADD SUCCESS: Account added successfully";
	public final String ACCOUNT_ADD_FAILURE_WRONGPASSWORD = "ACCOUNT ADD FAILURE: Wrong Admin Password";
	public final String ACCOUNT_SEARCHED = "ACCOUNT SEARCH: User Searched";
	public final String INITIAL_CONFIGUARTION_STARTED = "INITIAL CONFIGURATION: Begin Initial Configuration";
	public final String INITIAL_CONFIGUARTION_FAILED = "INITIAL CONFIGURATION: Initial Configuration Failed, No Changes Made/Saved...";
	public final String INITIAL_CONFIGUARTION_SUCCESS = "INITIAL CONFIGURATION: Initial Configuration Success";
	public final String INITIAL_CONFIGUARTION_PASSPHRASE_CHANGED = "INITIAL CONFIGURATION: Passphrase Changed";
	
	public ProgramLogs() {
		this.logNewSessionInitiated();
	}
	
	//-----------------------------------------------------------------------------------
	public void logNewSessionInitiated() {
		String defaultLogString = String.format("*--------------START LOGGING--------------\n" + "*Separate Session For: " 
				+ this.getCurrentLocalDateTimeStamp());
		
		System.out.println(defaultLogString);
		this.writeToLog(defaultLogString);
	}
	
	//-----------------------------------------------------------------------------------
	public String getCurrentLocalDateTimeStamp() {
	    return LocalDateTime.now()
	       .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}
	
	//-----------------------------------------------------------------------------------
	public String printTimeStampUserName(String usrType, String usrName) {
		String logStringHead = String.format(this.getCurrentLocalDateTimeStamp() + " - " 
				+ "(" + usrType + ")" + usrName + " -> ");
		
		return logStringHead;
	}
	
	//-----------------------------------------------------------------------------------
	public void logCurrentEvent(String usrType, String usrName, String eventLogged) {
		String logString = "";
		
		logString += String.format(
				this.printTimeStampUserName(usrType, usrName));
		
		if(eventLogged.equals(LOGIN_SUCCESS)) {
			logString += LOGIN_SUCCESS;
		}

		if(eventLogged.equals(LOGIN_FAIL_INVALID_NAME)) {
			logString += LOGIN_FAIL_INVALID_NAME;
		}

		if(eventLogged.equals(LOGIN_FAIL)) {
			logString += LOGIN_FAIL;
		}

		if(eventLogged.equals(LOGOUT_SUCCESS)) {
			logString += LOGOUT_SUCCESS;
		}

		if(eventLogged.equals(PASSWORD_CHANGE_SUCCESS)) {
			logString += PASSWORD_CHANGE_SUCCESS;
		}

		if(eventLogged.equals(PASSWORD_CHANGE_FAIL)) {
			logString += PASSWORD_CHANGE_FAIL;
		}

		if(eventLogged.equals(PASSWORD_CHANGE_ILLEGAL_CHARACTER)) {
			logString += PASSWORD_CHANGE_ILLEGAL_CHARACTER;
		}

		if(eventLogged.equals(PASSWORD_CHANGE_CANCELLED)) {
			logString += PASSWORD_CHANGE_CANCELLED;
		}
		
		if(eventLogged.equals(ACCOUNT_SEARCHED)) {
			logString += ACCOUNT_SEARCHED;
		}
		
		if(eventLogged.equals(INITIAL_CONFIGUARTION_STARTED)) {
			logString += INITIAL_CONFIGUARTION_STARTED;
		}
		
		if(eventLogged.equals(INITIAL_CONFIGUARTION_FAILED)) {
			logString += INITIAL_CONFIGUARTION_FAILED;
		}
		
		if(eventLogged.equals(INITIAL_CONFIGUARTION_SUCCESS)) {
			logString += INITIAL_CONFIGUARTION_SUCCESS;
		}
		
		if(eventLogged.equals(INITIAL_CONFIGUARTION_SUCCESS)) {
			logString += INITIAL_CONFIGUARTION_SUCCESS;
		}
		
		if(eventLogged.equals(INITIAL_CONFIGUARTION_PASSPHRASE_CHANGED)) {
			logString += INITIAL_CONFIGUARTION_PASSPHRASE_CHANGED;
		}
		
		System.out.println(logString);
		
		this.writeToLog(logString);
	}
	
	//-----------------------------------------------------------------------------------
	public void writeToLog(String logString) {
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
