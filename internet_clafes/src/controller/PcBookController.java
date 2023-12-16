package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PC;
import model.PCBook;
import view.ViewPC;

public class PcBookController {
	public static boolean DeleteBookData(int bookID) throws SQLException {
		PCBook getPCBook = PCBook.getPCBookedDetail(bookID);	
		
		LocalDate bookedLocalDate = getPCBook.getBookedDate().toLocalDate();
		if(bookedLocalDate.isBefore(LocalDate.now())) {
			String errorMSG = "Unable to cancel booking_id: " + getPCBook.getBookID().toString() + " because the date has passed";
			Helper.showAlert(AlertType.ERROR, errorMSG);
			return false;
		}
		
		
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
	
	public static PCBook getPCBookedData(int pcID, Date date) throws SQLException {
		return PCBook.getPCBookedData(pcID, date);
	}
	
	public static boolean assignUsertoNewPC(int bookID, Integer newPCID, Date date) throws SQLException {
		if(newPCID == 0) {
			Helper.showAlert(AlertType.ERROR, "PC ID must be filled");
			return false;
		}
		
		if(PCBook.getPCBookedData(newPCID, date)!=null) {
			Helper.showAlert(AlertType.ERROR, "PC has been booked for that day");
			return false;
		}
		
		PC getPC = PCController.getPCDetail(newPCID.toString());
		if(!getPC.getPc_condition().equals("Usable")) {
			String condition = "PC ID: " +  getPC.getPc_ID().toString() + ", condition is " + getPC.getPc_condition() + ", pc can't be booked right now";
			Helper.showAlert(AlertType.ERROR, condition);
			return false;
		}
		
		try {
			PCBook.assignUsertoNewPC(bookID, newPCID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error assigning user");
			return false;
		}
		return true;
	}
	
	public PCBook getPCBookedDetail(int bookID) throws SQLException {
		return PCBook.getPCBookedDetail(bookID);
	}
	
	public static boolean addNewBook(String pcID, Integer userID, Date bookedDate) throws SQLException {
		PC getPC = PCController.getPCDetail(pcID);
		if(!getPC.getPc_condition().equals("Usable")) {
			String condition = "PC ID: " +  getPC.getPc_ID().toString() + ", condition is " + getPC.getPc_condition() + ", pc can't be booked right now. Please return to the home page (accessible in the menu bar)";
			Helper.showAlert(AlertType.ERROR, condition);
			return false;
		}
		else if(pcID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Please choose a PC");
			return false;
		}
		else if(bookedDate == null) {
			Helper.showAlert(AlertType.ERROR, "Please pick a date");
			return false;
		}
		
		LocalDate bookedLocalDate = bookedDate.toLocalDate();
		if(bookedLocalDate.isBefore(LocalDate.now())) {
			Helper.showAlert(AlertType.ERROR, "You can't book the PC before today");
			return false;
		}
		else if(PCBook.getPCBookedData(Integer.parseInt(pcID), bookedDate)!=null) {
			Helper.showAlert(AlertType.ERROR, "PC has been booked for that day");
			return false;
		}
		
		PCBook.addNewBook(Integer.parseInt(pcID), userID, bookedDate);
		return true;
		
	}
	
	public static boolean finishBook(ArrayList<PCBook> pcbooks, Integer staffID) {
		try {
			PCBook.finishBook(pcbooks, staffID);
		} catch (SQLException e) {
			Helper.showAlert(AlertType.ERROR, "Error finish booking");
			return false;
		}
        ViewPC viewpc = ViewPC.getInstance();
		viewpc.show();
		return true;
	}
	
	public static ArrayList<PCBook> getAllPCBookedData(){
		try {
			return PCBook.getAllPCBookedData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error fetching data");
			return new ArrayList<PCBook>();
		}
	}
	
	public ArrayList<PCBook> getPcBookedByDate(Date date){
		try {
			return PCBook.getPCBookedByDate(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error fetching data");
			return new ArrayList<PCBook>();
		}
	}
	
	public static PCBook getPCBookedByID(int pcID) throws SQLException {
		return PCBook.getPCBookedByID(pcID);
	}
	
	
	
}
