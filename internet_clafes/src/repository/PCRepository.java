package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.PC;
import sqlConnect.Connect;

public class PCRepository {
	
	//method untuk mendapatkan seluruh data pc yang ada dari database
	//akan mengembalikan seluruh data pc
	public static ArrayList<PC> getAllPCData(){
		ArrayList<PC> pcList = new ArrayList<PC>();//membuat arraylist kosong untuk menampung data
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String queryExecute = "SELECT * FROM pcs";
		ResultSet res = db.executeQuery(queryExecute);//menjalankan query dan mengambil hasilnya
		
//		pcList = null;
		try {
			while(res.next()) {//hasil yang sudah diambil dimasukkan ke variabel sementara
				Integer currPC_ID = res.getInt(1);
				String currPC_condition = res.getString(2);
				
				//dari variabel sementara, dibuat sebuah object pc baru
				PC currPC = new PC(currPC_ID, currPC_condition);
				pcList.add(currPC);//object tersebut dimasukkan ke arraylist yang sudah dibuat
			}
		} catch (SQLException e) {
			//Menangkap dan menangani exception jika terjadi kesalahan SQL
			e.printStackTrace();
		}
		
		return pcList;//araylist yang sudah dibuat akan di-return
		
	}
	
	//method untuk mengupdate status pc dengan id pc dan status baunya dalam databse
	public static String updatePCCondition(String pc_ID, String pc_condition) {
		Integer curr_pc_ID = Integer.parseInt(pc_ID);
		
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String query = "UPDATE pcs SET pc_condition = '%s' WHERE pc_ID = '%d'";
		//memasukan/mengisi %s dengan pc_condition yang diterima
		//memasukan/mengisi %d dengan curr_pc_ID yang dibuat sebelumnya
		String queryExecute = String.format(query, pc_condition, curr_pc_ID);
		
		//menjalankan query
		db.executeUpdate(queryExecute);
		
		return "Success updated the PC condition!";//mengembalikan "Success updated the PC condition!"
	}
	
	//method untuk menghapus data pc berdasarkan id pcnya dari database
	public static String deleteNewPC(String pc_ID) {
		//mngubah tipe data string menjadi int dan memasukan pc_ID ke curr_pc_ID
		Integer curr_pc_ID = Integer.parseInt(pc_ID);
		
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String query = "DELETE FROM pcs WHERE pc_ID = '%d'";
		//memasukan/mengisi %d dengan curr_pc_ID yang dibuat sebelumnya
		String queryExecute = String.format(query, curr_pc_ID);
		
		//menjalankan query
		db.executeUpdate(queryExecute);
		
		return "Success remove the PC ID: " + pc_ID;//akan mengembalikan "Success remove the PC ID: " dan id pc yang telah dihapus tersebut
	}
	
	//method untuk membuat pc baru berdasarkan data yang diinput ke method kedalam database

	public static String insertNewPC(PC newPC) {
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String query = "INSERT INTO pcs VALUES ('%d', '%s')";
		//memasukan/mengisi %d dengan mengambil id dari newPC yang diterima
		//memasukan/mengisi %s dengan mengambil Pc_condition dari newPC yang diterima
		String queryExecute = String.format(query, newPC.getPc_ID(), newPC.getPc_condition());
		
		//menjalankan query
		db.executeUpdate(queryExecute);
		
		return "Success add new PC!";	//akan mengembalikan "Success add new PC!" 
	}
	
	//method untuk mendapatkan detail/data dari pc berdasarkan id pcnya dari database
	//akan mengembalikan 1 data pc dengan id pc yang diinginkan
	public static PC getPCDetail(String pc_ID) {
		//mngubah tipe data string menjadi int dan memasukan pc_ID ke curr_pc_ID
		Integer curr_pc_ID = Integer.parseInt(pc_ID);
		
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String query = "SELECT * FROM pcs WHERE pc_ID = '%d'";
		//memasukan/mengisi %d dengan curr_pc_ID yang dibuat sebelumnya
		String queryExecute = String.format(query, curr_pc_ID);
		
		ResultSet res = db.executeQuery(queryExecute);//menjalankan query dan mengambil hasilnya
		
		PC getPC = null;//membuat getPC dengan tipe data User
		try {
			while(res.next()) {//hasil yang sudah diambil dimasukkan ke variabel sementara
				Integer currPC_ID = res.getInt(1);
				String currPC_condition = res.getString(2);
				
				//dari variabel sementara, dibuat sebuah object PC baru yang dimasukan kedalam getPC
				getPC = new PC(currPC_ID, currPC_condition);
			}
		} catch (SQLException e) {
			//Menangkap dan menangani exception jika terjadi kesalahan SQL
			e.printStackTrace();
		}
		
		return getPC;//mengambalikan getPC
	}
}
