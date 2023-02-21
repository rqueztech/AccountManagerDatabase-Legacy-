package databaseproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

class InitialConfigurationWorker extends SwingWorker<Boolean, Void> {
	
	public AdministratorFunctions administratorFunctions;
	public PanelCentral panelCentral;
	public ProgramLogs programLogs;
	
	private String adminPassphrase;
	private String adminFirstName;
	private String adminLastName;
	private String adminPassword;
	
	public InitialConfigurationWorker(PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
		this.administratorFunctions = new AdministratorFunctions(this.panelCentral);
		this.programLogs = panelCentral.programLogs;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		
		this.isAdministratorPassphraseExists();
		result = this.isAdministratorAccountExists();
		
		return result;
	}
	
	@Override
	protected void done() {
		try {
			boolean success = get();
			if(success) {
				JOptionPane.showMessageDialog(null, "Configuration Success");
			} else {
				JOptionPane.showMessageDialog(null, "Configuration Failed");
			}
		} catch (InterruptedException | ExecutionException e) {
			JOptionPane.showMessageDialog(null, "Fatal Error, shutting down...");
		}
	}
	
	//-----------------------------------------------------------------------------------
	public boolean isAdministratorPassphraseExists() {
		this.adminPassphrase = this.administratorFunctions.configurationOperations.getAdminPassphrase();
		
		// If the administrator passphrase is null, that means the passphrase does not currently
		// Exist (nothing has been read from the file). In this case, we can break out and return
		// False. We are done.
		if(this.getAdminPassphrase() != null) {
			return true;
		}
		
		// If the admin passphrase has resulted null, this means there is not administrator passphrase
		// Currently in the program. Prompt the user to enter an administrator account.
		else if(this.getAdminPassphrase() == null) {
			// If the administrator passphrase returns with a
			do {
				this.setAdminPassphrase(JOptionPane.showInputDialog(null, "Enter Admin Passphrase:\nPlease Enter An Admin Passphrase", 
						"Passphrase", JOptionPane.INFORMATION_MESSAGE));
				
				if(this.getAdminPassphrase() == null) {
					JOptionPane.showMessageDialog(null, "Pasphrase Cancelled, EOP...", "Passphrase Operation", JOptionPane.ERROR_MESSAGE);
					
					this.programLogs.logCurrentEvent("ADMIN", 
							"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_FAILED());
					
					System.exit(0);
					return false;
				}
				
			} while(!this.administratorFunctions.inputOperations.passwordRequirements(this.getAdminPassphrase()));
			
		}
		
		return true;
	}
	
	public boolean isAdministratorAccountExists() {
		boolean result = this.administratorFunctions.csvOperations.isAdminExists;
		
		if(result) {
			return true;
		}
		
		else if(!result) {
			JOptionPane.showMessageDialog(null, "Must Create User Account");
			this.adminFirstName = "";
			this.adminLastName = "";
			this.adminPassword = "";
			
			
			this.adminFirstName = JOptionPane.showInputDialog(null, "Enter Admin First Name");
			
			if(this.adminFirstName != null) {
				while(this.adminFirstName != null && this.adminFirstName.length() <= 3
					|| !this.administratorFunctions.inputOperations.onlyLetterCharacters(this.adminFirstName)) {
					this.adminFirstName = JOptionPane.showInputDialog(null, "Enter Admin First Name (Must Only Contain Letters)", "Alphabet Characters Only!!!", JOptionPane.ERROR_MESSAGE);
				}
				
				this.adminLastName = JOptionPane.showInputDialog(null, "Enter Admin Last Name");
				while(this.adminFirstName != null && this.adminLastName.length() <= 3
					|| !this.administratorFunctions.inputOperations.onlyLetterCharacters(this.adminLastName)) {	
					this.adminLastName = JOptionPane.showInputDialog(null, "Enter Admin Last Name (Must Only Contain Letters)", "Alphabet Characters Only!!!", JOptionPane.ERROR_MESSAGE);
				}
				
				this.adminPassword = JOptionPane.showInputDialog(null, "Enter Admin Password");
				while(this.adminFirstName != null && this.adminPassword.length() <= 3
					|| !this.administratorFunctions.inputOperations.passwordRequirements(this.adminPassword)) {
					
					this.adminPassword = JOptionPane.showInputDialog(null, "Enter Valid Password:"
							+ "\n8 Char Minimum"
							+ "\n1 Number"
							+ "\n1 Special Symbol", 
							"Alphabet Characters Only!!!", 
							JOptionPane.ERROR_MESSAGE);
				}
				
				if(this.adminFirstName != null
				&& this.adminLastName != null 
				&& this.adminPassword != null) {
					System.out.println(this.adminFirstName);
					System.out.println(this.adminLastName);
					System.out.println(this.adminPassword);
					this.saveConfigurationChanges();
					result = true;
				}
			}
				
			else {
				JOptionPane.showMessageDialog(null, "Must Create User... Please Restart Configuration", "Exit", JOptionPane.ERROR_MESSAGE);
				
				this.programLogs.logCurrentEvent("ADMIN", 
						"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_FAILED());
				
				System.exit(0);
			}
		}
		
		return result;
	}
	
	//-----------------------------------------------------------------------------------
	public void saveConfigurationChanges() {
		this.administratorFunctions.configurationOperations.createAdministrativePassphrase(this.getAdminPassphrase());			
		this.administratorFunctions.csvOperations.overwriteConfigFile();
		
		// Create the new admin here
		boolean isAdminCreated = this.administratorFunctions.createNewAdmin(this.adminFirstName, this.adminLastName);
		if(isAdminCreated) {
			this.administratorFunctions.csvOperations.overwriteAdminFile();
			
			this.programLogs.logCurrentEvent("ADMIN", 
					"INITIAL_CONFIG", this.programLogs.getINITIAL_CONFIGUARTION_SUCCESS());
			
			JOptionPane.showMessageDialog(null, "Admin Name:\n" + "\nConfiguration Success...\nNow You Can Customize Your Database", "SUCCESS!!!", JOptionPane.INFORMATION_MESSAGE);
			this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_LOGIN);
		}
	}
	
	public String getAdminPassphrase() {
		return adminPassphrase;
	}

	public void setAdminPassphrase(String adminPassphrase) {
		this.adminPassphrase = adminPassphrase;
	}

	public String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
}

public class ConfigurationPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5013287192014503920L;
	
