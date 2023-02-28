package databaseproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

class AddAdminWorker extends SwingWorker<Boolean, Void> {
	private String firstName;
	private String lastName;
	private String employeeCreationMessage;
	private AdministratorFunctions administratorFunctions;
	
	/**
	 * This class constructs the general information required to create an employee
	 * in a separate thread to be passed in to the createEmployee function found in
	 * the AdministratorFunctions class
	 * @param firstName gets the first name of the user to be added
	 * @param lastName gets the last name of the user to be added
	 * @param gender gets the gender of the user to be added
	 * @param administratorFunctions
	 */
	public AddAdminWorker(String firstName, String lastName, 
			AdministratorFunctions administratorFunctions) {
		
		this.administratorFunctions = administratorFunctions;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		boolean isAdminCreated = false;
		
		char[] mgrPassword = JOptionPane.showInputDialog("Enter Manager Password").toCharArray();
		boolean checkAdminPassword = this.administratorFunctions.loginOperations
				.checkAdminPassphrase(mgrPassword);
		
		if(checkAdminPassword) {
			isAdminCreated = this.administratorFunctions.createNewAdmin(firstName, lastName);
				this.administratorFunctions.panelCentral.panelAdminDisplayUsers.updateTable();
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Improper Password Entered");
		}
		
		// TODO Auto-generated method stub
		return isAdminCreated;
	}
	
	@Override
	protected void done() {
		try {
            boolean success = get();
            
            if (success) {
            	JOptionPane.showMessageDialog(null, "Admin Creation Successful", "Creation Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
            	JOptionPane.showMessageDialog(null, this.employeeCreationMessage, "Creation Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null, "Error searching error.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
	}
}

class AddEmployeeWorker extends SwingWorker<Boolean, Void> {
	private String firstName;
	private String lastName;
	private String gender;
	private String employeeCreationMessage;
	private AdministratorFunctions administratorFunctions;
	
	/**
	 * This class constructs the general information required to create an employee
	 * in a separate thread to be passed in to the createEmployee function found in
	 * the AdministratorFunctions class
	 * @param firstName gets the first name of the user to be added
	 * @param lastName gets the last name of the user to be added
	 * @param gender gets the gender of the user to be added
	 * @param administratorFunctions
	 */
	public AddEmployeeWorker(String firstName, String lastName, 
			String gender,AdministratorFunctions administratorFunctions) {
		
		this.administratorFunctions = administratorFunctions;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		boolean isUserCreated = false;
		
		char[] mgrPassword = JOptionPane.showInputDialog("Enter Manager Password").toCharArray();
		boolean checkAdminPassword = this.administratorFunctions.loginOperations
				.checkAdminPassphrase(mgrPassword);
		
		if(checkAdminPassword) {
			
			isUserCreated = this.administratorFunctions.createNewEmployee
					(firstName, lastName, gender);
			
			this.administratorFunctions.panelCentral.panelAdminDisplayUsers.updateTable();
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Improper Password Entered");
		}
		
		// TODO Auto-generated method stub
		return isUserCreated;
	}
	
	@Override
	protected void done() {
		try {
            boolean success = get();
            
            if (success) {
            	JOptionPane.showMessageDialog(null, "User Creation Successful", "Creation Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
            	JOptionPane.showMessageDialog(null, this.employeeCreationMessage, "Creation Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null, "Error searching error.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
	}
}

public class AdminAddUserPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4841719570690036053L;
	
	public Image image;
	public AdministratorFunctions administratorFunctions;
	public LoginOperations loginOperations;
	public GridBagConstraints grid;
	public PanelCentral panelCentral;
	public InputOperations inputOperations;
	
	public final int WIDTH = 600;
	public final int HEIGHT = 600;
	
	// First name, last name, and password Text Field
	private JTextField fName;
	private JTextField lName;
	
	private JComboBox<String> gender;
	
	public AdminAddUserPanel(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		String[] genderOptions = {"Select", "Male", "Female"};
		
		// Classes needed
		this.loginOperations = administratorFunctions.loginOperations;
		
		this.gender = new JComboBox<String>(genderOptions);
		this.gender.setForeground(Color.white);
		this.gender.setBackground(Color.black);

		this.panelCentral = panelCentral;
		this.administratorFunctions = administratorFunctions;
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
		addButton.addActionListener(e -> this.addEmployee());
		
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
	public void addEmployee() {
		AddEmployeeWorker addEmployeeWorker = 
				new AddEmployeeWorker(this.getFirstName(), 
				this.getLastName(), this.getGender(), 
				this.administratorFunctions);
		
		this.clearBoxes();
		
		addEmployeeWorker.execute();
	}
	
	// -----------------------------------------------------------------------------------
	public void addAdmin() {
		AddAdminWorker addAdminWorker = 
				new AddAdminWorker(this.getFirstName(), 
				this.getLastName(), 
				this.administratorFunctions);
		
		this.clearBoxes();
		
		addAdminWorker.execute();
	}
	
	// -----------------------------------------------------------------------------------
	public void clearBoxes() {
		this.fName.setText("");
		this.lName.setText("");
		this.gender.setSelectedIndex(0);
	}
	
	// -----------------------------------------------------------------------------------
	public void goBack() {
		this.panelCentral.setCurrentPanelString(
				this.panelCentral.PANEL_ADMINDISPLAYUSERS);
	}
	
	// -----------------------------------------------------------------------------------
	public void logoutAdmin() {
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
	
	public String getFirstName() {
		return this.fName.getText();
	}
	
	public String getLastName() {
		return this.lName.getText();
	}
	
	public String getGender() {
		return this.gender.getSelectedItem().toString();
	}
}