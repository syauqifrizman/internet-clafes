package controller;

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
	
	public boolean assignUsertoNewPC(int BookID, int NewPCID, String date, IView view) throws SQLException {
		if(NewPCID == 0) {
			view.showError("PC ID must be filled");
			return false;
		}
		
		if(PCBook.getPCBookedData(NewPCID, date)==null) {
			view.showError("PC has been booked for that day");
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
	
	public boolean addNewBook(int PcID, int UserID, String bookedDate, IView view) throws SQLException {
		if(PcID == 0) {
			view.showError("Please choose a PC");
			return false;
		}
		
		if(bookedDate.isBlank()) {
			view.showError("Please pick a date");
			return false;
		}
		
		if(PCBook.getPCBookedData(PcID, bookedDate)==null) {
			view.showError("PC has been booked for that day");
			return false;
		}
		
		PCBook.addNewBook(PcID, UserID, bookedDate);
		return true;
		
	}
	
	public boolean finishBook(ArrayList<PCBook> pcbooks) {
		//ini juga nunggu controller
		return true;
	}
	
	public ArrayList<PCBook> getAllPCBookedData(IView view){
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
	
	
	
}
