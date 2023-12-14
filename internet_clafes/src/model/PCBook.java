package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sqlConnect.Connect;

public class PCBook {

	private Integer bookID;
	private Integer pc_ID;
	private Integer userID;
	private String bookedDate;
	
	public PCBook(Integer bookID, Integer pc_ID, Integer userID, String bookedDate) {
		this.bookID = bookID;
		this.pc_ID = pc_ID;
		this.userID = userID;
		this.bookedDate = bookedDate;
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

	public String getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(String bookedDate) {
		this.bookedDate = bookedDate;
	}

	public static void deleteBookData(Integer bookID) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("DELETE FROM `PCBook` WHERE BookID = ?");
		
		ps.setInt(1, bookID);

		ps.executeUpdate();
	}
	
	public static PCBook getPCBookedData(Integer pc_ID, String date) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `PCBook` WHERE PC_ID = ? AND BookedDate = '?'");
		ps.setInt(1, pc_ID);
		ps.setDate(2, Date.valueOf(date));
		
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
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `PCBook` WHERE BookID = ?");
		ps.setInt(1, bookID);
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			Integer pc_ID;
			Integer userID;
			String bookedDate;
			
			pc_ID = rs.getInt(2);
			userID = rs.getInt(3);
			bookedDate = rs.getDate(4).toString();
			
			pcbook = new PCBook(bookID, pc_ID, userID, bookedDate);
		}
		
		return pcbook;
	}
	
	public static void addNewBook(Integer pc_ID, Integer userID, String bookedDate) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `PCBook` VALUES (?, ?, ?, ?)");
		ps.setInt(1, 0);
		ps.setInt(2, pc_ID);
		ps.setInt(3, userID);
		ps.setDate(4, Date.valueOf(bookedDate));
		
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
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `PCBook`");
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			Integer bookID;
			Integer pc_ID;
			Integer userID;
			String bookedDate;
			
			bookID = rs.getInt(1);
			pc_ID = rs.getInt(2);
			userID = rs.getInt(3);
			bookedDate = rs.getDate(4).toString();
			
			pcbook = new PCBook(bookID, pc_ID, userID, bookedDate);
			pcbooks.add(pcbook);
		}
		
		return pcbooks;
		
	}
	
	public static ArrayList<PCBook> getPCBookedByDate(String date) throws SQLException{
		Connect db = Connect.getConnection();
		ArrayList<PCBook> pcbooks = new ArrayList<PCBook>();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `PCBook` WHERE BookedDate = ?");
		ps.setDate(1, Date.valueOf(date));
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			Integer bookID;
			Integer pc_ID;
			Integer userID;
			
			bookID = rs.getInt(1);
			pc_ID = rs.getInt(2);
			userID = rs.getInt(3);
			
			pcbook = new PCBook(bookID, pc_ID, userID, date);
			pcbooks.add(pcbook);
		}
		
		return pcbooks;
		
	}
	
	public static void assignUsertoNewPC(Integer bookID, Integer newPCID) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("UPDATE `PCBook` SET PC_ID = ? WHERE BookID= ?");
	
		ps.setInt(1, newPCID);
		ps.setInt(2, bookID);
		
		ps.executeUpdate();
	}
	
	public static PCBook getPCBookedByID(Integer pcID) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `PCBook` WHERE pc_ID = ?");
		ps.setInt(1, pcID);
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			Integer bookID;
			Integer userID;
			String bookedDate;
			
			bookID = rs.getInt(1);
			userID = rs.getInt(3);
			bookedDate = rs.getDate(4).toString();
			
			pcbook = new PCBook(bookID, pcID, userID, bookedDate);
		}
		
		return pcbook;
	}
	
}
