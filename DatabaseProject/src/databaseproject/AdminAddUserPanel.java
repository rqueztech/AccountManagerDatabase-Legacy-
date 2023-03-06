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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class AdminAddUserPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4841719570690036053L;
	
	private Image image;
	private AdministratorFunctions administratorFunctions;
	private LoginOperations loginOperations;
	private GridBagConstraints grid;
	private PanelCentral panelCentral;
	
	private final int WIDTH = 600;
	private final int HEIGHT = 600;
	
	// First name, last name, and password Text Field
	private JTextField fName;
	private JTextField lName;
	
	private JComboBox<String> gender;

	//-----------------------------------------------------------------------------------
	AdminAddUserPanel(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		// Classes needed
		this.loginOperations = administratorFunctions.loginOperations;
		this.panelCentral = panelCentral;
		this.administratorFunctions = administratorFunctions;
		
		SwingUtilities.invokeLater(() -> {
			this.isInvokeGUI();
		});
	}
	
	private void isInvokeGUI() {
		// Gender Dropbox
		String[] genderOptions = {"Select", "Male", "Female"};
		this.gender = new JComboBox<String>(genderOptions);
		this.gender.setForeground(Color.white);
		this.gender.setBackground(Color.black);

		// Set the layout for the current frame
		this.setSize(this.WIDTH, this.HEIGHT);
		this.image = new ImageIcon("backgroundd.jpg").getImage();
		this.setLayout(new GridBagLayout());
		this.grid = new GridBagConstraints();
		
		this.fName = new JTextField(15);
		this.lName = new JTextField(15);
		
		// First Name Label
		JLabel fNameLabel = new JLabel("First Name");
		fNameLabel.setBackground(Color.black);
		fNameLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.grid.gridy = 0;
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.add(fNameLabel, grid);
		
		// TEXT FIELD
		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridwidth = 2;
		this.grid.gridheight = 1;
		this.add(this.fName, grid);
		
		// Last Name Label
		JLabel lNameLabel = new JLabel("Last Name");
		lNameLabel.setBackground(Color.black);
		lNameLabel.setForeground(Color.white);
		this.grid.gridx = 0;
		this.grid.gridy = 2;
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.add(lNameLabel, grid);
		
		// TEXT FIELD
		this.grid.gridx = 0;
		this.grid.gridy = 3;
		this.grid.gridwidth = 2;
		this.grid.gridheight = 1;
		this.add(this.lName, grid);
		
		// TEXT FIELD
		this.grid.gridx = 0;
		this.grid.gridy = 5;
		this.grid.gridwidth = 2;
		this.grid.gridheight = 1;
		//this.add(this.password, grid);
		
		// Gender checkbox
		this.grid.gridx = 0;
		this.grid.gridy = 6;
		this.grid.gridheight = 1;
		this.grid.gridwidth = 1;
		this.add(gender, grid);
		
		// Add Button here
		JButton addButton = new JButton("Add");
		addButton.setBackground(Color.black);
		addButton.setForeground(Color.white);
		this.grid.gridx = 0;
		this.grid.gridy = 8;
		this.grid.gridheight = 1;
		this.grid.gridwidth = 1;
		this.add(addButton, grid);
		addButton.addActionListener(e -> this.addUser());
		
		// Delete Button
		JButton goBackButton = new JButton("Go Back");
		goBackButton.setBackground(Color.black);
		goBackButton.setForeground(Color.white);
		this.grid.gridx = 1;
		this.grid.gridy = 8;
		this.grid.gridwidth = 1;
		this.add(goBackButton, grid);
		goBackButton.addActionListener(e -> this.goBack());
		
		// Logout Button
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(Color.black);
		logoutButton.setForeground(Color.gray);
		this.grid.gridx = 1;
		this.grid.gridy = 9;
		this.grid.gridwidth = 1;
		this.add(logoutButton, grid);
		logoutButton.addActionListener(e -> this.panelCentral.panelAdminAddUser.logoutAdmin());
	}
	
	// -----------------------------------------------------------------------------------
	private void addUser() {
		AddUserWorker addUserWorker = 
				new AddUserWorker(this.getFirstName(), 
				this.getLastName(), this.getGender(), 
				this.administratorFunctions);
		
		this.clearBoxes();
		
		addUserWorker.execute();
	}
	
	// -----------------------------------------------------------------------------------
	// Dead code?
	private void addAdmin() {
		AdminAddWorker addAdminWorker = 
				new AdminAddWorker(this.getFirstName(), 
				this.getLastName(), 
				this.administratorFunctions);
		
		this.clearBoxes();
		
		addAdminWorker.execute();
	}
	
	// -----------------------------------------------------------------------------------
	private void clearBoxes() {
		this.fName.setText("");
		this.lName.setText("");
		this.gender.setSelectedIndex(0);
	}
	
	// -----------------------------------------------------------------------------------
	private void goBack() {
		this.panelCentral.setCurrentPanelString(
				this.panelCentral.PANEL_ADMINDISPLAYUSERS);
	}
	
	// -----------------------------------------------------------------------------------
	void logoutAdmin() {
		this.loginOperations.logOutuser();
		JOptionPane.showMessageDialog(null, "Log out successful");
		this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_LOGIN);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2D = (Graphics) g;
		g2D.drawImage(image, 0, 0, null);
	}
	
	// -----------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// -----------------------------------------------------------------------------------
	private String getFirstName() {
		return this.fName.getText();
	}

	//-----------------------------------------------------------------------------------
	private String getLastName() {
		return this.lName.getText();
	}

	//-----------------------------------------------------------------------------------
	private String getGender() {
		return this.gender.getSelectedItem().toString();
	}
}