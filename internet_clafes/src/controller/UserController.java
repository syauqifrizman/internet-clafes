package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.User;
import view.IView;

public class UserController {
	public static User getUserData(String username, String password, IView view) throws SQLException{
		
		return User.getUserData(username, password);
		
	}
	
	public ArrayList<User> getAllUserData(IView view){
		try {
			return User.getAllUserData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error Fetching Users");
			return new ArrayList<User>();
		}
	}
	
	public ArrayList<User> getAllTechnicianData(IView view){
		try {
			return User.getAllTechnician();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error Fetching Technician");
			return new ArrayList<User>();
		}
	}
	
	public static boolean AddNewUser(String username, String pass, int age, String confpass, IView view) {
		if(username.length() < 7) {
			view.showError("Username must be longer than 6 characters");
			return false;
		}
		
		if(!pass.equals(confpass)) {
			view.showError("Password doesn't match Confirmation Password");
			return false;
		}
		
		if(pass.length() == 0) {
			view.showError("Password cannot be empty");
			return false;
		}
		
		if(confpass.length() == 0) {
			view.showError("Password Confirmation cannot be empty");
			return false;
		}
		
		if(!pass.matches("[A-Za-z0-9]+")) {
			view.showError("Password must be Alphanumeric");
			return false;
		}
		
		if(age<13 || age>65) {
			view.showError("Age must be 13-65 (inclusive)");
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
	
	public boolean changeUserRole(int id, String newRole, IView view) {
		try {
			User.ChangeUserRole(id, newRole);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error Changing Roles");
			return false;
		}
	}
	
	
}
