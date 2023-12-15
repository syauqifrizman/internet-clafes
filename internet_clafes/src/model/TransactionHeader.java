package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import repository.UserRepository;
import sqlConnect.Connect;

public class TransactionHeader {
	private Integer transactionID;
	private Integer staffID;
	private String staffName;
	private String transactionDate;
	
	public TransactionHeader(Integer transactionID, Integer staffID, String staffName, String transactionDate) {
		this.transactionID = transactionID;
		this.staffID = staffID;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}

	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public Integer getStaffID() {
		return staffID;
	}

	public void setStaffID(Integer staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public static ArrayList<TransactionHeader> getAllTransactionHeaderData() throws SQLException{
		ArrayList<TransactionHeader> theaders = new ArrayList<TransactionHeader>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `transactionHeader`");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int tID;
			int sID;
			String sName;
			String tDate;
			
			tID = rs.getInt(1);
			sID = rs.getInt(2);
			sName = rs.getString(3);
			tDate = rs.getDate(4).toString();
			
			TransactionHeader theader = new TransactionHeader(tID, sID, sName, tDate);
			theaders.add(theader);
		}
		
		return theaders;
	}
	
	public static void addNewTransactionHeader(Integer staffID, String transactionDate) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `transactionHeader` VALUES (?, ?, ?, ?)");
		ps.setInt(1, 0);
		ps.setInt(2, staffID);
		ps.setString(3, UserRepository.getUserDetail(staffID).getUserName());
		ps.setDate(4, Date.valueOf(transactionDate));
		
		ps.executeUpdate();
		
	}
	
	public static Integer peekLastID() throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM users ORDER BY UserID DESC LIMIT 1");
		ResultSet rs = ps.executeQuery();
		
		Integer id = rs.getInt(1);
		
		return id;
	}
	
}
