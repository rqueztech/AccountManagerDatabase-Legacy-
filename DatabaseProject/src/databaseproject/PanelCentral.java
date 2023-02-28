package databaseproject;

import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class PanelCentral extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8333046760151911094L;
	
	public final String PANEL_LOGIN = "PANEL_LOGIN";
	public final String PANEL_ADMINCENTRAL = "PANEL_ADMINCENTRAL";
	public final String PANEL_ADMINADDUSER = "PANEL_ADMINADDUSER";
	public final String PANEL_ADMINDELETEUSER = "PANEL_ADMINDELETEUSER";
	public final String PANEL_ADMINDISPLAYUSERS = "PANEL_ADMINDISPLAYUSERS";
	public final String PANEL_INITIALCONFIGURATION = "PANEL_INITIALCONFIGURATION";
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	
	public final String ADMIN = "ADMIN";
	public final String USER = "USER";
	
	public PasswordEncryption passwordEncryption;
	
	// Set the wiidth and height variable to 600 each, that will be a 600px
	// by 600 px screen
	
	
	public ImageIcon icon;
	public String currentPanelString;
	
	// HERE ARE THE METHODS FOR OTHER CLASSES
	public AdministratorFunctions administratorFunctions;
	
	
	public MainLoginPanel panelLogin;
	public AdminCentralPanel panelAdminCentral;
	public AdminAddUserPanel panelAdminAddUser;
	public AdminDisplayUsersPanel panelAdminDisplayUsers;
	public ConfigurationPanel panelInitialConfiguration;
	public ProgramLogs programLogs;
	
	//-----------------------------------------------------------------------------------
	// Declare the public AdminOperations class here. This will ensure that every
	// Panel will get the same instance.
	
	public PanelCentral() {
		// Initialize all classes used in the program
		this.programLogs = new ProgramLogs();
		
		// Setting up all of the attributes for the frame in the file
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setTitle("Database Project");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.icon = new ImageIcon("SELF_PUNCH.jpg");
		
		this.administratorFunctions = new AdministratorFunctions(this);
		this.passwordEncryption = new PasswordEncryption();
		
		// Initialize the hashmap that will be used for the administrator functions
		this.administratorFunctions.csvOperations.initializeEssentialFiles();
		this.administratorFunctions.csvOperations.initializeInitialFileRead();
		
		//this.panelInitialConfiguration.checkDefaults();
		
		// Initialize all the panels used in the program and pass the appropriate instances
		// To each class
		this.panelLogin = new MainLoginPanel(this.administratorFunctions, this);
		this.panelAdminCentral = new AdminCentralPanel(this.administratorFunctions, this);
		this.panelAdminAddUser = new AdminAddUserPanel(this.administratorFunctions, this);
		this.panelAdminDisplayUsers = new AdminDisplayUsersPanel(this.administratorFunctions, this);
		this.panelInitialConfiguration = new ConfigurationPanel(this.administratorFunctions, this);
		
		// Add panels to the current frame
		this.add(this.panelLogin);
		this.add(this.panelAdminCentral);
		this.add(this.panelAdminAddUser);
		this.add(this.panelAdminDisplayUsers);
		this.add(this.panelInitialConfiguration);
		
		this.checkDefaultConfiguration();
		
		this.showCurrentSelectedPanel();
	}
	
	//-----------------------------------------------------------------------------------
	
	public void showCurrentSelectedPanel() {
		this.clearPanels();
		
		if(this.currentPanelString.equals(PANEL_LOGIN)) {
			this.panelLogin.setVisible(true);
			this.setVisible(true);
		}
		
		else if(this.currentPanelString.equals(PANEL_ADMINCENTRAL)) {
			this.panelAdminCentral.setVisible(true);
		}
		
		else if(this.currentPanelString.equals(PANEL_ADMINADDUSER)) {
			this.panelAdminAddUser.setVisible(true);
		}
		
		else if(this.currentPanelString.equals(PANEL_ADMINDISPLAYUSERS)) {
			this.panelAdminDisplayUsers.setVisible(true);
		}
		
		else if(this.currentPanelString.equals(PANEL_ADMINDELETEUSER)) {
			this.panelAdminDisplayUsers.setVisible(true);
		}
		
		else if(this.currentPanelString.equals(PANEL_INITIALCONFIGURATION)) {
			this.panelInitialConfiguration.setVisible(true);
		}
	}
	
	//-----------------------------------------------------------------------------------
	// Set the visibility of every panel to false. Following this function,
	// The calling method will call the showCurrentSelectedPanel to set the
	// Desired panel in the program to true
	public void clearPanels() {
		this.panelLogin.setVisible(false);
		this.panelAdminCentral.setVisible(false);
		this.panelAdminAddUser.setVisible(false);
		this.panelAdminDisplayUsers.setVisible(false);
		this.panelInitialConfiguration.setVisible(false);
	}
	
	//-----------------------------------------------------------------------------------
	public void setCurrentPanelString(String currentPanelString) {
		this.currentPanelString = currentPanelString;
		this.showCurrentSelectedPanel();
	}
	
	//-----------------------------------------------------------------------------------
	public void checkDefaultConfiguration() {
		if(this.administratorFunctions.configurationOperations.getAdminPassphrase() == null) {
			this.setCurrentPanelString(PANEL_INITIALCONFIGURATION);
			this.showCurrentSelectedPanel();
			this.setVisible(true);
		}
		
		else {
			// Set the initial panel to the "panelLogin". These strings will dictate
			// Which panel will be displayed at any given time.
			this.setCurrentPanelString(PANEL_LOGIN);
		}
	}
}
