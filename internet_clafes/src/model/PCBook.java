package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class PCBook {

	private int BookID;
	private int PC_ID;
	private int UserID;
	private String BookedDate;
	
	public PCBook(int bookID, int pC_ID, int userID, String bookedDate) {
		super();
		BookID = bookID;
		PC_ID = pC_ID;
		UserID = userID;
		BookedDate = bookedDate;
	}

	public int getBookID() {
		return BookID;
	}

	public void setBookID(int bookID) {
		BookID = bookID;
	}

	public int getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(int pC_ID) {
		PC_ID = pC_ID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getBookedDate() {
		return BookedDate;
	}

	public void setBookedDate(String bookedDate) {
		BookedDate = bookedDate;
	}
	
	public static void deleteBookData(int BookID) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("DELETE FROM `PCBook` WHERE BookID = ?");
		
		ps.setInt(1, BookID);

		ps.executeUpdate();
	}
	
	public static PCBook getPCBookedData(int PcID, String date) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `PCBook` WHERE PC_ID = ?, BookedDate = '?'");
		ps.setInt(1, PcID);
		ps.setString(2, date);
		
		ResultSet rs = ps.executeQuery();
		
		
		PCBook pcbook = null;
		while(rs.next()) {
			int bookID;
			int userID;
			
			bookID = rs.getInt(1);
			userID = rs.getInt(3);
			
			pcbook = new PCBook(bookID, PcID, userID, date);
		}
		
		return pcbook;
	}
	
	public static PCBook getPCBookedDetail(int BookID) throws SQLException{
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `PCBook` WHERE BookID = ?");
		ps.setInt(1, BookID);
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			int PC_ID;
			int userID;
			String bookedDate;
			
			PC_ID = rs.getInt(2);
			userID = rs.getInt(3);
			bookedDate = rs.getString(4);
			
			pcbook = new PCBook(BookID, PC_ID, userID, bookedDate);
		}
		
		return pcbook;
	}
	
	public static void addNewBook(int PcID, int UserID, String bookedDate) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `PCBook` VALUES (?, ?, ?, '?')");
		ps.setInt(1, 0);
		ps.setInt(2, PcID);
		ps.setInt(3, UserID);
		ps.setString(4, bookedDate);
		
		ps.executeUpdate();
	}
	
	public static void finishBook(ArrayList<PCBook> pcbooks) {
		//jujur gw g paham ini disuruh ngapain oakowkaokowkoak
	}
	
	public static ArrayList<PCBook> getAllPCBookedData() throws SQLException{
		Connect db = Connect.getConnection();
		ArrayList<PCBook> pcbooks = new ArrayList<PCBook>();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `PCBook`");
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			int BookID;
			int PC_ID;
			int userID;
			String bookedDate;
			
			BookID = rs.getInt(1);
			PC_ID = rs.getInt(2);
			userID = rs.getInt(3);
			bookedDate = rs.getString(4);
			
			pcbook = new PCBook(BookID, PC_ID, userID, bookedDate);
			pcbooks.add(pcbook);
		}
		
		return pcbooks;
		
	}
	
	public static ArrayList<PCBook> getPCBookedByDate(String date) throws SQLException{
		Connect db = Connect.getConnection();
		ArrayList<PCBook> pcbooks = new ArrayList<PCBook>();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `PCBook` WHERE BookedDate = '?'");
		ps.setString(1, date);
		
		ResultSet rs = ps.executeQuery();
		
		PCBook pcbook = null;
		while(rs.next()) {
			int BookID;
			int PC_ID;
			int userID;
			
			BookID = rs.getInt(1);
			PC_ID = rs.getInt(2);
			userID = rs.getInt(3);
			
			pcbook = new PCBook(BookID, PC_ID, userID, date);
			pcbooks.add(pcbook);
		}
		
		return pcbooks;
		
	}
	
	public static void assignUsertoNewPC(int BookID, int NewPCID) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("UPDATE `PCBook` SET PC_ID = ? WHERE BookID= ?");
	
		ps.setInt(1, NewPCID);
		ps.setInt(2, BookID);
		
		ps.executeUpdate();
	}
	
}
