package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import sqlConnect.Connect;

public class PCBook {

	private Integer bookID;
	private Integer pc_ID;
	private Integer userID;
	private Date bookedDate;
	
	public PCBook(Integer bookID, Integer pc_ID, Integer userID, Date bookedDate2) {
		this.bookID = bookID;
		this.pc_ID = pc_ID;
		this.userID = userID;
		this.bookedDate = bookedDate2;
	}
	
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

	public static void deleteBookData(Integer bookID) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("DELETE FROM `pcbooks` WHERE BookID = ?");
		
		ps.setInt(1, bookID);

		ps.executeUpdate();
	}
	
	public static PCBook getPCBookedData(Integer pc_ID, Date date) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `pcbooks` WHERE PC_ID = ? AND BookedDate = ?");
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
	
	public static PCBook getPCBookedDetail(Integer bookID) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `pcbooks` WHERE BookID = ?");
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
	
	public static void addNewBook(Integer pc_ID, Integer userID, Date bookedDate) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `pcbooks` VALUES (?, ?, ?, ?)");
		ps.setInt(1, 0);
		ps.setInt(2, pc_ID);
		ps.setInt(3, userID);
		ps.setDate(4, bookedDate);
		
		ps.executeUpdate();
	}
	
	public static void finishBook(ArrayList<PCBook> pcbooks) throws SQLException {
		for (PCBook pcBook : pcbooks) {
			deleteBookData(pcBook.getBookID());
		}
	}
	
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
			Date bookedDate = rs.getDate(4);
			
			PCBook pcbook = new PCBook(bookID, pc_ID, userID, selectedDate);
			pcbooks.add(pcbook);
		}
		
		return pcbooks;
		
	}
	
	public static void assignUsertoNewPC(Integer bookID, Integer newPCID) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("UPDATE `pcbooks` SET pc_ID = ? WHERE bookID= ?");
	
		ps.setInt(1, newPCID);
		ps.setInt(2, bookID);
		
		ps.executeUpdate();
	}
	
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
