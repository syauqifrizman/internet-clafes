package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.User;

public class UserController {
	public static User getUserData(String username, String password) throws SQLException{
		
		return User.getUserData(username, password);
		
	}
	
	public ArrayList<User> getAllUserData(){
		try {
			return User.getAllUserData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Fetching Users");
			return new ArrayList<User>();
		}
	}
	
	public ArrayList<User> getAllTechnicianData(){
		try {
			return User.getAllTechnician();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Fetching Technician");
			return new ArrayList<User>();
		}
	}
	
	public static boolean AddNewUser(String username, String pass, int age, String confpass) {
		if(username.length() < 7) {
			Helper.showAlert(AlertType.ERROR, "Username must be longer than 6 characters");
			return false;
		}
		
		if(!pass.equals(confpass)) {
			Helper.showAlert(AlertType.ERROR, "Password doesn't match Confirmation Password");
			return false;
		}
		
		if(pass.length() == 0) {
			Helper.showAlert(AlertType.ERROR, "Password cannot be empty");
			return false;
		}
		
		if(confpass.length() == 0) {
			Helper.showAlert(AlertType.ERROR, "Password Confirmation cannot be empty");
			return false;
		}
		
		if(!pass.matches("[A-Za-z0-9]+")) {
			Helper.showAlert(AlertType.ERROR, "Password must be Alphanumeric");
			return false;
		}
		
		if(age<13 || age>65) {
			Helper.showAlert(AlertType.ERROR, "Age must be 13-65 (inclusive)");
			return false;
		}
		
		try {
			User.AddNewUser(username, pass, age);;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//view.showError("Error Saving to Database");
			return false;
		}
		
		return true;
	}
	
	public boolean changeUserRole(int id, String newRole) {
		try {
			User.ChangeUserRole(id, newRole);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Changing Roles");
			return false;
		}
	}
	
	
}
