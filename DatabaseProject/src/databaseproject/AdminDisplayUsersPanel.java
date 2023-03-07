package databaseproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import databaseproject.PanelCentral.PanelType;

class AdminDisplayUsersPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 488767503804714638L;
	
	public AdministratorFunctions administratorFunctions;
	public LoginOperations loginOperations;
	public GridBagConstraints grid;
	public PanelCentral panelCentral;
	public InputOperations inputOperations;
	public Image image;
	
	private String row[][];
	private String colArray[];
	
	// First name, last name, and password Text Field
	private JTextField fName;
	private JTextField lName;

	private JButton confirmSearchButton = new JButton("Search!!!");
	private JButton confirmDeleteButton = new JButton("Delete!!!");
	
	private JLabel nameToDelete;
	private JLabel nameToSearch;
	
	private JTextField userNameSearch;
	private JTextField userNameAdd;
	private JTextField userNameDelete;
	
	private DefaultTableModel model;
	private TableRowSorter<DefaultTableModel> sorter;
	private JTable table;
	
	private JComboBox<String> gender;

	AdminDisplayUsersPanel(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
		this.administratorFunctions = administratorFunctions;
		
		SwingUtilities.invokeLater(() -> {
			this.createTable();
			this.updateTable();
			this.invokeGUI();
		});
	}

	// -----------------------------------------------------------------------------------
	void confirmDeleteUser() {
		boolean isValid = this.administratorFunctions
				.inputOperations
				.isOnlyLettersAndNumbers(this.userNameDelete.getText()).isEmpty();
		
		String userToDelete = this.userNameDelete.getText();
		this.userNameDelete.setText("");
		
		
		if(isValid) {
			// Create the string message that will appear
	    	String deleteMessage = String.format(
					"Are you sure you want to delete: " 
					+ userToDelete + " ?");
	    	
	    	// Prompt the user whether they 
			int response = JOptionPane.showConfirmDialog(null, deleteMessage, 
					"Delete Confirmation", 
					JOptionPane.YES_NO_OPTION);
	    	
			if(response == JOptionPane.YES_OPTION) {
				// Delete the user
				AdminDeleteUserSwingWorker adminDeleteUserSwingWorker = new AdminDeleteUserSwingWorker(userToDelete, this.administratorFunctions);
				adminDeleteUserSwingWorker.execute();
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Delete Operation Cancelled", 
						"Deletion Failed", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Only numbers and letters allowed");
		}
	}
	
	// -----------------------------------------------------------------------------------
	void initializeUserTable() {
		int userHashMapSize = this.administratorFunctions.databaseHashMaps
				.getUserHashMap().size();
		
		this.row = new String[userHashMapSize][4];
		this.colArray = this.administratorFunctions.csvOperations.userCSVOperations.csvHeaderUser.split(",",4);
		
		Iterator<Entry<String, UserNode>> hmIterator = 
				this.administratorFunctions.databaseHashMaps.getUserHashMap()
				.entrySet()
				.iterator();

		int currentCounter = 0;

		// Iterate through the hashmap
		while (hmIterator.hasNext()) {
			Map.Entry<String, UserNode> mapElement = (Map.Entry<String, UserNode>) hmIterator.next();

			this.row[currentCounter][0] = mapElement.getValue().getUserName();
			this.row[currentCounter][1] = mapElement.getValue().getFirstName();
			this.row[currentCounter][2] = mapElement.getValue().getLastName();
			this.row[currentCounter][3] = mapElement.getValue().getGender();
			
			currentCounter++;
		}
	}
	
	// -----------------------------------------------------------------------------------
	void UserTable() {
		int userHashMapSize = this.administratorFunctions.databaseHashMaps.getUserHashMap().size();
		
		this.row = new String[userHashMapSize][4];
		this.colArray = this.administratorFunctions.csvOperations.userCSVOperations.csvHeaderUser.split(",",4);
		
		Iterator<Entry<String, UserNode>> hmIterator = 
				this.administratorFunctions.databaseHashMaps.getUserHashMap()
				.entrySet()
				.iterator();

		int currentCounter = 0;

		// Iterate through the hashmap
		while (hmIterator.hasNext()) {
			Map.Entry<String, UserNode> mapElement = (Map.Entry<String, UserNode>) hmIterator.next();

			this.row[currentCounter][0] = mapElement.getValue().getUserName();
			this.row[currentCounter][1] = mapElement.getValue().getFirstName();
			this.row[currentCounter][2] = mapElement.getValue().getLastName();
			this.row[currentCounter][3] = mapElement.getValue().getGender();
			
			currentCounter++;
		}
		
		this.model = new DefaultTableModel(this.getRow(), this.getCol());
	}
	
	public void invokeGUI() {
		this.setSize(600, 600);
		this.image = new ImageIcon("backgroundd.jpg").getImage();
		this.setLayout(new GridBagLayout());
		this.grid = new GridBagConstraints();
		
		JLabel label = new JLabel("Table Below");
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLACK);
		this.grid.gridx = 0;
		this.grid.gridy = 0;
		this.grid.gridheight = 1;
		this.grid.gridwidth = 4;
		this.add(label, this.grid);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(380, 100));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridwidth = 4;

		this.add(scrollPane, this.grid);
		
		this.nameToDelete = new JLabel("Delete User: ");
		this.nameToDelete.setBackground(Color.BLACK);
		this.nameToDelete.setForeground(Color.WHITE);
		this.nameToDelete.setOpaque(false);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 0;
		this.grid.gridy = 5;
		this.nameToDelete.setVisible(false);
		this.add(nameToDelete, this.grid);
		
		this.nameToSearch = new JLabel("Search User: ");
		this.nameToSearch.setBackground(Color.BLACK);
		this.nameToSearch.setForeground(Color.WHITE);
		this.nameToSearch.setOpaque(false);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 0;
		this.grid.gridy = 5;
		this.nameToSearch.setVisible(false);
		this.add(nameToSearch, this.grid);
		
		// Confirm SEARCN button
		this.confirmSearchButton.setBackground(Color.GRAY);
		this.confirmSearchButton.setForeground(Color.WHITE);
		this.confirmSearchButton.setOpaque(true);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 2;
		this.grid.gridy = 5;
		this.confirmSearchButton.setVisible(false);
		this.add(confirmSearchButton, this.grid);
		
		this.confirmSearchButton.addActionListener(e -> 
			this.confirmSearchUser());
		
		// Confirm DELETE button
		this.confirmDeleteButton.setBackground(Color.GRAY);
		this.confirmDeleteButton.setForeground(Color.WHITE);
		this.confirmDeleteButton.setOpaque(true);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 2;
		this.grid.gridy = 5;
		this.confirmDeleteButton.setVisible(false);
		this.add(confirmDeleteButton, this.grid);
		
		this.confirmDeleteButton.addActionListener(e -> 
			this.confirmDeleteUser());
		
		// USER NAME ADD
		this.userNameAdd = new JTextField(10);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 1;
		this.grid.gridy = 5;
		this.userNameAdd.setVisible(false);
		this.add(userNameAdd, this.grid);
		
		// USER NAME SEARCH
		this.userNameSearch = new JTextField(10);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 1;
		this.grid.gridy = 5;
		this.userNameSearch.setVisible(false);
		this.add(userNameSearch, this.grid);
		
		// USER NAME DELETE
		this.userNameDelete = new JTextField(10);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 1;
		this.grid.gridy = 5;
		this.userNameDelete.setVisible(false);
		this.add(userNameDelete, this.grid);
		
		// Search Button
		JButton addUserButton = new JButton("Add User");
		addUserButton.setBackground(Color.BLACK);
		addUserButton.setForeground(Color.GRAY);
		this.grid.gridx = 0;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(addUserButton, this.grid);
		addUserButton.addActionListener(e -> this.addUser());
		
		// Delete Button
		JButton deleteUserButton = new JButton("Delete User");
		deleteUserButton.setBackground(Color.BLACK);
		deleteUserButton.setForeground(Color.GRAY);
		this.grid.gridx = 1;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(deleteUserButton, this.grid);
		deleteUserButton.addActionListener(e -> this.deleteUserDisplay());
		
		JButton searchUserButton = new JButton("Search User");
		searchUserButton.setBackground(Color.black);
		searchUserButton.setForeground(Color.gray);
		this.grid.gridx = 2;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(searchUserButton, this.grid);
		searchUserButton.addActionListener(e -> this.searchUser());
		
		// Search Button
		JButton goBackButton = new JButton("Go Back");
		goBackButton.setBackground(Color.black);
		goBackButton.setForeground(Color.gray);
		this.grid.gridx = 3;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(goBackButton, this.grid);
		goBackButton.addActionListener(e -> this.goBack());
		
		// Search Button
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(Color.black);
		logoutButton.setForeground(Color.gray);
		this.grid.gridx = 3;
		this.grid.gridy = 5;
		this.grid.gridwidth = 1;
		this.add(logoutButton, this.grid);
		logoutButton.addActionListener(e -> this.panelCentral.panelAdminAddUser.logoutAdmin());
	}
	
	// -----------------------------------------------------------------------------------
	void setTableModel() {
		this.table.setModel(this.model);
	}
	
	// -----------------------------------------------------------------------------------
	void clearBoxes() {
		this.fName.setText("");
		this.lName.setText("");
		this.gender.setSelectedIndex(0);
	}

	// -----------------------------------------------------------------------------------
	void searchUser() {
		this.clearPanels();
		this.nameToSearch.setVisible(true);
		this.userNameSearch.setVisible(true);
		this.confirmSearchButton.setVisible(true);
	}
	
	// -----------------------------------------------------------------------------------
	void confirmSearchUser() {
		String userNameSearch = this.userNameSearch.getText();
		this.userNameSearch.setText("");
		
		AdminSearchUserWorker searchUserWorker = new AdminSearchUserWorker(userNameSearch, administratorFunctions);
		searchUserWorker.execute();
	}
	
	// -----------------------------------------------------------------------------------
	void addUser() {
		this.clearPanels();
		this.panelCentral.showCurrentSelectedPanel(PanelType.ADMIN_ADD_USER);
	}
	
	// -----------------------------------------------------------------------------------
	void deleteUserDisplay() {
		this.clearPanels();
		this.nameToDelete.setVisible(true);
		this.userNameDelete.setVisible(true);
		this.confirmDeleteButton.setVisible(true);
	}
	
	// -----------------------------------------------------------------------------------
	void goBack() {
		this.panelCentral.showCurrentSelectedPanel(
				PanelType.ADMIN_CENTRAL);
	}

	// -----------------------------------------------------------------------------------
	void logoutAdmin() {
		ProgramLogs event = ProgramLogs.ACCOUNT_ADD_SUCCESS;
		System.out.println(event.getLogMessage());
		event.logCurrentEvent(TOOL_TIP_TEXT_KEY, TOOL_TIP_TEXT_KEY, event);
		
		this.loginOperations.logOutuser();
		
		JOptionPane.showMessageDialog(null, "Log out successful");
		this.panelCentral.showCurrentSelectedPanel(PanelType.LOGIN);
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
	String[][] getRow() {
		return this.row;
	}
	
	// -----------------------------------------------------------------------------------
	String[] getCol() {
		return this.colArray;
	}
	
	// -----------------------------------------------------------------------------------
	void createTable() {
		this.table = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4138389809837691771L;

			@Override
	 		public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table.setBackground(Color.WHITE);
		table.getTableHeader().setBackground(Color.GRAY);
	}
	
	// -----------------------------------------------------------------------------------
	void updateTable() {
		this.initializeUserTable();
		this.model = new DefaultTableModel(this.getRow(), this.getCol());
		this.table.setRowSorter(sorter);
		this.table.setModel(model);
	}
	
	// -----------------------------------------------------------------------------------
	void clearPanels() {
		this.nameToSearch.setVisible(false);
		this.nameToDelete.setVisible(false);
		this.userNameSearch.setVisible(false);
		this.userNameDelete.setVisible(false);
		this.confirmDeleteButton.setVisible(false);
		this.confirmSearchButton.setVisible(false);
	}
}