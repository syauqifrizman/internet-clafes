package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import repository.UserRepository;
import sqlConnect.Connect;

public class TransactionHeader {
	
	//mendeklarasikan atribut yang dibutuhkan oleh TransactionHeader
	//id unik untuk setiap TransactionHeader
	private Integer transactionID;
	//id staff(operator) yang menyelesaikan book customer
	private Integer staffID;
	//nama staff(operator) yang menyelesaikan book customer
	private String staffName;
	//tanggal transaksi
	private String transactionDate;
	
	//membuat kontruksi kelas TransactionHeader sesuai atributnya
	public TransactionHeader(Integer transactionID, Integer staffID, String staffName, String transactionDate) {
		this.transactionID = transactionID;
		this.staffID = staffID;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}

	//karena atribut dibuat private maka tidak dapat diakses secara langsung oleh kelas luar
	//maka untuk diakses kelas luar, dibutuhkan public getter(untuk mendapatkan atribut)
	//dan setter untuk menaruh value ke atribut) untuk setiap atribut private
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
	
	//method untuk mengambil data dari seluruh TransactionHeader dari database
	public static ArrayList<TransactionHeader> getAllTransactionHeaderData() throws SQLException{
		ArrayList<TransactionHeader> theaders = new ArrayList<TransactionHeader>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `transactionheader`");
		
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
	
	//method untuk menambahkan TransactionHeader baru ke database
	public static void addNewTransactionHeader(Integer staffID, String transactionDate) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `transactionheader` VALUES (?, ?, ?, ?)");
		ps.setInt(1, 0);
		ps.setInt(2, staffID);
		ps.setString(3, UserRepository.getUserDetail(staffID).getUserName());
		ps.setDate(4, Date.valueOf(transactionDate));
		
		ps.executeUpdate();
		
	}
	
	//method untuk mengambil id TransactionHeader terakhir dari database
	public static Integer peekLastID() throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `transactionheader` ORDER BY transactionID DESC LIMIT 1");
		ResultSet rs = ps.executeQuery();
		
		Integer id = 0;
		
		 try {
			if(rs.next()) {
			     id = id+rs.getInt("transactionID");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
}
