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

public class PcBookController {
	public static boolean DeleteBookData(int BookID) {
		try {
			PCBook.deleteBookData(BookID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Deleting record");
			return false;
		}
		return true;
	}
	
	public static PCBook getPCBookedData(int PcID, Date date) throws SQLException {
		return PCBook.getPCBookedData(PcID, date);
	}
	
	public static boolean assignUsertoNewPC(int BookID, Integer NewPCID, Date date) throws SQLException {
		if(NewPCID == 0) {
			Helper.showAlert(AlertType.ERROR, "PC ID must be filled");
			return false;
		}
		
		if(PCBook.getPCBookedData(NewPCID, date)!=null) {
			Helper.showAlert(AlertType.ERROR, "PC has been booked for that day");
			return false;
		}
		
		PC getPC = PCController.getPCDetail(NewPCID.toString());
		if(!getPC.getPc_condition().equals("Usable")) {
			String condition = "PC ID: " +  getPC.getPc_ID().toString() + ", condition is " + getPC.getPc_condition() + ", pc can't be booked right now";
			Helper.showAlert(AlertType.ERROR, condition);
			return false;
		}
		
		try {
			PCBook.assignUsertoNewPC(BookID, NewPCID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error assigning user");
			return false;
		}
		return true;
	}
	
	public PCBook getPCBookedDetail(int BookID) throws SQLException {
		return PCBook.getPCBookedDetail(BookID);
	}
	
	public static boolean addNewBook(String PcID, Integer UserID, Date bookedDate) throws SQLException {
		PC getPC = PCController.getPCDetail(PcID);
		if(!getPC.getPc_condition().equals("Usable")) {
			String condition = "PC ID: " +  getPC.getPc_ID().toString() + ", condition is " + getPC.getPc_condition() + ", pc can't be booked right now. Please return to the home page (accessible in the menu bar)";
			Helper.showAlert(AlertType.ERROR, condition);
			return false;
		}
		else if(PcID.isEmpty()) {
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
		else if(PCBook.getPCBookedData(Integer.parseInt(PcID), bookedDate)!=null) {
			Helper.showAlert(AlertType.ERROR, "PC has been booked for that day");
			return false;
		}
		
		PCBook.addNewBook(Integer.parseInt(PcID), UserID, bookedDate);
		return true;
		
	}
	
	public static boolean finishBook(ArrayList<PCBook> pcbooks, Integer StaffID) {
		try {
			PCBook.finishBook(pcbooks, StaffID);
		} catch (SQLException e) {
			Helper.showAlert(AlertType.ERROR, "Error finish booking");
			return false;
		}
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
