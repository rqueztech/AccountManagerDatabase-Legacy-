package databaseproject;

import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

class AdminSearchUserWorker extends SwingWorker<Boolean, Void> {
	private String searchedUser;
	private AdministratorFunctions administratorFunctions;
	
	// -----------------------------------------------------------------------------------
	AdminSearchUserWorker(String searchedUser, AdministratorFunctions administratorFunctions) {
		this.searchedUser = searchedUser;
		this.administratorFunctions = administratorFunctions;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return this.administratorFunctions.isUserExists(searchedUser);
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