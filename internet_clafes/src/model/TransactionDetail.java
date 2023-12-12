package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sqlConnect.Connect;

public class TransactionDetail {
	private Integer transactionID;
	private Integer pc_ID;
	private String customerName;
	private String bookedTime;
	
	public TransactionDetail(Integer transactionID, Integer pc_ID, String customerName, String bookedTime) {
		this.transactionID = transactionID;
		this.pc_ID = pc_ID;
		this.customerName = customerName;
		this.bookedTime = bookedTime;
	}

	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public Integer getPc_ID() {
		return pc_ID;
	}

	public void setPc_ID(Integer pc_ID) {
		this.pc_ID = pc_ID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(String bookedTime) {
		this.bookedTime = bookedTime;
	}

	public static ArrayList<TransactionDetail> getAllTransactionDetail(Integer transactionID) throws SQLException{
		Connect db = Connect.getConnection();
		ArrayList<TransactionDetail> tdetails = new ArrayList<TransactionDetail>();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `TransactionDetail` WHERE TransactionID = ?");
		ps.setInt(1, transactionID);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Integer id;
			Integer PC_ID;
			String name;
			String time;			
			
			id = rs.getInt(1);
			PC_ID = rs.getInt(2);
			name = rs.getString(3);
			time = rs.getString(4);
			
			TransactionDetail tdetail = new TransactionDetail(id, PC_ID, name, time);
			tdetails.add(tdetail);
		}
		
		return tdetails;
		
	}
	
	public static ArrayList<TransactionDetail> getUserTransactionDetail(Integer userID){
		return null;
//		Connect db = Connect.getConnection();
//		ArrayList<TransactionDetail> tdetails = new ArrayList<TransactionDetail>();
//		
//		User.getAllUserData().g
	}
	
}
