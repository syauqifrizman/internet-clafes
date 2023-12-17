package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.User;

public class UserController {
	
	//method untuk login 
	public static String loginUser(String username, String password) throws SQLException {
		
		//kalau username nya kosong, maka akan error
		if(username.isEmpty()) {
			return "Username must be filled";
		}
		
		//kalau username tidak ada di database, maka akan error
		else if(!isUsernameExist(username)) {
			return "Username must exist in database";
		}
		
		//kalau password kosong, maka akan error
		else if(password.isEmpty()) {
			return "Password must be filled";
		}
		
		//mencari dan mendapatkan User dari database sesuai dari credential yang dimasukkan
		User userLogin = User.getUserData(username, password);
		
		//kalau tidak return apa2, berarti password nya salah
		if(userLogin == null) {
			return "Password must match with the username in database";			
		}
		
		return "Login Success!";
	}
	
	//method untuk mengambil user data dengan username dan password
	public static User getUserData(String username, String password) throws SQLException{
		
		return User.getUserData(username, password);
		
	}
	
	//method untuk cek apakah username sudah ada di database atau belum
	private static boolean isUsernameExist(String username) throws SQLException {
		return User.isUsernameExist(username);
	}
	
	//method untuk mendapatkan semua user dari database
	public static ArrayList<User> getAllUserData(){
		try {
			return User.getAllUserData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Fetching Users");
			return new ArrayList<User>();
		}
	}
	
	//method untuk mendapatkan semua user dengan role "Computer Technician"
	public ArrayList<User> getAllTechnicianData(){
		try {
			return User.getAllTechnician();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Fetching Technician");
			return new ArrayList<User>();
		}
	}
	
	//method untuk menambah user baru/registrasi
	public static String AddNewUser(String username, String pass, String age, String confpass) {
//		username validation
		//kalau kosong, maka akan error
		if(username.isEmpty()) {
//			Helper.showAlert(AlertType.ERROR, "Username can't be empty");
			return "Username can't be empty";
//			return false;
		}
		
		//kalau panjang username kurang dari 7 karakter maka akan error
		else if(username.length() < 7) {
//			Helper.showAlert(AlertType.ERROR, "Username minimum 7 character long");
			return "Username minimum 7 character long";
//			return false;
		} else
			try {
				//kalau sudah ada username nya, maka akan error
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
		//kalau password kosong, maka akan error
		if(pass.length() == 0) {
//			Helper.showAlert(AlertType.ERROR, "Password cannot be empty");
			return "Password cannot be empty";
//			return false;
		}
		
		//kalau password kurang dari 6 karakter, maka akan error
		else if(pass.length() < 6) {
//			Helper.showAlert(AlertType.ERROR, "Password minimum 6 character long");
			return "Password minimum 6 character long";
//			return false;
		}
		
		//kalau password bukan alphanumeric, maka akan error
		else if(!pass.matches("[A-Za-z0-9]+")) {
//			Helper.showAlert(AlertType.ERROR, "Password must be Alphanumeric");
			return "Password must be Alphanumeric";
//			return false;
		}
		
//		confirm password validation
		//kalau confirm password kosong, maka akan error
		if(confpass.length() == 0) {
//			Helper.showAlert(AlertType.ERROR, "Password Confirmation cannot be empty");
			return "Password Confirmation cannot be empty";
//			return false;
		}
		
		//kalau confirm password tidak sesuai password, maka akan error
		else if(!confpass.equals(pass)) {
//			Helper.showAlert(AlertType.ERROR, "Confirmation Password doesn't match, must same with Password");
			return "Confirmation Password doesn't match, must same with Password";
//			return false;
		}
		
//		user age validation
		//kalau age kosong, maka akan error
		if(age.isEmpty()) {
//			Helper.showAlert(AlertType.ERROR, "Age cannot be empty");
			return "Age cannot be empty";
//			return false;
		}
		
		//kalau umur kurang dari 13 atau lebih dari 65, maka akan error
		else if(Integer.parseInt(age) < 13 || Integer.parseInt(age) > 65) {
//			Helper.showAlert(AlertType.ERROR, "Age must be 13-65 (inclusive)");
			return "Age must be 13-65 (inclusive)";
//			return false;
		}
		
		//memanggil method penambahan user di model
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
	
	//method untuk mengganti role
	public static boolean changeUserRole(int id, String newRole) {
		
		//validasi kalau role kosong, maka akan error
		if(newRole.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Cannot be empty");
			return false;
		}
		
		//validasi kalau role yang dimasukkan bukan Operator/Admin/Computer Technician, maka akan error
		else if(newRole.equals("Operator") 
				|| newRole.equals("Admin")
				|| newRole.equals("Computer Technician")
				) {
			try {
				//memanggil method dari model untuk mengganti role
				User.ChangeUserRole(id, newRole);
				Helper.showAlert(AlertType.INFORMATION, "Success change the role to: " + newRole);
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
