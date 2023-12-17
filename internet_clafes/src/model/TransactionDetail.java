package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sqlConnect.Connect;

public class TransactionDetail {
	
	//mendeklarasikan atribut yang dibutuhkan oleh TransactionDetail
	//id untuk setiap TransactionDetail
	private Integer transactionID;
	//id untuk pc
	private Integer pc_ID;
	//nama customer 
	private String customerName;
	//tanggal book
	private String bookedTime;
	
	//membuat kontruksi kelas TransactionDetail sesuai atributnya
	public TransactionDetail(Integer transactionID, Integer pc_ID, String customerName, String bookedTime) {
		this.transactionID = transactionID;
		this.pc_ID = pc_ID;
		this.customerName = customerName;
		this.bookedTime = bookedTime;
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

	//method untuk mengambil data seluruh transaksi detail di transactionID tertentu dari database
	public static ArrayList<TransactionDetail> getAllTransactionDetail(Integer transactionID) throws SQLException{
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		//membuat arraylist kosong untuk menampung data
		ArrayList<TransactionDetail> tdetails = new ArrayList<TransactionDetail>();
		//membuat query
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `transactiondetails` WHERE transactionID = ?");
		//mengisi tanda ? pada query dengan data yang sudah diterima
		ps.setInt(1, transactionID);
		//menjalankan query
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Integer id;
			Integer PC_ID;
			String name;
			String time;			
			
			//hasil yang sudah diambil dimasukkan ke variabel sementara
			id = rs.getInt(1);
			PC_ID = rs.getInt(2);
			name = rs.getString(3);
			time = rs.getString(4);
			//dari variabel sementara, dibuat sebuah object tdetail baru
			TransactionDetail tdetail = new TransactionDetail(id, PC_ID, name, time);
			tdetails.add(tdetail);//object tersebut dimasukkan ke arraylist yang sudah dibuat
		}
		
		return tdetails;//araylist yang sudah dibuat akan di-return
		
	}
	
	//method untuk mengambil data transaksi detail di userID tertentu dari database
	public static ArrayList<TransactionDetail> getUserTransactionDetail(Integer userID) throws SQLException{
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		//membuat arraylist kosong untuk menampung data
		ArrayList<TransactionDetail> tdetails = new ArrayList<TransactionDetail>();
		
		//mengambil username dari user id yang diterima
		String username = User.getUserDetail(userID).getUserName();
		//membuat query
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `transactiondetails` WHERE customerName = ?");
		//mengisi tanda ? pada query dengan username yang sudah dicari dan diambil
		ps.setString(1, username);
		//menjalankan query
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int tID;
			int pcID;
			String bookedTime;
			
			//hasil yang sudah diambil dimasukkan ke variabel sementara
			tID = rs.getInt(1);
			pcID = rs.getInt(2);
			bookedTime = rs.getDate(4).toString();
			
			//dari variabel sementara, dibuat sebuah object tdetail baru
			TransactionDetail tdetail = new TransactionDetail(tID, pcID, username, bookedTime);
			tdetails.add(tdetail);//object tersebut dimasukkan ke arraylist yang sudah dibuat
		}
		return tdetails;//araylist yang sudah dibuat akan di-return
		
	}
	
	//method untuk menambahkan data transaksi detail di transactionID tertentu ke database
	public static void addTransactionDetail(Integer transactionID, ArrayList<PCBook> pcbooks) throws SQLException {
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		for (PCBook pcBook : pcbooks) {
			//membuat query
			PreparedStatement ps = db.prepareStatement("INSERT INTO `transactiondetails` VALUES(?, ?, ?, ?)");
			//mengisi tanda ? pada query dengan data yang sudah diterima
			ps.setInt(1, transactionID);
			ps.setInt(2, pcBook.getPc_ID());
			ps.setString(3, User.getUserDetail(pcBook.getUserID()).getUserName());
			ps.setDate(4, pcBook.getBookedDate());
			//menjalankan query
			ps.executeUpdate();
		}
	}
	
}
