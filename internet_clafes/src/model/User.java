package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sqlConnect.Connect;

public class User {

	private Integer userID;
	private String userName;
	private String userPassword;
	private Integer userAge;
	private String userRole;
	
	public User(Integer userID, String userName, String userPassword, Integer userAge, String userRole) {
		this.userID = userID;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAge = userAge;
		this.userRole = userRole;
	}
	
	public User(Integer userID, String userName, Integer userAge, String userRole) {
		this.userID = userID;
		this.userName = userName;
		this.userAge = userAge;
		this.userRole = userRole;
	}
	
	public User(String userName, String userPassword, Integer userAge) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAge = userAge;
	}
	
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

	public static ArrayList<User> getAllUserData() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `User`");
		
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
	
	public static User getUserData(String username, String password) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `User` WHERE UserName = '?', userPassword = '?'");
		
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
	
	public static void AddNewUser(String username, String password, int age) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `User` VALUES (?, '?', '?', ?, '?')");
		
		ps.setInt(1, 0);
		ps.setString(2, username);
		ps.setString(3, password);
		ps.setInt(4, age);
		ps.setString(5, "Customer");
		
		ps.executeUpdate();
	}

	public static void ChangeUserRole(int id, String role) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("UPDATE `User` SET UserRole = '?' WHERE UserID = ?");
		
		ps.setString(1, role);
		ps.setInt(2, id);
		
		ps.executeUpdate();
	}
	
	public static ArrayList<User> getAllTechnician() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `User` WHERE UserRole = 'Technician' ");
		
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
