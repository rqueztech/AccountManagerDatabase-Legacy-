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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import databaseproject.PanelCentral.PanelType;

class AdminCentralPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3679043023594079500L;
	
	private Image image;
	private GridBagConstraints grid;
	private PanelCentral panelCentral;
	
	//-----------------------------------------------------------------------------------
	AdminCentralPanel(PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
		
		SwingUtilities.invokeLater(() -> {
			this.invokeGUI();
		});
	}
	
	//-----------------------------------------------------------------------------------
	private void invokeGUI() {
		System.out.println(SwingUtilities.isEventDispatchThread());
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
	void addUser() {
		SwingUtilities.invokeLater(() -> {
			this.panelCentral.panelAdminDisplayUsers.updateTable();
		});
		this.panelCentral.showCurrentSelectedPanel(PanelType.ADMIN_ADD_USER);
	}
	
	//-----------------------------------------------------------------------------------
	void delUser() {
		
	}
	
	//-----------------------------------------------------------------------------------
	private void editUsers() {
		this.panelCentral.showCurrentSelectedPanel(PanelType.ADMIN_DISPLAY_USERS);
	}
	
	//-----------------------------------------------------------------------------------
	void logoutAdmin() {
		this.panelCentral.administratorFunctions.loginOperations.logOutuser();
		// CREATE SOMETHING TO LOG USER OUT
		this.panelCentral.showCurrentSelectedPanel(PanelType.LOGIN);
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