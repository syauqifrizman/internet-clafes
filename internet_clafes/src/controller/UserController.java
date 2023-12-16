package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.User;

public class UserController {
	public static String loginUser(String username, String password) throws SQLException {
		if(username.isEmpty()) {
			return "Username must be filled";
		}
		else if(!isUsernameExist(username)) {
			return "Username must exist in database";
		}
		else if(password.isEmpty()) {
			return "Password must be filled";
		}
		
		User userLogin = User.getUserData(username, password);
		if(userLogin == null) {
			return "Password must match with the username in database";			
		}
		
		return "Login Success!";
	}
	
	public static User getUserData(String username, String password) throws SQLException{
		
		return User.getUserData(username, password);
		
	}
	
	private static boolean isUsernameExist(String username) throws SQLException {
		return User.isUsernameExist(username);
	}
	
	public static ArrayList<User> getAllUserData(){
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
	
	public static String AddNewUser(String username, String pass, String age, String confpass) {
//		username validation
		if(username.isEmpty()) {
//			Helper.showAlert(AlertType.ERROR, "Username can't be empty");
			return "Username can't be empty";
//			return false;
		}
		else if(username.length() < 7) {
//			Helper.showAlert(AlertType.ERROR, "Username minimum 7 character long");
			return "Username minimum 7 character long";
//			return false;
		} else
			try {
				if(isUsernameExist(username)) {
//				Helper.showAlert(AlertType.ERROR, "Username must be unique");
					return "The username already exist, username must be unique. Please enter new username";
//				return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "Error find username in the database";
			}

//		password validation
		if(pass.length() == 0) {
//			Helper.showAlert(AlertType.ERROR, "Password cannot be empty");
			return "Password cannot be empty";
//			return false;
		}
		else if(pass.length() < 6) {
//			Helper.showAlert(AlertType.ERROR, "Password minimum 6 character long");
			return "Password minimum 6 character long";
//			return false;
		}
		else if(!pass.matches("[A-Za-z0-9]+")) {
//			Helper.showAlert(AlertType.ERROR, "Password must be Alphanumeric");
			return "Password must be Alphanumeric";
//			return false;
		}
		
//		confirm password validation
		if(confpass.length() == 0) {
//			Helper.showAlert(AlertType.ERROR, "Password Confirmation cannot be empty");
			return "Password Confirmation cannot be empty";
//			return false;
		}
		else if(!confpass.equals(pass)) {
//			Helper.showAlert(AlertType.ERROR, "Confirmation Password doesn't match, must same with Password");
			return "Confirmation Password doesn't match, must same with Password";
//			return false;
		}
		
//		user age validation
		if(age.isEmpty()) {
//			Helper.showAlert(AlertType.ERROR, "Age cannot be empty");
			return "Age cannot be empty";
//			return false;
		}
		else if(Integer.parseInt(age) < 13 || Integer.parseInt(age) > 65) {
//			Helper.showAlert(AlertType.ERROR, "Age must be 13-65 (inclusive)");
			return "Age must be 13-65 (inclusive)";
//			return false;
		}
		
		try {
			Integer ageInput = Integer.parseInt(age);
			User.AddNewUser(username, pass, ageInput);;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//view.showError("Error Saving to Database");
			return "Registration Failed";
		}
		
		return "Registration Successful";
	}
	
	public static boolean changeUserRole(int id, String newRole) {
		if(newRole.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Cannot be empty");
			return false;
		}
		if(newRole.equals("Operator") 
				|| newRole.equals("Admin")
				|| newRole.equals("Computer Technician")
				) {
			try {
				User.ChangeUserRole(id, newRole);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Helper.showAlert(AlertType.ERROR, "Error Changing Roles");
				return false;
			}
			
		}
		Helper.showAlert(AlertType.ERROR, "Roles must be Operator / Admin / Computer Technician");
		return false;
		
	}
	
	
}
