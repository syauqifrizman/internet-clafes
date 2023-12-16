package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sqlConnect.Connect;

public class User {

	//mendeklarasikan atribut yang dibutuhkan oleh User
	//id unik untuk setiap User
	private Integer userID;
	//nama dari user
	private String userName;
	//password user ersebut
	private String userPassword;
	//umut user
	private Integer userAge;
	//role user "Admin", "Operator", "Computer Technician", "Customer"
	private String userRole;
	
	//membuat kontruksi kelas User sesuai atributnya
	public User(Integer userID, String userName, String userPassword, Integer userAge, String userRole) {
		this.userID = userID;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAge = userAge;
		this.userRole = userRole;
	}
	//membuat kontruksi kelas User sesuai atributnya kecuali password
	public User(Integer userID, String userName, Integer userAge, String userRole) {
		this.userID = userID;
		this.userName = userName;
		this.userAge = userAge;
		this.userRole = userRole;
	}
	
	//membuat kontruksi kelas User sesuai atributnya kecuali id dan role
	public User(String userName, String userPassword, Integer userAge) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAge = userAge;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	//karena atribut dibuat private maka tidak dapat diakses secara langsung oleh kelas luar
	//maka untuk diakses kelas luar, dibutuhkan public getter(untuk mendapatkan atribut)
	//dan setter untuk menaruh value ke atribut) untuk setiap atribut private
	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	//method untuk mengambil data dari seluruh User yang ada dari database
	public static ArrayList<User> getAllUserData() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `user`");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Integer id;
			String name;
			String pass;
			Integer age;
			String role;
			
			id = rs.getInt(1);
			name = rs.getString(2);
			pass = rs.getString(3);
			age = rs.getInt(4);
			role = rs.getString(5);
			
			User user = new User(id, name, pass, age, role);
			users.add(user);
		}
		
		return users;
	}
	
	//method untuk mengecek apakah username tersebut ada di database
	public static boolean isUsernameExist(String username) throws SQLException {
		Connect db = Connect.getConnection();
		
		String query = "SELECT * FROM user WHERE userName = '%s'";
		String queryExecute = String.format(query, username);
		
		ResultSet res = db.executeQuery(queryExecute);
		
		boolean isExist = false;
		while(res.next()){
			isExist = true;
		}
		return isExist;
	}
	
	//method untuk mengambil data dari User berdasarkan nama dan password dari database
	public static User getUserData(String username, String password) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `user` WHERE userName = ? AND userPassword = ?");
		
		ps.setString(1, username);
		ps.setString(2, password);
		
		ResultSet rs = ps.executeQuery();
		
		User user = null;
		while(rs.next()) {
			Integer id;
			String name;
			String pass;
			Integer age;
			String role;
			
			id = rs.getInt(1);
			name = rs.getString(2);
			pass = rs.getString(3);
			age = rs.getInt(4);
			role = rs.getString(5);
			
			user = new User(id, name, pass, age, role);
		}
		
		return user;
	}
	
	//method untuk menambahkan user baru dengan role Cutomer ke database
	public static void AddNewUser(String username, String password, Integer age) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `user` VALUES (?, ?, ?, ?, ?)");
		
		ps.setInt(1, 0);
		ps.setString(2, username);
		ps.setString(3, password);
		ps.setInt(4, age);
		ps.setString(5, "Customer");
		
		ps.executeUpdate();
	}

	//method untuk mengubah role berdasarka id user dan role barunya dari database
	public static void ChangeUserRole(int id, String role) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("UPDATE `user` SET UserRole = ? WHERE userID = ?");
		
		ps.setString(1, role);
		ps.setInt(2, id);
		
		ps.executeUpdate();
	}
	
	//method untuk mengambil seluruh data dari User yang memiliki role "Computer Technician" dari database
	public static ArrayList<User> getAllTechnician() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `user` WHERE userRole = 'Computer Technician' ");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Integer id;
			String name;
			String pass;
			Integer age;
			String role;
			
			id = rs.getInt(1);
			name = rs.getString(2);
			pass = rs.getString(3);
			age = rs.getInt(4);
			role = rs.getString(5);
			
			User user = new User(id, name, pass, age, role);
			users.add(user);
		}
		
		return users;
	}
}
