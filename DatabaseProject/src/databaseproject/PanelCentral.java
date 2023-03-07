package databaseproject;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class PanelCentral extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8333046760151911094L;
	
	public enum PanelType {
	    LOGIN,
	    ADMIN_CENTRAL,
	    ADMIN_ADD_USER,
	    ADMIN_DELETE_USER,
	    ADMIN_DISPLAY_USERS,
	    INITIAL_CONFIGURATION,
	    INITIAL_CONFIGURATION_AGREEMENT
	}

	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	
	public final String ADMIN = "ADMIN";
	public final String USER = "USER";
	
	public PasswordEncryption passwordEncryption;
	
	
	public ImageIcon icon;
	public String currentPanelString;
	
	// HERE ARE THE METHODS FOR OTHER CLASSES
	public AdministratorFunctions administratorFunctions;
	
	
	public MainLoginPanel mainLoginPanel;
	public AdminCentralPanel adminCentralPanel;
	public AdminAddUserPanel panelAdminAddUser;
	public AdminDisplayUsersPanel panelAdminDisplayUsers;
	public InitialConfigurationAgreementPanel panelInitialConfiguration;
	
	public InitialConfigurationPanel initialConfigurationAgreementPanel;
	
	//-----------------------------------------------------------------------------------
	// Declare the public AdminOperations class here. This will ensure that every
	// Panel will get the same instance.
	
	PanelCentral() {
		// Initialize all classes used in the program

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
		this.panelInitialConfiguration = new InitialConfigurationAgreementPanel(this.administratorFunctions, this);
		this.initialConfigurationAgreementPanel = new InitialConfigurationPanel(administratorFunctions, this);
		
		// Invoke the GUI on the SwingUtilities thread to prevent performance issues with the Swing
		// Class interfering with current threads. Put it on the EDT (Event Dispatch Thread)
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
	
		// Add every panel to the frame. This will allow us to use the panels
		// Individually in the program
		this.add(this.mainLoginPanel);
		this.add(this.adminCentralPanel);
		this.add(this.panelAdminAddUser);
		this.add(this.panelAdminDisplayUsers);
		this.add(this.panelInitialConfiguration);
		this.add(this.initialConfigurationAgreementPanel);
		
		this.setFirstPanel();
	}
	
	//-----------------------------------------------------------------------------------	
	public void showCurrentSelectedPanel(PanelType selectedPanel) {
		this.clearPanels();
		
		// Display the current panel. This will all be dictated by what the end-user
		// Is doing in the system
		switch (selectedPanel) {
		    case LOGIN:
		        this.mainLoginPanel.setVisible(true);
		        setVisible(true);
		        break;

		    case ADMIN_CENTRAL:
		        this.adminCentralPanel.setVisible(true);
		        setVisible(true);
		        break;

		    case ADMIN_ADD_USER:
		        this.panelAdminAddUser.setVisible(true);
		        setVisible(true);
		        break;

		    case ADMIN_DELETE_USER:
		        this.panelAdminDisplayUsers.setVisible(true);
		        setVisible(true);
		        break;

		    case ADMIN_DISPLAY_USERS:
		        this.panelAdminDisplayUsers.setVisible(true);
		        setVisible(true);
		        break;

		    case INITIAL_CONFIGURATION:
		        this.panelInitialConfiguration.setVisible(true);
		        setVisible(true);
		    	break;

		    case INITIAL_CONFIGURATION_AGREEMENT:
		    	this.initialConfigurationAgreementPanel.setVisible(true);
		    	setVisible(true);
		    	break;

		    default:
		    	System.out.println("ERROR");
		        break;
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
		this.initialConfigurationAgreementPanel.setVisible(false);
	}
	
	// Set the first panel that will be displayed in the program. Will either be:
	// 1. INITIAL_CONFIGURATION if NO admin exists, meaning it was already configured
	// 2. LOGIN if an admin exists, meaning configuration was previously complied with
	//-----------------------------------------------------------------------------------
	private void setFirstPanel() {
		// Needs initial configuration, send to default configuration screen
		if(this.administratorFunctions.configurationOperations.getAdminPassphrase() == null) {
			this.showCurrentSelectedPanel(PanelType.INITIAL_CONFIGURATION);
			this.setVisible(true);
		}
		
		// No need for an initial configuration, already configured
		else {
			// Set the initial panel to the "mainLoginPanel". These strings will dictate
			// Which panel will be displayed at any given time.
			this.showCurrentSelectedPanel(PanelType.LOGIN);
		}
	}
}
