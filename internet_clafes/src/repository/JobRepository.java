package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Job;
import model.PC;
import sqlConnect.Connect;

public class JobRepository {
	
	//method untuk menambahkan data job baru ke database 
	public static String addNewJob(Job newJob) {
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String query = "INSERT INTO `jobs` VALUES ('%d', '%d', '%s', '%s')";
		//mengisi tanda %d dan %s pada query dengan mengambil data newJob yang sudah diterima
		String queryExecute = String.format(query, newJob.getJob_ID(), newJob.getUserID(), newJob.getPc_ID(), newJob.getJobStatus());
		
		//menjalankan query
		db.executeUpdate(queryExecute);
		
		return "Success add new job!";// mengembalikan "Success add new job!"
	}
	
	//method untuk mengganti status job dengan id job dan status arunya ke database
	public static String updateJobStatus(String job_ID, String jobStatus) {
		//mngubah tipe data string menjadi int dan memasukan job_ID ke newJob_ID
		Integer newJob_ID = Integer.parseInt(job_ID);
		
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String query = "UPDATE `jobs` SET jobStatus = '%s' WHERE job_ID = %d";
		//memasukan/mengisi %s dengan jobStatus yang diterima
		//memasukan/mengisi %d dengan newJob_ID yang telah dibuat sebelumnya
		String queryExecute = String.format(query, jobStatus, newJob_ID);
		
		//menjalankan query
		db.executeUpdate(queryExecute);
		
		return "Success updated the job!";//mengembalikan "Success updated the job!"
	}
	
	//method untuk mengambil data dari seluruh job yang ada untuk user id tertentu dari database
	//akan mengembalikan seluruh job yang ada dengan user id yang diinginkan
	public static ArrayList<Job> getTechnicianJob(String userID) {
		//mngubah tipe data string menjadi int dan memasukan userID ke newUser_ID
		Integer newUser_ID = Integer.parseInt(userID);
		
		ArrayList<Job> jobList = new ArrayList<Job>();//membuat arraylist kosong untuk menampung data
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String query = "SELECT * FROM `jobs` WHERE user_ID = '%d'";
		
		//memasukan/mengisi %d dengan newUser_ID yang telah dibuat sebelumnya
		String queryExecute = String.format(query, newUser_ID);
		
		ResultSet res = db.executeQuery(queryExecute);//menjalankan query dan mengambil hasilnya
		
		if(res == null) {
			return jobList = null;
		}
		
		try {
			while(res.next()) {//hasil yang sudah diambil dimasukkan ke variabel sementara
				Integer currJob_ID = res.getInt(1);
				Integer currUserID = res.getInt(2);
				Integer currPC_ID = res.getInt(3);
				String currJobStatus = res.getString(4);
				
				//dari variabel sementara, dibuat sebuah object Job baru
				Job getJob = new Job(currJob_ID, currUserID, currPC_ID, currJobStatus);
				jobList.add(getJob);//object tersebut dimasukkan ke arraylist yang sudah dibuat
			}
		} catch (SQLException e) {
			//Menangkap dan menangani exception jika terjadi kesalahan SQL
			e.printStackTrace();
		}
		
		return jobList;// mengembalikan array jobList yang sudah diuat
		
	}
	
	//method untuk mendapatkan detail/data dari job dengan job idnya dari database
	//akan mengembalikan 1 data job dengan id job tersebut
	public static Job getJobDetail(String job_ID) {
		//mngubah tipe data string menjadi int dan memasukan job_ID ke newJob_ID
		Integer newJob_ID = Integer.parseInt(job_ID);
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String query = "SELECT * FROM `jobs` WHERE job_ID = '%d'";
		//memasukan/mengisi %d dengan newJob_ID yang telah dibuat sebelumnya
		String queryExecute = String.format(query, newJob_ID);
		
		ResultSet res = db.executeQuery(queryExecute);//menjalankan query dan mengambil hasilnya
		
		Job getJob = null;//membuat variable getJob dengan tipe data User
		try {
			while(res.next()) {//hasil yang sudah diambil dimasukkan ke variabel sementara
				Integer currJob_ID = res.getInt(1);
				Integer currUserID = res.getInt(2);
				Integer currPC_ID = res.getInt(3);
				String currJobStatus = res.getString(4);
				
				//dari variabel sementara, dibuat sebuah object Job baru yang dimasukan kedalam getJob
				getJob = new Job(currJob_ID, currUserID, currPC_ID, currJobStatus);
			}
		} catch (SQLException e) {
			//Menangkap dan menangani exception jika terjadi kesalahan SQL
			e.printStackTrace();
		}
		
		return getJob;//mengambalikan getJob
	}
	
	//method untuk mengambil data dari seluruh job yang ada  dari database 
	//akan mengembalikan seluruh data job
	public static ArrayList<Job> getAllJobData(){
		ArrayList<Job> jobList = new ArrayList<Job>();//membuat arraylist kosong untuk menampung data
		Connect db = Connect.getConnection();//untuk mendapatkan koneksi ke database
		
		//membuat query
		String queryExecute = "SELECT * FROM `jobs`";
		
		ResultSet res = db.executeQuery(queryExecute);//menjalankan query dan mengambil hasilnya
		
		if(res == null) {
			return jobList = null;
		}
		
//		jobList = null;
		try {
			while(res.next()) {//hasil yang sudah diambil dimasukkan ke variabel sementara
				Integer currJob_ID = res.getInt(1);
				Integer currUserID = res.getInt(2);
				Integer currPC_ID = res.getInt(3);
				String currJobStatus = res.getString(4);
				
				//dari variabel sementara, dibuat sebuah Job baru
				Job getJob = new Job(currJob_ID, currUserID, currPC_ID, currJobStatus);
				jobList.add(getJob);//object tersebut dimasukkan ke arraylist kosong yang sudah dibuat
			}
		} catch (SQLException e) {
			//Menangkap dan menangani exception jika terjadi kesalahan SQL
			e.printStackTrace();
		}
		
		return jobList; //araylist yang sudah dibuat akan di-return
	}
	
	
}
