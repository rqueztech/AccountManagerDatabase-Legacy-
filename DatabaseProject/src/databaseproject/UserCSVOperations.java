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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class UserCSVOperations {
	private AdministratorFunctions administratorFunctions;
	
	private final String filePathUser = "database.csv";
	public String csvHeaderUser = "usrName,firstName,lastName,gender,usrPassword,salt,userNo\n";
	private File database;
	
	private final Lock lock = new ReentrantLock();

	//-----------------------------------------------------------------------------------
	UserCSVOperations(AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
	}
	
	// ------------------------------------------------------------------------------------
	void initializeUserFile() {
		lock.lock();
		
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
		
		finally {
			lock.unlock();
		}
	}
	
	// ------------------------------------------------------------------------------------
	void overwriteUserFile() {
		lock.lock();
		
		try {
			FileWriter overwrite = new FileWriter(this.filePathUser, false);
			overwrite.write(this.csvHeaderUser);
			overwrite.close();
			
			Iterator<Entry<String, UserNode>> hmIterator = this.administratorFunctions.databaseHashMaps
					.getUserHashMap().entrySet().iterator();
			
			// Iterate through the hashmap
			while(hmIterator.hasNext()) {
				Map.Entry<String, UserNode> mapElement
                = (Map.Entry<String, UserNode>)hmIterator.next();
				
				overwrite = new FileWriter(this.filePathUser, true);
				
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
	void readUserFile() {
		
		lock.lock();
		
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
	                int userNo = Integer.parseInt(values[6]);
	
	                // Encapsulated function. Will add the current line read from the CSV into
	                // The Administrator hashmap.
	                this.administratorFunctions.databaseHashMaps.readIntoUserHashMap(usrName, fstName, lstName, 
	                		gender, hashedPassword, salt, userNo);
	            }
	            
	            count++;
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
}
