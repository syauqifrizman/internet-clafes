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
		ArrayList<User> users = new ArrayList<User>();//membuat arraylist kosong untuk menampung data
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		//membuat query
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `user`");
		//menjalankan query dan mengambil hasilnya
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Integer id;
			String name;
			String pass;
			Integer age;
			String role;
			
			//hasil yang sudah diambil dimasukkan ke variabel sementara
			id = rs.getInt(1);
			name = rs.getString(2);
			pass = rs.getString(3);
			age = rs.getInt(4);
			role = rs.getString(5);
			
			//dari variabel sementara, dibuat sebuah object user baru
			User user = new User(id, name, pass, age, role);
			users.add(user);//object tersebut dimasukkan ke arraylist yang sudah dibuat
		}
		
		return users;//araylist yang sudah dibuat akan di-return
	}
	
	//method untuk mengecek apakah username tersebut ada di database
	public static boolean isUsernameExist(String username) throws SQLException {
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		//membuat query
		String query = "SELECT * FROM user WHERE userName = '%s'";
		//mengisi tanda %s pada query dengan data yang sudah diterima
		String queryExecute = String.format(query, username);
		//menjalankan query dan mengambil hasilnya
		ResultSet res = db.executeQuery(queryExecute);
		
		//mengecek apakah username berada di database
		//mendeklarasi isExist dengan tipe data boolean yang diisi dengan false
		boolean isExist = false;
		while(res.next()){//mengecek seluruh databse
			//selama username pernah muncul saat di cek di database maka isExist akan menjadi true
			isExist = true;
		}
		return isExist;//isExist akan di-return
	}
	
	//method untuk mengambil data dari User berdasarkan nama dan password dari database
	public static User getUserData(String username, String password) throws SQLException{
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		//membuat query
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `user` WHERE userName = ? AND userPassword = ?");
		//mengisi tanda ? pada query dengan data yang sudah diterima
		ps.setString(1, username);
		ps.setString(2, password);
		//menjalankan query dan mengambil hasilnya
		ResultSet rs = ps.executeQuery();
		
		//mendeklarasi user dengan tipe data User
		User user = null;
		while(rs.next()) {
			Integer id;
			String name;
			String pass;
			Integer age;
			String role;
			
			//hasil yang sudah diambil dimasukkan ke variabel sementara
			id = rs.getInt(1);
			name = rs.getString(2);
			pass = rs.getString(3);
			age = rs.getInt(4);
			role = rs.getString(5);
			
			//dari variabel sementara, dibuat sebuah object user baru dan memasukannya ke dalam user
			user = new User(id, name, pass, age, role);
		}
		
		return user;//object yang sudah dibuat akan di-return
	}
	
	//method untuk menambahkan user baru dengan role Cutomer ke database
	public static void AddNewUser(String username, String password, Integer age) throws SQLException {
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		//membuat query
		PreparedStatement ps = db.prepareStatement("INSERT INTO `user` VALUES (?, ?, ?, ?, ?)");
		//mengisi tanda ? pada query dengan data yang sudah diterima
		ps.setInt(1, 0);
		ps.setString(2, username);
		ps.setString(3, password);
		ps.setInt(4, age);
		ps.setString(5, "Customer");
		//menjalankan query
		ps.executeUpdate();
	}

	//method untuk mengubah role berdasarka id user dan role barunya dari database
	public static void ChangeUserRole(int id, String role) throws SQLException {
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		//membuat query
		PreparedStatement ps = db.prepareStatement("UPDATE `user` SET UserRole = ? WHERE userID = ?");
		//mengisi tanda ? pada query dengan data yang sudah diterima
		ps.setString(1, role);
		ps.setInt(2, id);
		//menjalankan query
		ps.executeUpdate();
	}
	
	//method untuk mengambil seluruh data dari User yang memiliki role "Computer Technician" dari database
	public static ArrayList<User> getAllTechnician() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();//membuat arraylist kosong untuk menampung data
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		//membuat query
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `user` WHERE userRole = 'Computer Technician' ");
		//menjalankan query dan mengambil hasilnya
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Integer id;
			String name;
			String pass;
			Integer age;
			String role;
			
			//hasil yang sudah diambil dimasukkan ke variabel sementara
			id = rs.getInt(1);
			name = rs.getString(2);
			pass = rs.getString(3);
			age = rs.getInt(4);
			role = rs.getString(5);
			
			//dari variabel sementara, dibuat sebuah object user baru
			User user = new User(id, name, pass, age, role);
			users.add(user);//object tersebut dimasukkan ke arraylist yang sudah dibuat
		}
		
		return users;//araylist yang sudah dibuat akan di-return
	}
}
