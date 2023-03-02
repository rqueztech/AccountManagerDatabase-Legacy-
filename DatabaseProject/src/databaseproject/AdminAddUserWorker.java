package databaseproject;

import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

class AddUserWorker extends SwingWorker<Boolean, Void> {
	private String firstName;
	private String lastName;
	private String gender;
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
	AddUserWorker(String firstName, String lastName, 
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
				.adminLoginOperations.isAdminPassphrase(mgrPassword);
		
		if(checkAdminPassword) {
			
			isUserCreated = this.administratorFunctions.createNewUser
					(firstName, lastName, gender);
			
			
			SwingUtilities.invokeLater(() -> {
				this.administratorFunctions.panelCentral.panelAdminDisplayUsers.updateTable();
			});
				
			
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
            	JOptionPane.showMessageDialog(null, this.userCreationMessage, "Creation Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null, "Error searching error.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
	}
}