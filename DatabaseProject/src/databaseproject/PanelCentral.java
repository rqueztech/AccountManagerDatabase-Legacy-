package databaseproject;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class PanelCentral extends JFrame {
	
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
	
	
	public MainLoginPanel mainLoginPanel;
	public AdminCentralPanel adminCentralPanel;
	public AdminAddUserPanel panelAdminAddUser;
	public AdminDisplayUsersPanel panelAdminDisplayUsers;
	public ConfigurationPanel panelInitialConfiguration;
	public ProgramLogs programLogs;
	
	//-----------------------------------------------------------------------------------
	// Declare the public AdminOperations class here. This will ensure that every
	// Panel will get the same instance.
	
	PanelCentral() {
		// Initialize all classes used in the program
		this.programLogs = new ProgramLogs();
		this.administratorFunctions = new AdministratorFunctions(this);
		this.passwordEncryption = new PasswordEncryption();
		
		// Initialize the hashmap that will be used for the administrator functions
		this.administratorFunctions.csvOperations.initializeEssentialFiles();
		this.administratorFunctions.csvOperations.initializeInitialFileRead();
		
		// Initialize all the panels used in the program and pass the appropriate instances
		// To each class
		this.mainLoginPanel = new MainLoginPanel(this.administratorFunctions, this);
		this.adminCentralPanel = new AdminCentralPanel(this);
		this.panelAdminAddUser = new AdminAddUserPanel(this.administratorFunctions, this);
		this.panelAdminDisplayUsers = new AdminDisplayUsersPanel(this.administratorFunctions, this);
		this.panelInitialConfiguration = new ConfigurationPanel(this.administratorFunctions, this);
		
		// Setting up all of the attributes for the frame in the file
		
		SwingUtilities.invokeLater(() -> {;
			this.isInvokeGUI();
		});
	}
	
	private void isInvokeGUI() {
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setTitle("Database Project");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.icon = new ImageIcon("SELF_PUNCH.jpg");
	
		// Add panels to the current frame
		this.add(this.mainLoginPanel);
		this.add(this.adminCentralPanel);
		this.add(this.panelAdminAddUser);
		this.add(this.panelAdminDisplayUsers);
		this.add(this.panelInitialConfiguration);
		
		this.checkDefaultConfiguration();
		this.showCurrentSelectedPanel();
	}
	
	//-----------------------------------------------------------------------------------	
	private void showCurrentSelectedPanel() {
		this.clearPanels();
		
		if(this.currentPanelString.equals(PANEL_LOGIN)) {
			this.mainLoginPanel.setVisible(true);
			this.setVisible(true);
		}
		
		else if(this.currentPanelString.equals(PANEL_ADMINCENTRAL)) {
			this.adminCentralPanel.setVisible(true);
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
	private void clearPanels() {
		this.mainLoginPanel.setVisible(false);
		this.adminCentralPanel.setVisible(false);
		this.panelAdminAddUser.setVisible(false);
		this.panelAdminDisplayUsers.setVisible(false);
		this.panelInitialConfiguration.setVisible(false);
	}
	
	//-----------------------------------------------------------------------------------
	void setCurrentPanelString(String currentPanelString) {
		this.currentPanelString = currentPanelString;
		this.showCurrentSelectedPanel();
	}
	
	//-----------------------------------------------------------------------------------
	private void checkDefaultConfiguration() {
		if(this.administratorFunctions.configurationOperations.getAdminPassphrase() == null) {
			this.setCurrentPanelString(PANEL_INITIALCONFIGURATION);
			this.showCurrentSelectedPanel();
			this.setVisible(true);
		}
		
		else {
			// Set the initial panel to the "mainLoginPanel". These strings will dictate
			// Which panel will be displayed at any given time.
			this.setCurrentPanelString(PANEL_LOGIN);
		}
	}
}
