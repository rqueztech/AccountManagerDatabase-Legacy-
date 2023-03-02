package databaseproject;

import java.util.HashMap;

class DatabaseHashMaps {
	
	// These two hashmaps will contain all of the user and administrator
	// Information
	private HashMap<String,UserNode> userHashMap;
	private HashMap<String, AdminNode> adminHashMap;
	
	// -----------------------------------------------------------------------------------
	DatabaseHashMaps() {
		this.userHashMap = new HashMap<String, UserNode>();
		this.adminHashMap = new HashMap<String, AdminNode>();
	}
	
	// ------------------------------------------------------------------------------------
	// USER HASHMAP
	// The hashmap acts as a data structure that holds all of the data being interacted with in the program currently.
	HashMap <String, UserNode> getUserHashMap() {
		return this.userHashMap;
	}
	
	// ------------------------------------------------------------------------------------
	// ADMIN HASHMAP
	// The hashmap acts as a data structure that holds all of the data being interacted with in the program currently.
	HashMap <String, AdminNode> getAdminHashMap() {
		return this.adminHashMap;
	}
	
	// ------------------------------------------------------------------------------------
	void readIntoAdminHashMap(String usrName, String fstName, String lstName,
		String hashedPassword, String salt, int userNo) {
		
		this.getAdminHashMap().put(usrName, new AdminNode(usrName, fstName,
				lstName, hashedPassword, salt, userNo));
	}
	
	// ------------------------------------------------------------------------------------
	void readIntoUserHashMap(String usrName, String fstName, String lstName, 
			String gender, String hashedPassword, String salt, int userNo) {
		
		this.getUserHashMap().put(usrName, new UserNode(usrName, fstName, 
				lstName, gender, hashedPassword, salt, userNo));
	}
	
	// ------------------------------------------------------------------------------------
	void addNewUserToHashMap(String newUserName, UserNode newUser) {
		// Add the new created user into the hashmap
		this.getUserHashMap().put(newUserName, newUser);
	}
	
	//-------------------------------------------------------------------------------------
	void addNewAdminToHashMap(String newAdminName, AdminNode newAdmin) {
		// Add the new created user into the hashmap
		this.getAdminHashMap().put(newAdminName, newAdmin);
	}
	
	//-------------------------------------------------------------------------------------
	void deleteUser(String userToDelete) {
		this.getAdminHashMap().remove(userToDelete);
	}
}
