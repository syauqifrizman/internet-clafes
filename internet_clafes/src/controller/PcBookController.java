package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import model.PCBook;
import view.IView;

public class PcBookController {
	public boolean DeleteBookData(int BookID, IView view) {
		try {
			PCBook.deleteBookData(BookID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error Deleting record");
			return false;
		}
		return true;
	}
	
	public PCBook getPCBookedData(int PcID, String date) throws SQLException {
		return PCBook.getPCBookedData(PcID, date);
	}
	
	public boolean assignUsertoNewPC(int BookID, Integer NewPCID, String date, IView view) throws SQLException {
		if(NewPCID == 0) {
			view.showError("PC ID must be filled");
			return false;
		}
		
		if(PCBook.getPCBookedData(NewPCID, date)!=null) {
			view.showError("PC has been booked for that day");
			return false;
		}
		
		PCController pcCotroller = new PCController();
		if(!pcCotroller.getPCDetail(NewPCID.toString()).getPc_condition().equals("Usable")) {
			view.showError("PC is unusable");
			return false;
		}
		
		try {
			PCBook.assignUsertoNewPC(BookID, NewPCID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error assigning user");
			return false;
		}
		return true;
	}
	
	public PCBook getPCBookedDetail(int BookID) throws SQLException {
		return PCBook.getPCBookedDetail(BookID);
	}
	
	public static boolean addNewBook(String PcID, Integer UserID, Date bookedDate, IView view) throws SQLException {
		if(PcID.isEmpty()) {
			view.showError("Please choose a PC");
			return false;
		}
		
		if(bookedDate == null) {
			view.showError("Please pick a date");
			return false;
		}
		if(!PCController.getPCDetail(PcID).getPc_condition().equals("Usable")) {
			view.showError("PC is unusable");
			return false;
		}
		
		if(PCBook.getPCBookedData(Integer.parseInt(PcID), bookedDate)!=null) {
			view.showError("PC has been booked for that day");
			return false;
		}
		
		PCBook.addNewBook(Integer.parseInt(PcID), UserID, bookedDate);
		return true;
		
	}
	
	public boolean finishBook(ArrayList<PCBook> pcbooks) {
		//ini juga nunggu controller
		return true;
	}
	
	public static ArrayList<PCBook> getAllPCBookedData(IView view){
		try {
			return PCBook.getAllPCBookedData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error fetching data");
			return new ArrayList<PCBook>();
		}
	}
	
	public ArrayList<PCBook> getPcBookedByDate(String date, IView view){
		try {
			return PCBook.getPCBookedByDate(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error fetching data");
			return new ArrayList<PCBook>();
		}
	}
	
	public static PCBook getPCBookedByID(int pcID) throws SQLException {
		return PCBook.getPCBookedByID(pcID);
	}
	
	
	
}
