package databaseproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		
		labelOne.setBackground(Color.white);
		
		this.grid.gridx = 0;
		this.grid.gridy = 0;
		this.grid.gridheight = 1;
		this.add(labelOne, grid);
		
		JLabel labelTwo = new JLabel("You Must set an Administrative Passphrase");
		
		labelTwo.setBackground(Color.white);
		
		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridheight = 1;
		this.add(labelTwo, grid);
		
		JLabel labelThree = new JLabel("And an Administrator Account To Begin Use.");
		
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
