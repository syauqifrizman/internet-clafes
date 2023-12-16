package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.TransactionController;
import sqlConnect.Connect;

public class PCBook {

	//mendeklarasikan atribut yang dibutuhkan oleh PCBook
	//id unik untuk setiap book
	private Integer bookID;
	//id pc yang akan di book
	private Integer pc_ID;
	//id user(Customer) yang membooking pc
	private Integer userID;
	//tanggal pc tersebut di book oleh user
	private Date bookedDate;
	
	//membuat kontruksi kelas PCBook sesuai atributnya
	public PCBook(Integer bookID, Integer pc_ID, Integer userID, Date bookedDate2) {
		this.bookID = bookID;
		this.pc_ID = pc_ID;
		this.userID = userID;
		this.bookedDate = bookedDate2;
	}
	
	//karena atribut dibuat private maka tidak dapat diakses secara langsung oleh kelas luar
	//maka untuk diakses kelas luar, dibutuhkan public getter(untuk mendapatkan atribut)
	//dan setter untuk menaruh value ke atribut) untuk setiap atribut private
	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public Integer getPc_ID() {
		return pc_ID;
	}

	public void setPc_ID(Integer pc_ID) {
		this.pc_ID = pc_ID;
	}

	public Integer getUserID() {
		return userID;
	}
	
	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Date getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}

	//method untuk menghapus bookdata berdasarkan bookid dari database
	public static void deleteBookData(Integer bookID) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("DELETE FROM `pcbooks` WHERE BookID = ?");
		
		ps.setInt(1, bookID);

		ps.executeUpdate();
	}
	
	//method untuk mengambil data berdasarkan pc id dan tanggalnya dari database
	public static PCBook getPCBookedData(Integer pc_ID, Date date) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `pcbooks` WHERE pc_ID = ? AND bookedDate = ?");
		ps.setInt(1, pc_ID);
		ps.setDate(2, date);
		
		ResultSet rs = ps.executeQuery();
		
		
		PCBook pcbook = null;
		while(rs.next()) {
			Integer bookID;
			Integer userID;
			
			bookID = rs.getInt(1);
			userID = rs.getInt(3);
			
			pcbook = new PCBook(bookID, pc_ID, userID, date);
		}
		
		return pcbook;
	}
	
	//method untuk mengambil data berdasarkan book id dari database
	public static PCBook getPCBookedDetail(Integer bookID) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `pcbooks` WHERE bookID = ?");
		ps.setInt(1, bookID);
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			Integer pc_ID;
			Integer userID;
			Date bookedDate;
			
			pc_ID = rs.getInt(2);
			userID = rs.getInt(3);
			bookedDate = rs.getDate(4);
			
			pcbook = new PCBook(bookID, pc_ID, userID, bookedDate);
		}
		
		return pcbook;
	}
	
	//method untuk menambahkan book dengan pc id, user id, dan tanggalnya dari database/query
	public static void addNewBook(Integer pc_ID, Integer userID, Date bookedDate) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `pcbooks` VALUES (?, ?, ?, ?)");
		ps.setInt(1, 0);
		ps.setInt(2, pc_ID);
		ps.setInt(3, userID);
		ps.setDate(4, bookedDate);
		
		ps.executeUpdate();
	}
	
	//method untuk menyelesaikan book
	//method ini akan meambahkan book data ke transaksi dan menghapus book data 
	public static void finishBook(ArrayList<PCBook> pcbooks, Integer StaffID) throws SQLException {
		TransactionController.AddTransaction(pcbooks, StaffID);
		for (PCBook pcBook : pcbooks) {
			deleteBookData(pcBook.getBookID());
		}
	}
	
	//method untuk mendapat setiap book data yang ada dari database
	public static ArrayList<PCBook> getAllPCBookedData() throws SQLException{
		Connect db = Connect.getConnection();
		ArrayList<PCBook> pcbooks = new ArrayList<PCBook>();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `pcbooks`");
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			Integer bookID;
			Integer pc_ID;
			Integer userID;
			Date bookedDate;
			
			bookID = rs.getInt(1);
			pc_ID = rs.getInt(2);
			userID = rs.getInt(3);
			bookedDate = rs.getDate(4);
			
			pcbook = new PCBook(bookID, pc_ID, userID, bookedDate);
			pcbooks.add(pcbook);
		}
		
		return pcbooks;
		
	}
	
	//method untuk mendapat setiap book data yang ada di tanggal tertentu dari database
	public static ArrayList<PCBook> getPCBookedByDate(Date selectedDate) throws SQLException{
		Connect db = Connect.getConnection();
		ArrayList<PCBook> pcbooks = new ArrayList<>();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `pcbooks` WHERE DATE(bookedDate) = ?");
		ps.setDate(1, selectedDate);
		
		ResultSet rs = ps.executeQuery();
		
//		PCBook pcbook = null;
		while(rs.next()) {
			
			Integer bookID = rs.getInt(1);
			Integer pc_ID = rs.getInt(2);
			Integer userID = rs.getInt(3);
			
			PCBook pcbook = new PCBook(bookID, pc_ID, userID, selectedDate);
			pcbooks.add(pcbook);
		}
		
		return pcbooks;
		
	}
	
	//method untuk memindahkan user ke pc lain yang kosong dari databse
	//method ini akan mengubah pd id berdasarkan pc id baru dan book id di book data pcbooks 
	public static void assignUsertoNewPC(Integer bookID, Integer newPCID) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("UPDATE `pcbooks` SET pc_ID = ? WHERE bookID= ?");
	
		ps.setInt(1, newPCID);
		ps.setInt(2, bookID);
		
		ps.executeUpdate();
	}
	
	
	//method untuk mengambil data berdasarkan pc id dari database
	public static PCBook getPCBookedByID(Integer pcID) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `pcbooks` WHERE pc_ID = ?");
		ps.setInt(1, pcID);
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			Integer bookID;
			Integer userID;
			Date bookedDate;
			
			bookID = rs.getInt(1);
			userID = rs.getInt(3);
			bookedDate = rs.getDate(4);
			
			pcbook = new PCBook(bookID, pcID, userID, bookedDate);
		}
		
		return pcbook;
	}
	
}
