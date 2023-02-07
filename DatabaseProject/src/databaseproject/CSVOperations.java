package databaseproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class CSVOperations {
	private final String filePathUser = "database.csv";
	private final String filePathAdmin = "AdminDatabase.csv";
	private final String filePathConfig = "config.csv";
	
	private File database;
	private File databaseAdmin;
	private File configFile;
	
	public AdministratorFunctions administratorFunctions;
	public ConfigurationOperations configurationOperations;
	
	public boolean isAdminExists;
	
	public String csvHeaderUser = "usrName,firstName,lastName,gender,usrPassword,salt,empNo\n";
	public String csvHeaderAdmin = "usrName,firstName,lastName,usrPassword,salt,empNo\n";
	public String configHeader = "empNoCounter,admNoCounter,adPhrase,adSalt\n";
	
	// ------------------------------------------------------------------------------------
	public CSVOperations(AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
		this.configurationOperations = administratorFunctions.configurationOperations;
	}
	
	// ------------------------------------------------------------------------------------
	// If no database exists, initiate a database file with the
	// Default header
	public void initializeEssentialFiles() {
		this.initializeAdminFile();
		this.initializeUserFile();
		this.initializeConfigFile();
	}
	
	// ------------------------------------------------------------------------------------
	public void initializeAdminFile() {
		try {
			this.databaseAdmin = new File(this.filePathAdmin);
			
			if(this.databaseAdmin.createNewFile()) {
				OutputStreamWriter writer =
                        new OutputStreamWriter(new FileOutputStream(this.filePathAdmin, true), StandardCharsets.UTF_8);
            	writer.append(this.csvHeaderAdmin);
            	writer.close();
			}
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ------------------------------------------------------------------------------------
	public void initializeUserFile() {
		try {
			this.database = new File(this.filePathUser);
			
			if(this.database.createNewFile()) {
				OutputStreamWriter writer =
                        new OutputStreamWriter(new FileOutputStream(this.filePathUser, true), StandardCharsets.UTF_8);
            	writer.append(this.csvHeaderUser);
            	writer.close();
			}
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ------------------------------------------------------------------------------------
	public void initializeConfigFile() {
		try {
			this.configFile = new File(this.filePathConfig);
			
			if(this.configFile.createNewFile()) {
				OutputStreamWriter writer =
                        new OutputStreamWriter(new FileOutputStream(this.filePathConfig, true), StandardCharsets.UTF_8);
            	writer.append(this.configHeader);
            	writer.close();
			}
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ------------------------------------------------------------------------------------
	// Initial file read operation
	public void initializeInitialFileRead() {
		this.readFromAdminFile();
		this.readFromUserFile();
		this.readFromConfigurationFile();
	}
	
	// ------------------------------------------------------------------------------------
	public void overwriteAdminFile() {
		try {
			FileWriter overwrite = new FileWriter(this.filePathAdmin, false);
			overwrite.write(this.csvHeaderAdmin);
			overwrite.close();
			
			Iterator<Entry<String, AdminNode>> hmIterator = this.administratorFunctions.
					getAdminHashMap().entrySet().iterator();
			
			// Iterate through the hashmap
			while(hmIterator.hasNext()) {
				Map.Entry<String, AdminNode> mapElement
                = (Map.Entry<String, AdminNode>)hmIterator.next();
				
				overwrite = new FileWriter(this.filePathAdmin, true);
				
				String stringHash = mapElement.getValue().toString();
				overwrite.append(stringHash);
				overwrite.close();
			}
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// ------------------------------------------------------------------------------------
	public void overwriteUserFile() {
		try {
			FileWriter overwrite = new FileWriter(this.filePathUser, false);
			overwrite.write(this.csvHeaderUser);
			overwrite.close();
			
			Iterator<Entry<String, EmployeeNode>> hmIterator = this.administratorFunctions.
					getEmployeeHashMap().entrySet().iterator();
			
			// Iterate through the hashmap
			while(hmIterator.hasNext()) {
				Map.Entry<String, EmployeeNode> mapElement
                = (Map.Entry<String, EmployeeNode>)hmIterator.next();
				
				overwrite = new FileWriter(this.filePathUser, true);
				
				String stringHash = mapElement.getValue().toString();
				overwrite.append(stringHash);
				overwrite.close();
			}
			
			
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	// ------------------------------------------------------------------------------------
	public void overwriteConfigFile() {
		//try {
			String updatedConfiguration = configurationOperations.getConfigurationString();
			
			FileWriter overwrite;
			try {
				overwrite = new FileWriter(this.filePathConfig, false);
				overwrite.write(updatedConfiguration);
				overwrite.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		
	}
	
	// ------------------------------------------------------------------------------------
	public void readFromAdminFile() {
		try {
			BufferedReader br = new BufferedReader
				     (new InputStreamReader(new FileInputStream(this.filePathAdmin)));
			
			int count = 0;
	        // Read the csv line by line. Create a split on the commas (,)
	        // Which will separate each piece of data in the line
			
			String databaseString = "";
			
	        while((databaseString = br.readLine()) != null) {
	            String[] values = databaseString.split(",");
	            
	            if(count != 0) {
	            	//int empNo = values[0]);
	            	String usrName = values[0];
	                String fstName = values[1];
	                String lstName = values[2];
	                String hashedPassword = values[3];
	                String salt = values[4];
	                
	                int empNo = Integer.parseInt(values[5]);
	
	                // Encapsulated function. Will add the current line read from the CSV into
	                // The Administrator hashmap.
	                this.administratorFunctions.readIntoAdminHashMap(usrName, fstName, lstName, hashedPassword, salt, empNo);    
	            }
	            
	            count++;
	        }
	
	        if(count > 1) {
	        	this.setAdminIsEmpty(true);
	        }
	        
	        else if(count <= 1) {
	        	this.setAdminIsEmpty(false);
	        }
	        
	        br.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ------------------------------------------------------------------------------------
	public boolean isEmptyAdmin() {
		return this.isAdminExists;
	}
	
	// ------------------------------------------------------------------------------------
	public void setAdminIsEmpty(boolean isEmpty) {
		this.isAdminExists = isEmpty;
	}
	
	// ------------------------------------------------------------------------------------
	public void readFromUserFile() {
		try {
			BufferedReader br = new BufferedReader
				     (new InputStreamReader(new FileInputStream(this.filePathUser)));
			
			int count = 0;
	        // Read the csv line by line. Create a split on the commas (,)
	        // Which will separate each piece of data in the line
	        
	        String databaseString = "";
	        
			
	        while((databaseString = br.readLine()) != null) {
	            String[] values = databaseString.split(",");
	            
	            if(count != 0) {
	            	String usrName = values[0];
	                String fstName = values[1];
	                String lstName = values[2];
	                String gender = values[3];
	                String hashedPassword = values[4];
	                String salt = values[5];
	                int empNo = Integer.parseInt(values[6]);
	
	                // Encapsulated function. Will add the current line read from the CSV into
	                // The Administrator hashmap.
	                this.administratorFunctions.readIntoEmployeeHashMap(usrName, fstName, lstName, 
	                		gender, hashedPassword, salt, empNo);
	            }
	            
	            count++;
	        }
	
	        br.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ------------------------------------------------------------------------------------
	public void readFromConfigurationFile() {
		try {
			BufferedReader br = new BufferedReader
					(new InputStreamReader(new FileInputStream(this.filePathConfig)));
			
			int count = 0;
			String databaseString = "";
			
			while((databaseString = br.readLine()) != null) {
	            String[] values = databaseString.split(",");
	            
	            if(count != 0) {
	            	int empNo = Integer.parseInt(values[0]);
                	int admNo = Integer.parseInt(values[1]);
                	String admPassphrase = values[2];
                	String salt = values[3];
                	
                	this.configurationOperations.updateConfiguration(empNo, admNo, admPassphrase, salt);
	            }
	            
	            count++;
	        }
			
			br.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
