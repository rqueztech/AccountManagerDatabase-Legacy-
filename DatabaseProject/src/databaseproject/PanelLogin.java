package databaseproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class PanelLogin extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -702019029783943216L;
	
	public PanelCentral panelCentral;
	public PanelAdminCentral panelAdminCentral;
	public ProgramLogs programLogs;
	
	public Image image;
	public GridBagConstraints grid;
	public ProgramLogs pgmLogs;
	
	JPanel pwdInput;
	
	// Text fields for usrName and Password
	public JTextField usrName;
	public JPasswordField usrPassword;
	
	public AdministratorFunctions administratorFunctions;
	
	public LoginOperations loginOperations;
	public CSVOperations csvOperations;
	
	// This instance does not rely on other instances
	public InputOperations inputOperations;
	
	
	// Text boxes that will be used for password reset prompt
	public JTextField usrDefaultChange;
	public JTextField usrDefaultChangeRepeat;
	public JTextField passphraseInput;
	
	public PanelLogin(AdministratorFunctions administratorFunctions, PanelCentral panelCentral) {
		// Potentially remove login Ops and CSV OPS 
		// class
		this.administratorFunctions = administratorFunctions;
		
		this.csvOperations = this.administratorFunctions.csvOperations;
		this.loginOperations = this.administratorFunctions.loginOperations;
		this.panelCentral = panelCentral;
		this.programLogs = this.panelCentral.programLogs;
		
		this.panelAdminCentral = new PanelAdminCentral(this.administratorFunctions, 
				this.panelCentral);
		
		// Initialize instances that don't depend on other instances
		this.inputOperations = new InputOperations();
		
		this.setSize(600, 600);
		this.image = new ImageIcon("backgroundd.jpg").getImage();
		
		this.setLayout(new GridBagLayout());
		this.grid = new GridBagConstraints();
		
		this.passphraseInput = new JTextField();
		
		//-----------------------------------------------------------------------------------
		// NAME_USER: This whole sector deals with the label and textfield
		// For the username
		JLabel fLab = new JLabel("Username");
		this.grid.gridx = 0;
		this.grid.gridy = 0;
		this.grid.gridwidth = 1;
		fLab.setForeground(Color.WHITE);
		this.add(fLab, grid);
		
		// Input User Name to login
		this.usrName = new JTextField(15);
		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridwidth = 2;
		this.add(this.usrName, grid);
		
		//-----------------------------------------------------------------------------------
		// PASSWORD_USER: This whole sector deals with the label and textfield
		// For the password
		JLabel lLab = new JLabel("Password");
		this.grid.gridx = 0;
		this.grid.gridy = 2;
		this.grid.gridwidth = 1;
		lLab.setForeground(Color.WHITE);
		this.add(lLab, grid);
		
		this.usrPassword = new JPasswordField(15);
		this.grid.gridx = 0;
		this.grid.gridy = 3;
		this.grid.gridwidth = 2;
		this.add(this.usrPassword, grid);
		
		//-----------------------------------------------------------------------------------
		// LOGIN_BUTTON: This whole section deals with the login for both the user and
		// The Admin login
		
		JButton userLoginButton = new JButton("Login");
		userLoginButton.setBackground(Color.black);
		userLoginButton.setForeground(Color.gray);
		this.grid.gridx = 0;
		this.grid.gridy = 4;
		this.grid.gridwidth = 1;
		this.add(userLoginButton, grid);
		userLoginButton.addActionListener(e -> this.userLoginMethod());
		
		// Login Admin Button
		JButton adminLoginButton = new JButton("Admin");
		adminLoginButton.setBackground(Color.black);
		adminLoginButton.setForeground(Color.gray);
		this.grid.gridx = 1;
		this.grid.gridy = 4;
		this.grid.gridwidth = 1;
		this.add(adminLoginButton, grid);
		adminLoginButton.addActionListener(e -> this.adminLoginMethod());
		
		//Enter String (BOTH)
		this.pwdInput = new JPanel();
		
		this.grid.gridx = 0;
		this.grid.gridy = 0;
		this.grid.gridwidth = 1;
		this.pwdInput.add(new JLabel("Enter Password: "), grid);
		
		this.grid.gridx = 1;
		this.grid.gridy = 0;
		this.grid.gridwidth = 1;
		this.usrDefaultChange = new JTextField(16);
		this.pwdInput.add(this.usrDefaultChange, grid);
		
		this.grid.gridx = 0;
		this.grid.gridy = 1;
		this.grid.gridwidth = 1;
		this.pwdInput.add(new JLabel("Repeat Password: "), grid);
		
		this.grid.gridx = 1;
		this.grid.gridy = 1;
		this.grid.gridwidth = 1;
		this.usrDefaultChangeRepeat = new JTextField(16);
		this.pwdInput.add(this.usrDefaultChangeRepeat, grid);
	}
	
	//-----------------------------------------------------------------------------------
	// The action method for the login button
	public void userLoginMethod() {
		String userName = usrName.getText();
		char[] userPasswordEntered = this.usrPassword.getPassword();
		UserLoginWorker userLogin = new UserLoginWorker(userName,userPasswordEntered,administratorFunctions);
		this.clearTextBoxes();
		userLogin.execute();
	}
	
	//-----------------------------------------------------------------------------------
	// The action method for the Administrator Login
	public void adminLoginMethod() {
		String adminName = usrName.getText();
		char[] adminPasswordEntered = this.usrPassword.getPassword();
		AdminLoginWorker adminLogin = new AdminLoginWorker(adminName,adminPasswordEntered,administratorFunctions);
		this.clearTextBoxes();
		adminLogin.execute();
	}
	
	//------------------------------------------------------------------------------------
	public void clearTextBoxes() {
		this.usrName.setText("");
		this.usrPassword.setText("");
	}
	
	//------------------------------------------------------------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2D = (Graphics) g;
		g2D.drawImage(image, 0, 0, null);
	}
}