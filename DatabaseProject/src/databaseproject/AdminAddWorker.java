package databaseproject;

import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

class AdminAddWorker extends SwingWorker<Boolean, Void> {
	private String firstName;
	private String lastName;
	private String userCreationMessage;
	private AdministratorFunctions administratorFunctions;
	
	/**
	 * This class constructs the general information required to create an user
	 * in a separate thread to be passed in to the createUser function found in
	 * the AdministratorFunctions class
	 * @param firstName gets the first name of the user to be added
	 * @param lastName gets the last name of the user to be added
	 * @param gender gets the gender of the user to be added
	 * @param administratorFunctions
	 */
	AdminAddWorker(String firstName, String lastName, 
			AdministratorFunctions administratorFunctions) {
		
		this.administratorFunctions = administratorFunctions;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		boolean isAdminCreated = false;
		
		char[] mgrPassword = JOptionPane.showInputDialog("Enter Manager Password").toCharArray();
		boolean checkAdminPassword = this.administratorFunctions.loginOperations.adminLoginOperations
				.isAdminPassphrase(mgrPassword);
		
		if(checkAdminPassword) {
			isAdminCreated = this.administratorFunctions.createNewAdmin(firstName, lastName);
				SwingUtilities.invokeLater(() -> {
					this.administratorFunctions.panelCentral.panelAdminDisplayUsers.updateTable();
				});
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
            	JOptionPane.showMessageDialog(null, this.userCreationMessage, "Creation Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null, "Error searching error.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
	}
}