	public Image image;
	public PanelCentral panelCentral;
	public AdministratorFunctions administratorFunctions;
	public GridBagConstraints grid;
	
	public JTextField firstField;
	public JTextField secondField;
	
	public String adminPassphrase;
	public String adminFirstName;
	public String adminLastName;
	public String adminPassword;
	
	public ConfigurationPanel(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		this.administratorFunctions = administratorFunctions;
		this.panelCentral = panelCentral;
		this.grid = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		this.image = new ImageIcon("background.jpg").getImage();
		this.setSize(600, 600);
		
		JLabel labelOne = new JLabel("Welcome To The Initial Configuration.");
		
		labelOne.setBackground(Color.black);
		labelOne.setBackground(Color.white);
		
		this.grid.gridx = 0;
		this.grid.gridy = 0;
		this.grid.gridheight = 1;
		this.add(labelOne, grid);
		
		JLabel labelTwo = new JLabel("You Must set an Administrative Passphrase");
		
		labelTwo.setBackground(Color.black);
		labelTwo.setBackground(Color.white);
		
		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridheight = 1;
		this.add(labelTwo, grid);
		
		JLabel labelThree = new JLabel("And an Administrator Account To Begin Use.");
		
		labelThree.setBackground(Color.black);
		labelThree.setBackground(Color.white);
		
		this.grid.gridx = 0;
		this.grid.gridy = 2;
		this.grid.gridheight = 1;
		this.add(labelThree, grid);
		
		JButton addButton = new JButton("Start Configuration");
		addButton.setBackground(Color.black);
		addButton.setForeground(Color.white);
		this.grid.gridx = 0;
		this.grid.gridy = 3;
		this.grid.gridheight = 1;
		this.grid.gridwidth = 1;
		this.add(addButton, grid);
		addButton.addActionListener(e -> this.initialConfiguration());
		
	}
	
	public void initialConfiguration() {
		InitialConfigurationWorker worker = new InitialConfigurationWorker(panelCentral);
		worker.execute();
	}
	
	//-----------------------------------------------------------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2D = (Graphics) g;
		g2D.drawImage(image, 0, 0, null);
	}
}
