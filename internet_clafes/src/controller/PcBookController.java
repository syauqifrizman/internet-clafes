package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PC;
import model.PCBook;
import view.ViewPC;

public class PcBookController {
	
	//untuk delete (cancel) book data
	public static boolean DeleteBookData(int bookID) throws SQLException {
		
		//ngambil data booking nya pake ID
		PCBook getPCBook = PCBook.getPCBookedDetail(bookID);	
		
		//ngambil date nya
		LocalDate bookedLocalDate = getPCBook.getBookedDate().toLocalDate();
		
		//kalau booking date sebelum sekarang, gabisa di delete/cancel
		if(bookedLocalDate.isBefore(LocalDate.now())) {
			String errorMSG = "Unable to cancel booking_id: " + getPCBook.getBookID().toString() + " because the date has passed";
			Helper.showAlert(AlertType.ERROR, errorMSG);
			return false;
		}
		
		//delete book data
		try {
			PCBook.deleteBookData(bookID);
			
			String successMSG = "Success cancel booking_id: " + bookID + ", booking by user_id: " + getPCBook.getUserID() + ", at the pc_id: " + getPCBook.getPc_ID();
			Helper.showAlert(AlertType.INFORMATION, successMSG);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Deleting record");
			return false;
		}
        ViewPC viewpc = ViewPC.getInstance();
		viewpc.show();
		return true;
	}
	
	//untuk ngambil book data berdasarkan PC ID dan tanggal
	public static PCBook getPCBookedData(int pcID, Date date) throws SQLException {
		return PCBook.getPCBookedData(pcID, date);
	}
	
	//untuk memindahkan User ke PC lain
	public static boolean assignUsertoNewPC(int bookID, Integer newPCID, Date date) throws SQLException {
		
		//validasi kalau PC ID nya kosong akan error
		if(newPCID == 0) {
			Helper.showAlert(AlertType.ERROR, "PC ID must be filled");
			return false;
		}
		
		//validasi kalau PC sudah dibooking hari itu/belum dengan memanggil method yang ngambil
		//PC Book Data berdasarkan PC ID dan Tanggal. Kalau ada yg di-return, berarti ada yg booking
		else if(PCBook.getPCBookedData(newPCID, date)!=null) {
			Helper.showAlert(AlertType.ERROR, "PC has been booked for that day");
			return false;
		}
		
		//validasi kondisi PC. Kalau kondisinya ga Usable, berarti tidak bisa di-booking
		PC getPC = PCController.getPCDetail(newPCID.toString());
		if(!getPC.getPc_condition().equals("Usable")) {
			String condition = "PC ID: " +  getPC.getPc_ID().toString() + ", condition is " + getPC.getPc_condition() + ", pc can't be booked right now";
			Helper.showAlert(AlertType.ERROR, condition);
			return false;
		}
		
		//pindah user ke PC lain
		try {
			PCBook.assignUsertoNewPC(bookID, newPCID);
			Helper.showAlert(AlertType.INFORMATION, "Success assign customer to new pc");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error assigning user");
			return false;
		}
		return true;
	}
	
	//ngambil PC Booked Data berdasarkan booking ID nya
	public PCBook getPCBookedDetail(int bookID) throws SQLException {
		return PCBook.getPCBookedDetail(bookID);
	}
	
	//method untuk buat booking baru
	public static boolean addNewBook(String pcID, Integer userID, Date bookedDate) throws SQLException {
		
		//validasi kondisi PC. Kalau kondisinya ga Usable, berarti tidak bisa di-booking
		PC getPC = PCController.getPCDetail(pcID);
		if(!getPC.getPc_condition().equals("Usable")) {
			String condition = "PC ID: " +  getPC.getPc_ID().toString() + ", condition is " + getPC.getPc_condition() + ", pc can't be booked right now. Please return to the home page (accessible in the menu bar)";
			Helper.showAlert(AlertType.ERROR, condition);
			return false;
		}
		
		//validasi kalau PC ID nya kosong, maka akan error
		else if(pcID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Please choose a PC");
			return false;
		}
		
		//validasi kalau tanggal nya kosong, maka akan error
		else if(bookedDate == null) {
			Helper.showAlert(AlertType.ERROR, "Please pick a date");
			return false;
		}
		
		//validasi kalau tanggal yang di-booking itu sebelum tanggal hari ini, maka tidak bisa di-booking
		LocalDate bookedLocalDate = bookedDate.toLocalDate();
		if(bookedLocalDate.isBefore(LocalDate.now())) {
			Helper.showAlert(AlertType.ERROR, "You can't book the PC before today");
			return false;
		}
		//validasi kalau PC sudah dibooking hari itu/belum dengan memanggil method yang ngambil
		//PC Book Data berdasarkan PC ID dan Tanggal. Kalau ada yg di-return, berarti ada yg booking
		else if(PCBook.getPCBookedData(Integer.parseInt(pcID), bookedDate)!=null) {
			Helper.showAlert(AlertType.ERROR, "PC has been booked for that day");
			return false;
		}
		
		//tambah booking baru
		PCBook.addNewBook(Integer.parseInt(pcID), userID, bookedDate);
		return true;
		
	}
	
	//method untuk menyelesaikan sebuah booking (user sudah selesai menggunakan PC yang sudah di booking)
	public static boolean finishBook(ArrayList<PCBook> pcbooks, Integer staffID) {
		
		//memanggil method finishBook di model
		try {
			PCBook.finishBook(pcbooks, staffID);
			Helper.showAlert(AlertType.INFORMATION, "Success finish book");
		} catch (SQLException e) {
			Helper.showAlert(AlertType.ERROR, "Error finish booking");
			return false;
		}

		return true;
	}
	
	//mengambil semua PC Booked Data dari database
	public static ArrayList<PCBook> getAllPCBookedData(){
		try {
			return PCBook.getAllPCBookedData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error fetching data");
			return new ArrayList<PCBook>();
		}
	}
	
	//mengambil semua PC Booked Data pada 1 tanggal tertentu
	public ArrayList<PCBook> getPcBookedByDate(Date date){
		try {
			return PCBook.getPCBookedByDate(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error fetching data");
			return new ArrayList<PCBook>();
		}
	}
	
	//mengambil PC Booked Data berdasarkan PC ID nya
	public static PCBook getPCBookedByID(int pcID) throws SQLException {
		return PCBook.getPCBookedByID(pcID);
	}
	
	
	
}
