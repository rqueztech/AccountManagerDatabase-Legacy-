package databaseproject;

class ConfigurationNode {
	private String admNoCounter;
	private String admPhrase;
	private String admSalt;
	private String userNoCounter;
	
	// -----------------------------------------------------------------------------------
	ConfigurationNode(String userNoCounter, String admNoCounter, String admPhrase, String admSalt) {
		this.admNoCounter = admNoCounter;
		this.admPhrase = admPhrase;
		this.admSalt = admSalt;
		this.userNoCounter = userNoCounter;
	}

	// -----------------------------------------------------------------------------------
	String getUserNoCounter() {
		return userNoCounter;
	}
	
	// -----------------------------------------------------------------------------------
	void setUserNoCounter(String userNoCounter) {
		this.userNoCounter = userNoCounter;
	}

	// -----------------------------------------------------------------------------------
	String getAdmNoCounter() {
		return admNoCounter;
	}

	// -----------------------------------------------------------------------------------
	void setAdmNoCounter(String admNoCounter) {
		this.admNoCounter = admNoCounter;
	}

	// -----------------------------------------------------------------------------------
	String getAdmPhrase() {
		return admPhrase;
	}

	// -----------------------------------------------------------------------------------
	void setAdmPhrase(String admPhrase) {
		this.admPhrase = admPhrase;
	}

	// -----------------------------------------------------------------------------------
	String getAdmSalt() {
		return admSalt;
	}

	// -----------------------------------------------------------------------------------
	void setadmSalt(String admSalt) {
		this.admSalt = admSalt;
	}

	// -----------------------------------------------------------------------------------
	@Override
	public String toString() {
		String result = "";
		
		result += String.format("%s,%s,%s,%s", this.getUserNoCounter(), this.getAdmNoCounter(), 
				this.getAdmPhrase(), this.getAdmSalt());
		
		return result;
	}
	
	
}
