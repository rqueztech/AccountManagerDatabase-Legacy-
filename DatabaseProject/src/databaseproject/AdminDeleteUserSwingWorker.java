package databaseproject;

import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

//This class will extend the SwingWorker thread, which will allow
//The action to be performed in a new thread to avoid performance issues
//With the java swing class
class AdminDeleteUserSwingWorker extends SwingWorker<Boolean, Void> {

	// Create two instance variables which will take the user to delete along
	// With the administratorfunctions instance being passed into it
	private String userToDelete;
	private AdministratorFunctions administratorFunctions;
	
	// Specific constructor will get the userToDelte and the administratorFunctions
	AdminDeleteUserSwingWorker(String userToDelete, AdministratorFunctions administratorFunctions) {
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