package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class TransactionDetail {
	private int TransactionID;
	private int PC_ID;
	private String CustomerName;
	private String BookedTime;
	
	public TransactionDetail(int transactionID, int pC_ID, String customerName, String bookedTime) {
		super();
		TransactionID = transactionID;
		PC_ID = pC_ID;
		CustomerName = customerName;
		BookedTime = bookedTime;
	}
	public int getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
	}
	public int getPC_ID() {
		return PC_ID;
	}
	public void setPC_ID(int pC_ID) {
		PC_ID = pC_ID;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getBookedTime() {
		return BookedTime;
	}
	public void setBookedTime(String bookedTime) {
		BookedTime = bookedTime;
	}
	
	public static ArrayList<TransactionDetail> getAllTransactionDetail(int TransactionID) throws SQLException{
		Connect db = Connect.getConnection();
		ArrayList<TransactionDetail> tdetails = new ArrayList<TransactionDetail>();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `TransactionDetail` WHERE TransactionID = ?");
		ps.setInt(1, TransactionID);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int id;
			int PC_ID;
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
	
	public static ArrayList<TransactionDetail> getUserTransactionDetail(int UserID){
//		Connect db = Connect.getConnection();
//		ArrayList<TransactionDetail> tdetails = new ArrayList<TransactionDetail>();
//		
//		User.getAllUserData().g
	}
	
}
