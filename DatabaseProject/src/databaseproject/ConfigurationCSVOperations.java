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
import java.util.concurrent.locks.ReentrantLock;

class ConfigurationCSVOperations {
	public AdministratorFunctions administratorFunctions;
	
	private final String filePathConfig = "config.csv";
	public String configHeader = "userNoCounter,admNoCounter,adPhrase,adSalt\n";
	private File configFile;
	public ConfigurationOperations configurationOperations;
	
	private ReentrantLock lock = new ReentrantLock();

	//-----------------------------------------------------------------------------------
	ConfigurationCSVOperations(AdministratorFunctions administratorFunctions) {
		this.administratorFunctions = administratorFunctions;
		this.configurationOperations = this.administratorFunctions.configurationOperations;
	}
	
	// ------------------------------------------------------------------------------------
	void initializeConfigFile() {
		lock.lock();

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
		
		finally {
			lock.unlock();
		}
	}
	
	// ------------------------------------------------------------------------------------
	void overwriteConfigFile() {		
		String updatedConfiguration = this.configurationOperations.getConfigurationString();
		FileWriter overwrite;
		
		lock.lock();

		try {
			overwrite = new FileWriter(this.filePathConfig, false);
			overwrite.write(updatedConfiguration);
			overwrite.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			lock.unlock();
		}
	}
	
	// ------------------------------------------------------------------------------------
	void readConfigurationFile() {
		lock.lock();

		try {
			BufferedReader br = new BufferedReader
					(new InputStreamReader(new FileInputStream(this.filePathConfig)));
			
			int count = 0;
			String databaseString = "";
			
			while((databaseString = br.readLine()) != null) {
	            String[] values = databaseString.split(",");
	            
	            if(count != 0) {
	            	int userNo = Integer.parseInt(values[0]);
                	int admNo = Integer.parseInt(values[1]);
                	String admPassphrase = values[2];
                	String salt = values[3];
                	
                	this.configurationOperations.updateConfiguration(userNo, admNo, admPassphrase, salt);
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
