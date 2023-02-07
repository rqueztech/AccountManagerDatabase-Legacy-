package databaseproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelAdminCentral extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3679043023594079500L;
	
	public Image image;
	public AdministratorFunctions administratorFunctions;
	public GridBagConstraints grid;
	public PanelCentral panelCentral;
	public ProgramLogs pgmLogs;
	
	public String usrName;
	public String usrPassword;
	
	//-----------------------------------------------------------------------------------
	public PanelAdminCentral(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
		this.administratorFunctions = administratorFunctions;
		this.setSize(600, 600);
		this.image = new ImageIcon("backgroundd.jpg").getImage();
		this.setLayout(new GridBagLayout());
		this.grid = new GridBagConstraints();
		
		
		// Display Users Button
		// Search Button
		JButton userDisplayButton = new JButton("Edit Users");
		userDisplayButton.setBackground(Color.black);
		userDisplayButton.setForeground(Color.gray);
		this.grid.gridx = 0;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(userDisplayButton, grid);
		userDisplayButton.addActionListener(e -> editUsers());
		
		// Search Button
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(Color.black);
		logoutButton.setForeground(Color.gray);
		this.grid.gridx = 1;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(logoutButton, grid);
		logoutButton.addActionListener(e -> this.panelCentral.panelAdminAddUser.logoutAdmin());
	}
	
	//-----------------------------------------------------------------------------------
	public void addEmployee() {
		this.panelCentral.panelAdminDisplayUsers.updateTable();
		this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_ADMINADDUSER);
	}
	
	//-----------------------------------------------------------------------------------
	public void delEmployee() {
		
	}
	
	//-----------------------------------------------------------------------------------
	public void editUsers() {
		this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_ADMINDISPLAYUSERS);
	}
	
	//-----------------------------------------------------------------------------------
	public void logoutAdmin() {
		JOptionPane.showMessageDialog(null, "Log out successful");
		this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_LOGIN);
	}
	
	//-----------------------------------------------------------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2D = (Graphics) g;
		g2D.drawImage(image, 0, 0, null);
	}
	
	//-----------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}