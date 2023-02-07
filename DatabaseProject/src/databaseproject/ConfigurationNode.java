package databaseproject;

public class ConfigurationNode {
	private String empNoCounter;
	private String admNoCounter;
	private String admPhrase;
	private String admSalt;
	
	public ConfigurationNode(String empNoCounter, String admNoCounter, String admPhrase, String admSalt) {
		this.empNoCounter = empNoCounter;
		this.admNoCounter = admNoCounter;
		this.admPhrase = admPhrase;
		this.admSalt = admSalt;
	}

	public String getEmpNoCounter() {
		return empNoCounter;
	}
	
	public void setEmpNoCounter(String empNoCounter) {
		this.empNoCounter = empNoCounter;
	}

	public String getAdmNoCounter() {
		return admNoCounter;
	}

	public void setAdmNoCounter(String admNoCounter) {
		this.admNoCounter = admNoCounter;
	}

	public String getAdmPhrase() {
		return admPhrase;
	}

	public void setAdmPhrase(String admPhrase) {
		this.admPhrase = admPhrase;
	}

	public String getAdmSalt() {
		return admSalt;
	}

	public void setadmSalt(String admSalt) {
		this.admSalt = admSalt;
	}

	@Override
	public String toString() {
		String result = "";
		
		result += String.format("%s,%s,%s,%s", this.getEmpNoCounter(), this.getAdmNoCounter(), 
				this.getAdmPhrase(), this.getAdmSalt());
		
		return result;
	}
	
	
}
