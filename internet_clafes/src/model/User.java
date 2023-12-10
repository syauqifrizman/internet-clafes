package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class User {
	
	private int UserID;
	private String UserName;
	private String userPassword;
	private int UserAge;
	private String UserRole;
	
	public User(int userID, String userName, String userPassword, int userAge, String userRole) {
		super();
		UserID = userID;
		UserName = userName;
		this.userPassword = userPassword;
		UserAge = userAge;
		UserRole = userRole;
	}
	
	public User(int userID, String userName, int userAge, String userRole) {
		super();
		UserID = userID;
		UserName = userName;
		UserAge = userAge;
		UserRole = userRole;
	}

	public int getUserID() {
		return UserID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUserAge() {
		return UserAge;
	}

	public void setUserAge(int userAge) {
		UserAge = userAge;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}
	
	public static ArrayList<User> getAllUserData() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM 'User'");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int id;
			String name;
			String pass;
			int age;
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
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM 'User' WHERE UserName = ?, userPassword = ?");
		
		ps.setString(1, username);
		ps.setString(2, password);
		
		ResultSet rs = ps.executeQuery();
		
		User user = null;
		while(rs.next()) {
			int id;
			String name;
			String pass;
			int age;
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
	
	public void AddNewUser(String username, String password, int age) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO 'User' VALUES (?, ?, ?, ?, ?)");
		
		ps.setInt(1, 0);
		ps.setString(2, username);
		ps.setString(3, password);
		ps.setInt(4, age);
		ps.setString(5, "Customer");
		
		ps.executeUpdate();
	}

	public void ChangeUserRole(int id, String role) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("UPDATE 'User' SET UserRole = ? WHERE UserID = ?");
		
		ps.setString(1, role);
		ps.setInt(2, id);
		
		ps.executeUpdate();
	}
	
	public static ArrayList<User> getAllTechnician() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM 'User' WHERE UserRole = 'Technician' ");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int id;
			String name;
			String pass;
			int age;
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
