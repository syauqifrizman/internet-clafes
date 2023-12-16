package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;

public class TransactionController {
	
	
	//method untuk mengambil semua transaction header data dari database
	public static ArrayList<TransactionHeader> getAllTransactionHeaderData(){
		try {
			return TransactionHeader.getAllTransactionHeaderData();
		} catch (SQLException e) {
			return new ArrayList<TransactionHeader>();
		}
	}
	
	//method untuk mengambil semua transaction detail data dari database
	public static ArrayList<TransactionDetail> getAllTransactionDetailData(Integer transactionID){
		try {
			return TransactionDetail.getAllTransactionDetail(transactionID);
		} catch (SQLException e) {
			return new ArrayList<TransactionDetail>();
		}
	}
	
	//method untuk mengambil semua transaction detail data sebuah user dari database
	public static ArrayList<TransactionDetail> getUserTransactionDetail(Integer UserID){
		try {
			return TransactionDetail.getUserTransactionDetail(UserID);
		} catch (SQLException e) {
			return new ArrayList<TransactionDetail>();
		}
	}
	
	
	//method untuk membuat transaction baru
	public static boolean AddTransaction(ArrayList<PCBook> pcbooks, Integer StaffID) {
		
		//buat dulu headernya dengan memanggil pembuatan Transaction Header baru
		try {
			TransactionHeader.addNewTransactionHeader(StaffID, java.time.LocalDate.now().toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch blo
			Helper.showAlert(AlertType.ERROR, "Error in Transaction Header");
			return false;
		}
		
		//lalu lihat ID dari transaction header yang baru dibuat, dan ambil ID itu untuk
		//membuat Transaction Detail baru
		try {
			TransactionDetail.addTransactionDetail(peekLastID(), pcbooks);
		} catch (SQLException e) {
			Helper.showAlert(AlertType.ERROR, "Error in Transaction Detail");
			return false;
		}
		
		return true;
	}
	
	//method untuk melihat Transaction ID dari transaction terakhir
	public static Integer peekLastID() {
		try {
			return TransactionHeader.peekLastID();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
}
