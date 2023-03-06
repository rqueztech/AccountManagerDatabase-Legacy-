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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class ConfigurationAgreementPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5013287192014503920L;
	
	private Image image;
	private PanelCentral panelCentral;
	private GridBagConstraints grid;
	
	// This panel will hold the GUI for the configuration operations of the program
	ConfigurationAgreementPanel(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
		
		// Put the invoke GUI into the SwingUtilities lambda (previously known as runnable).
		// This will ensure that swing components run on the EDT.
		SwingUtilities.invokeLater(() -> {
			this.isInvokeGUI();
		});
	}
	
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
		
		JTextField firstNameTextField = new JTextField(15);
		firstNameTextField.setOpaque(true);
		firstNameTextField.setForeground(Color.white);
		this.grid.gridx = 1;
		this.add(firstNameTextField, grid);
		
		// ---------------------------------------------------------
		// Last Name Label And TextField Pair
		this.grid.gridy += 2;
		JLabel lastNameLabel = new JLabel("Last Name: ");
		lastNameLabel.setOpaque(false);
		lastNameLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(lastNameLabel, grid);
		
		JTextField lastNameTextField = new JTextField(15);
		lastNameTextField.setOpaque(true);
		lastNameTextField.setForeground(Color.white);
		this.grid.gridx = 1;
		this.add(lastNameTextField, grid);
		
		// ---------------------------------------------------------
		// Password Label And TextField Pair
		this.grid.gridy += 2;
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setOpaque(false);
		passwordLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(passwordLabel, grid);
		
		JTextField passwordTextField = new JTextField(15);
		passwordTextField.setOpaque(true);
		passwordTextField.setForeground(Color.white);
		this.grid.gridx = 1;
		this.add(passwordTextField, grid);
		
		// ---------------------------------------------------------
		// Confirm Password Label And TextField Pair
		this.grid.gridy += 2;
		JLabel confirmPasswordLabel = new JLabel("Confirm Password: ");
		confirmPasswordLabel.setOpaque(false);
		confirmPasswordLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(confirmPasswordLabel, grid);
		
		JTextField reenterPasswordTextField = new JTextField(15);
		reenterPasswordTextField.setOpaque(true);
		reenterPasswordTextField.setForeground(Color.white);
		this.grid.gridx = 1;
		this.add(reenterPasswordTextField, grid);
		
		// ---------------------------------------------------------
		// Passphrase Label And TextField Pair
		this.grid.gridy += 2;
		JLabel passphraseLabel = new JLabel("Passphrase: ");
		passphraseLabel.setOpaque(false);
		passphraseLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.add(passphraseLabel, grid);
		
		JTextField passphraseTextField = new JTextField(15);
		passphraseTextField.setOpaque(true);
		passphraseTextField.setForeground(Color.white);
		this.grid.gridx = 1;
		this.add(passphraseTextField, grid);
		
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
	}
	
	//-----------------------------------------------------------------------------------
	public void goBackFunction() {
		this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_CONFIGURATIONAGREEMENTPANEL);
	}

	//-----------------------------------------------------------------------------------
	void initialConfiguration() {
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
