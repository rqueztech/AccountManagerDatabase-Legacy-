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
import javax.swing.SwingUtilities;

class ConfigurationPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5013287192014503920L;
	
	private Image image;
	private PanelCentral panelCentral;
	private GridBagConstraints grid;
	
	// This panel will hold the GUI for the configuration operations of the program
	ConfigurationPanel(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
		
		SwingUtilities.invokeLater(() -> {
			this.isInvokeGUI();
		});
	}
	
	void isInvokeGUI() {
		this.grid = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		this.image = new ImageIcon("background.jpg").getImage();
		this.setSize(600, 600);
		
		Insets insets = new Insets(0, 0, 5, 0);
		this.grid.insets = insets;
		
		JLabel labelOne = new JLabel("Welcome To The Initial Configuration.");
		labelOne.setOpaque(false);
		labelOne.setForeground(Color.white);
		this.grid.gridx = 0;
		this.grid.gridy = 0;
		this.grid.gridheight = 1;
		this.add(labelOne, grid);
		
		JLabel labelTwo = new JLabel("You Must set an Administrative Passphrase");
		labelTwo.setOpaque(false);
		labelTwo.setForeground(Color.white);
		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridheight = 1;
		this.add(labelTwo, grid);
		
		JLabel labelThree = new JLabel("And an Administrator Account To Begin Use.");
		labelThree.setOpaque(false);
		labelThree.setForeground(Color.white);
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

	//-----------------------------------------------------------------------------------
	void initialConfiguration() {
		this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_INITIALCONFIGURATIONAGREEMENT);
		
		/* IN WORK
		InitialConfigurationWorker worker = new InitialConfigurationWorker(panelCentral);
		this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_INITIALCONFIGURATIONAGREEMENT);
		worker.execute();
		*/
	}
	
	//-----------------------------------------------------------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2D = (Graphics) g;
		g2D.drawImage(image, 0, 0, null);
	}
}
