package databaseproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class InitialConfigurationAgreementPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5013287192014503920L;
	
	private Image image;
	private PanelCentral panelCentral;
	private GridBagConstraints grid;
	
	private JTextField adminFirstName = new JTextField(15);
	private JTextField adminLastName = new JTextField(15);
	private JPasswordField adminPassword = new JPasswordField(15);
	private JPasswordField adminPasswordReentered = new JPasswordField(15);
	private JPasswordField adminPassphrase = new JPasswordField(15);
	
	// This panel will hold the GUI for the configuration operations of the program
	InitialConfigurationAgreementPanel(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
		
		// Put the invoke GUI into the SwingUtilities lambda (previously known as runnable).
		// This will ensure that swing components run on the EDT.
		SwingUtilities.invokeLater(() -> {
			this.isInvokeGUI();
		});
	}
	
	// Initial Configuration GUI. This is separated from the main constructor
	// To avoid clutter and for easy invocation in the EDT.
	void isInvokeGUI() {
		this.grid = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		this.image = new ImageIcon("background.jpg").getImage();
		this.setSize(600, 600);
		
		this.grid.gridy = 0;
		this.grid.gridheight = 1;
		
		Insets insets = new Insets(0, 0, 5, 0);
		this.grid.insets = insets;
		
		// ---------------------------------------------------------
		// First Name Label And TextField Pair
		JLabel firstNameLabel = new JLabel("First Name: ");
		firstNameLabel.setOpaque(false);
		firstNameLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(firstNameLabel, grid);
		
		adminFirstName.setOpaque(true);
		adminFirstName.setForeground(Color.black);
		this.grid.gridx = 1;
		this.add(adminFirstName, grid);
		
		// ---------------------------------------------------------
		// Last Name Label And TextField Pair
		this.grid.gridy += 2;
		JLabel lastNameLabel = new JLabel("Last Name: ");
		lastNameLabel.setOpaque(false);
		lastNameLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(lastNameLabel, grid);
		
		adminLastName.setOpaque(true);
		adminLastName.setForeground(Color.black);
		this.grid.gridx = 1;
		this.add(adminLastName, grid);
		
		// ---------------------------------------------------------
		// Password Label And TextField Pair
		this.grid.gridy += 2;
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setOpaque(false);
		passwordLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(passwordLabel, grid);
		
		adminPassword.setOpaque(true);
		adminPassword.setForeground(Color.black);
		this.grid.gridx = 1;
		this.add(adminPassword, grid);
		
		// ---------------------------------------------------------
		// Confirm Password Label And TextField Pair
		this.grid.gridy += 2;
		JLabel confirmPasswordLabel = new JLabel("Confirm Password: ");
		confirmPasswordLabel.setOpaque(false);
		confirmPasswordLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(confirmPasswordLabel, grid);
		
		adminPasswordReentered.setOpaque(true);
		adminPasswordReentered.setForeground(Color.black);
		this.grid.gridx = 1;
		this.add(adminPasswordReentered, grid);
		
		// ---------------------------------------------------------
		// Passphrase Label And TextField Pair
		this.grid.gridy += 2;
		JLabel passphraseLabel = new JLabel("Passphrase: ");
		passphraseLabel.setOpaque(false);
		passphraseLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(passphraseLabel, grid);
		
		adminPassphrase.setOpaque(true);
		adminPassphrase.setForeground(Color.black);
		this.grid.gridx = 1;
		this.add(adminPassphrase, grid);
		
		// ---------------------------------------------------------
		// Go Back and Submit Pair
		this.grid.gridy += 1;
		JButton goBackButton = new JButton("Go Back");
		goBackButton.setBackground(Color.black);
		goBackButton.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(goBackButton, grid);
		goBackButton.addActionListener(e -> this.goBackFunction());
		
		JButton submitButton = new JButton("Submit");
		submitButton.setBackground(Color.black);
		submitButton.setForeground(Color.white);
		this.grid.gridx = 1;
		this.add(submitButton, grid);
		submitButton.addActionListener(e -> this.submitFunction());
	}
	
	//-----------------------------------------------------------------------------------
	public void goBackFunction() {
		this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_INITIALCONFIGURATION);
	}

	//-----------------------------------------------------------------------------------
	public void submitFunction() {
		InitialConfigurationWorker initialConfigurationWorker = 
				new InitialConfigurationWorker(panelCentral, 
						this.adminFirstName.getText(),
						this.adminLastName.getText(),
						this.adminPassphrase.getPassword(),
						this.adminPassword.getPassword(),
						this.adminPasswordReentered.getPassword()
				);
		initialConfigurationWorker.execute();
	}
	
	//-----------------------------------------------------------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2D = (Graphics) g;
		g2D.drawImage(image, 0, 0, null);
	}
}
