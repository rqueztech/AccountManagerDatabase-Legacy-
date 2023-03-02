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
import java.util.concurrent.locks.ReentrantLock;

class AdminCSVOperations {
	private AdministratorFunctions administratorFunctions;
	
	private final String filePathAdmin = "AdminDatabase.csv";
	private String csvHeaderAdmin = "usrName,firstName,lastName,usrPassword,salt,userNo\n";
	private File databaseAdmin;
	
	public boolean isAdminExists;
	
	private ReentrantLock lock = new ReentrantLock() ;

	//-----------------------------------------------------------------------------------
	AdminCSVOperations(AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
	}
	
	// ------------------------------------------------------------------------------------
	/**
	 * If the Administrator user database file does not exist, creates it with the default header.
	 */
	void initializeAdminFile() {
		try {
			lock.lock();

			this.databaseAdmin = new File(this.filePathAdmin);
			
			if(this.databaseAdmin.createNewFile()) {
				OutputStreamWriter writer =
                        new OutputStreamWriter(
                        new FileOutputStream(this.filePathAdmin, true),
                        StandardCharsets.UTF_8);
            	writer.append(this.csvHeaderAdmin);
            	writer.close();
			}
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			lock.unlock();
		}
	}
	
	// ------------------------------------------------------------------------------------
	/**
	 * Overwrites the contents of the Administrator user database file with the data from 
	 * the AdministratorFunctions object.
	 */
	void overwriteAdminFile() {
		try {
			lock.lock();
		
			FileWriter overwrite = new FileWriter(this.filePathAdmin, false);
			overwrite.write(this.csvHeaderAdmin);
			overwrite.close();
			
			Iterator<Entry<String, AdminNode>> hmIterator = this.administratorFunctions.databaseHashMaps.
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

		finally {
			lock.unlock();
		}
	}
	
	// ------------------------------------------------------------------------------------
	/**
	 * Reads the data from the Administrator user database file and adds it to the AdministratorFunctions object.
	 */
	void readAdminFile() {
		try {
			lock.lock();
		
			BufferedReader br = new BufferedReader
				     (new InputStreamReader(new FileInputStream(this.filePathAdmin)));
			
			int count = 0;
	        // Read the csv line by line. Create a split on the commas (,)
	        // Which will separate each piece of data in the line
			
			String databaseString = "";
			
	        while((databaseString = br.readLine()) != null) {
	            String[] values = databaseString.split(",");
	            
	            if(count != 0) {
	            	//int userNo = values[0]);
	            	String usrName = values[0];
	                String fstName = values[1];
	                String lstName = values[2];
	                String hashedPassword = values[3];
	                String salt = values[4];
	                
	                int userNo = Integer.parseInt(values[5]);
	
	                // Encapsulated function. Will add the current line read from the CSV into
	                // The Administrator hashmap.
	                this.administratorFunctions.databaseHashMaps.readIntoAdminHashMap(usrName, fstName, lstName, hashedPassword, salt, userNo);    
	            }
	            
	            count++;
	        }
	
	        // If there is more than one row (the first row is the header), then the file is empty.
	        // Set the boolean to true, indicating isEmpty.
	        if(count > 1) {
	        	this.setAdminIsEmpty(true);
	        }
	        
	        // If less than or equal to one, it means either file is empty (0), or there is no
	        // Administrator accounts (1)
	        else if(count <= 1) {
	        	this.setAdminIsEmpty(false);
	        }
	        
	        br.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			lock.unlock();
		}
	}
	
	// ------------------------------------------------------------------------------------
	boolean isEmptyAdmin() {
		return this.isAdminExists;
	}
	
	// ------------------------------------------------------------------------------------
	void setAdminIsEmpty(boolean isEmpty) {
		this.isAdminExists = isEmpty;
	}
}
