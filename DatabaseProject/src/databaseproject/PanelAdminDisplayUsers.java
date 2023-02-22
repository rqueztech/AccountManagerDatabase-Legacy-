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
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PanelAdminDisplayUsers extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 488767503804714638L;
	
	public Image image;
	public AdministratorFunctions administratorFunctions;
	public LoginOperations loginOperations;
	public GridBagConstraints grid;
	public PanelCentral panelCentral;
	public InputOperations inputOperations;
	
	private String row[][];
	private String colArray[];
	
	// First name, last name, and password Text Field
	private JTextField fName;
	private JTextField lName;

	public JButton confirmSearchButton = new JButton("Search!!!");
	public JButton confirmDeleteButton = new JButton("Delete!!!");
	
	public JLabel nameToDelete;
	public JLabel nameToSearch;
	
	public JTextField userNameSearch;
	public JTextField userNameAdd;
	public JTextField userNameDelete;
	
	public DefaultTableModel model;
	public TableRowSorter<DefaultTableModel> sorter;
	public JTable table;
	
	public StringBuilder sb = new StringBuilder();
	
	private JComboBox<String> gender;

	public PanelAdminDisplayUsers(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		this.panelCentral = panelCentral;
		this.administratorFunctions = administratorFunctions;
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
		this.add(label, grid);
		
		this.createTable();
		this.updateTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(380, 100));

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridwidth = 4;

		this.add(scrollPane, grid);
		
		this.nameToDelete = new JLabel("Delete User: ");
		this.nameToDelete.setBackground(Color.BLACK);
		this.nameToDelete.setForeground(Color.WHITE);
		this.nameToDelete.setOpaque(false);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 0;
		this.grid.gridy = 5;
		this.nameToDelete.setVisible(false);
		this.add(nameToDelete, grid);
		
		this.nameToSearch = new JLabel("Search User: ");
		this.nameToSearch.setBackground(Color.BLACK);
		this.nameToSearch.setForeground(Color.WHITE);
		this.nameToSearch.setOpaque(false);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 0;
		this.grid.gridy = 5;
		this.nameToSearch.setVisible(false);
		this.add(nameToSearch, grid);
		
		// Confirm SEARCN button
		this.confirmSearchButton.setBackground(Color.GRAY);
		this.confirmSearchButton.setForeground(Color.WHITE);
		this.confirmSearchButton.setOpaque(true);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 2;
		this.grid.gridy = 5;
		this.confirmSearchButton.setVisible(false);
		this.add(confirmSearchButton, grid);
		
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
		this.add(confirmDeleteButton, grid);
		
		this.confirmDeleteButton.addActionListener(e -> 
			this.confirmDeleteUser());
		
		// USER NAME ADD
		this.userNameAdd = new JTextField(10);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 1;
		this.grid.gridy = 5;
		this.userNameAdd.setVisible(false);
		this.add(userNameAdd, grid);
		
		// USER NAME SEARCH
		this.userNameSearch = new JTextField(10);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 1;
		this.grid.gridy = 5;
		this.userNameSearch.setVisible(false);
		this.add(userNameSearch, grid);
		
		// USER NAME DELETE
		this.userNameDelete = new JTextField(10);
		this.grid.gridwidth = 1;
		this.grid.gridheight = 1;
		this.grid.gridx = 1;
		this.grid.gridy = 5;
		this.userNameDelete.setVisible(false);
		this.add(userNameDelete, grid);
		
		// Search Button
		JButton addUserButton = new JButton("Add User");
		addUserButton.setBackground(Color.BLACK);
		addUserButton.setForeground(Color.GRAY);
		this.grid.gridx = 0;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(addUserButton, grid);
		addUserButton.addActionListener(e -> this.addUser());
		
		// Delete Button
		JButton deleteUserButton = new JButton("Delete User");
		deleteUserButton.setBackground(Color.BLACK);
		deleteUserButton.setForeground(Color.GRAY);
		this.grid.gridx = 1;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(deleteUserButton, grid);
		deleteUserButton.addActionListener(e -> this.deleteUser());
		
		JButton searchUserButton = new JButton("Search User");
		searchUserButton.setBackground(Color.black);
		searchUserButton.setForeground(Color.gray);
		this.grid.gridx = 2;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(searchUserButton, grid);
		searchUserButton.addActionListener(e -> searchUser());
		
		// Search Button
		JButton goBackButton = new JButton("Go Back");
		goBackButton.setBackground(Color.black);
		goBackButton.setForeground(Color.gray);
		this.grid.gridx = 3;
		this.grid.gridy = 3;
		this.grid.gridwidth = 1;
		this.add(goBackButton, grid);
		goBackButton.addActionListener(e -> this.goBack());
		
		// Search Button
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBackground(Color.black);
		logoutButton.setForeground(Color.gray);
		this.grid.gridx = 3;
		this.grid.gridy = 5;
		this.grid.gridwidth = 1;
		this.add(logoutButton, grid);
		logoutButton.addActionListener(e -> this.panelCentral.panelAdminAddUser.logoutAdmin());
	}

	// -----------------------------------------------------------------------------------
	public void updateTable() {
		this.initializeEmployeeTable();
		this.model = new DefaultTableModel(this.getRow(), this.getCol());
		this.table.setRowSorter(sorter);
		this.table.setModel(model);
	}
	
	// -----------------------------------------------------------------------------------
	public void confirmDeleteUser() {
		boolean isValid = this.administratorFunctions
				.inputOperations
				.isOnlyLettersAndNumbers(this.userNameDelete.getText());
		
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
				DeleteEmployeeSwingWorker worker = new DeleteEmployeeSwingWorker(userToDelete, this.administratorFunctions);
				worker.execute();
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
	public void initializeEmployeeTable() {
		int employeeHashMapSize = this.administratorFunctions.getEmployeeHashMap().size();
		
		this.row = new String[employeeHashMapSize][4];
		this.colArray = this.administratorFunctions.csvOperations.csvHeaderUser.split(",",4);
		
		Iterator<Entry<String, EmployeeNode>> hmIterator = 
				this.administratorFunctions.getEmployeeHashMap()
				.entrySet()
				.iterator();

		int currentCounter = 0;

		// Iterate through the hashmap
		while (hmIterator.hasNext()) {
			Map.Entry<String, EmployeeNode> mapElement = (Map.Entry<String, EmployeeNode>) hmIterator.next();

			this.row[currentCounter][0] = mapElement.getValue().getUserName();
			this.row[currentCounter][1] = mapElement.getValue().getFirstName();
			this.row[currentCounter][2] = mapElement.getValue().getLastName();
			this.row[currentCounter][3] = mapElement.getValue().getGender();
			
			currentCounter++;
		}
	}
	
	// -----------------------------------------------------------------------------------
	public void EmployeeTable() {
		int employeeHashMapSize = this.administratorFunctions.getEmployeeHashMap().size();
		
		this.row = new String[employeeHashMapSize][4];
		this.colArray = this.administratorFunctions.csvOperations.csvHeaderUser.split(",",4);
		
		Iterator<Entry<String, EmployeeNode>> hmIterator = 
				this.administratorFunctions.getEmployeeHashMap()
				.entrySet()
				.iterator();

		int currentCounter = 0;

		// Iterate through the hashmap
		while (hmIterator.hasNext()) {
			Map.Entry<String, EmployeeNode> mapElement = (Map.Entry<String, EmployeeNode>) hmIterator.next();

			this.row[currentCounter][0] = mapElement.getValue().getUserName();
			this.row[currentCounter][1] = mapElement.getValue().getFirstName();
			this.row[currentCounter][2] = mapElement.getValue().getLastName();
			this.row[currentCounter][3] = mapElement.getValue().getGender();
			
			currentCounter++;
		}
		
		this.model = new DefaultTableModel(this.getRow(), this.getCol());
	}
		
	// -----------------------------------------------------------------------------------
	public void clearBoxes() {
		this.fName.setText("");
		this.lName.setText("");
		this.gender.setSelectedIndex(0);
	}

	// -----------------------------------------------------------------------------------
	public void searchUser() {
		this.clearPanels();
		this.nameToSearch.setVisible(true);
		this.userNameSearch.setVisible(true);
		this.confirmSearchButton.setVisible(true);
	}
	
	// -----------------------------------------------------------------------------------
	public void confirmSearchUser() {
		String userNameSearch = this.userNameSearch.getText();
		this.userNameSearch.setText("");
		
		SearchEmployeeWorker searchEmployeeWorker = new SearchEmployeeWorker(userNameSearch, administratorFunctions);
		searchEmployeeWorker.execute();
	}
	
	// -----------------------------------------------------------------------------------
	public void addUser() {
		this.clearPanels();
		this.panelCentral.setCurrentPanelString(this.panelCentral.PANEL_ADMINADDUSER);
	}
	
	// -----------------------------------------------------------------------------------
	public void deleteUser() {
		this.clearPanels();
		this.nameToDelete.setVisible(true);
		this.userNameDelete.setVisible(true);
		this.confirmDeleteButton.setVisible(true);
	}
	
	// -----------------------------------------------------------------------------------
	public void setTableModel() {
		this.table.setModel(this.model);
	}
	
	// -----------------------------------------------------------------------------------
	public void goBack() {
		this.panelCentral.setCurrentPanelString(
				this.panelCentral.PANEL_ADMINCENTRAL);
	}

	// -----------------------------------------------------------------------------------
	public void logoutAdmin() {
		this.loginOperations.logOutuser();
		this.panelCentral.programLogs.logCurrentEvent(this.panelCentral.ADMIN, 
				this.loginOperations.getCurrentUser(), this.panelCentral.programLogs.getLOGOUT_SUCCESS());
		
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
	public String[][] getRow() {
		return this.row;
	}
	
	// -----------------------------------------------------------------------------------
	public String[] getCol() {
		return this.colArray;
	}
	
	// -----------------------------------------------------------------------------------
	public void createTable() {
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
	public void clearPanels() {
		this.nameToSearch.setVisible(false);
		this.nameToDelete.setVisible(false);
		this.userNameSearch.setVisible(false);
		this.userNameDelete.setVisible(false);
		this.confirmDeleteButton.setVisible(false);
		this.confirmSearchButton.setVisible(false);
	}
}

// This class will extend the SwingWorker thread, which will allow
// The action to be performed in a new thread to avoid performance issues
// With the java swing class
class DeleteEmployeeSwingWorker extends SwingWorker<Boolean, Void> {

	// Create two instance variables which will take the user to delete along
	// With the administratorfunctions instance being passed into it
    private String userToDelete;
    private AdministratorFunctions administratorFunctions;

    // Specific constructor will get the userToDelte and the administratorFunctions
    public DeleteEmployeeSwingWorker(String userToDelete, AdministratorFunctions administratorFunctions) {
        this.userToDelete = userToDelete;
        this.administratorFunctions = administratorFunctions;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        // Perform the delete operation here
    	
        return this.administratorFunctions.deleteUser(this.userToDelete);
    }

    @Override
    protected void done() {
        try {
            boolean success = get();
            if (success) {
                JOptionPane.showMessageDialog(null, "User deleted successfully.", "Delete Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error deleting user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null, "Error deleting user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class SearchEmployeeWorker extends SwingWorker<Boolean, Void> {
	private String searchedUser;
	private AdministratorFunctions administratorFunctions;
	
	public SearchEmployeeWorker(String searchedUser, AdministratorFunctions administratorFunctions) {
		this.searchedUser = searchedUser;
		this.administratorFunctions = administratorFunctions;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return this.administratorFunctions.isEmployeeExists(searchedUser);
	}
	
	@Override
	protected void done() {
		try {
            boolean success = get();
            StringBuilder sb = new StringBuilder();
            
            sb.append(searchedUser);
            
            if (success) {
            	sb.append(" User searched successfully");
                JOptionPane.showMessageDialog(null, sb.toString(), "Search Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else {
            	sb.append(" User search unsuccessful");
                JOptionPane.showMessageDialog(null, sb.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null, "Error searching error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
}