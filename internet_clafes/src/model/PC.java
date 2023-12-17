package model;

import java.util.ArrayList;

import factory.PCFactory;
import repository.PCRepository;

public class PC {
	
	//mendeklarasikan atribut yang dibutuhkan oleh PC
	//id unik untuk setiap pc
	private Integer pc_ID;
	//status dari pc “Usable” dapat digunakan/ “Maintenance” dalam maintenance/ “Broken” sedang rusak.
	private String pc_condition;
	
	//membuat kontruksi kelas PC sesuai atributnya
	public PC(Integer pc_ID, String pc_condition) {
		this.pc_ID = pc_ID;
		this.pc_condition = pc_condition;
	}

	public PC() {
		// TODO Auto-generated constructor stub
	}

	//karena atribut dibuat private maka tidak dapat diakses secara langsung oleh kelas luar
	//maka untuk diakses kelas luar, dibutuhkan public getter(untuk mendapatkan atribut)
	//dan setter untuk menaruh value ke atribut) untuk setiap atribut private
	public Integer getPc_ID() {
		return pc_ID;
	}

	public void setPc_ID(Integer pc_ID) {
		this.pc_ID = pc_ID;
	}

	public String getPc_condition() {
		return pc_condition;
	}

	public void setPc_condition(String pc_condition) {
		this.pc_condition = pc_condition;
	}
	
	
	//mengambil detail pc berdasarkan idnya 
	//mengembalikan data pc dengan PCRepository
	public static PC getPCDetail(String pc_ID) {
		return PCRepository.getPCDetail(pc_ID);
	}
	
	//membuat pc baru dengan id tertentu melalui PCFactory 
	//mengembalikan pesan dengan PCRepository
	public static String insertNewPC(String pc_ID) {
		PC getPC = PCFactory.createPC(pc_ID);
		return PCRepository.insertNewPC(getPC);
	}
	//menghapus pc dengan id tertentu dan mengembalikan pesan dengan PCRepository
	public static String deletePC(String pc_ID) {
		return PCRepository.deleteNewPC(pc_ID);
	}
	
	//mengupdate status pc dengan id tertentu dan status barunya
	//mengembalikan pesan dengan PCRepository
	public static String updatePCCondition(String pc_id, String pc_condition) {
		return PCRepository.updatePCCondition(pc_id, pc_condition);
	}
	
	//mengambil seluruh detail pc yang ada 
	//mengembalikan data array list pc dengan PCRepository
	public static ArrayList<PC> getAllPCData(){
		return PCRepository.getAllPCData();
	}
	
}
