package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.PC;
import model.Report;
import view.IView;

public class ReportController {
	
	public static boolean addNewReport(String UserRole, String PcID, String ReportNote, IView view) {
		if(PcID.isEmpty()) {
			view.showError("PC ID can't be empty");
			return false;
		}
		
		if(PCController.getPCDetail(PcID)==null) {
			view.showError("PC not found");
			return false;
		}
		if(ReportNote.isBlank()) {
			view.showError("Report Note can't be empty");
			return false;
		}
		
		if(!UserRole.equals("Customer") && !UserRole.equals("Technician")) {
			view.showError("Only Users and Technician can make reports");
			return false;
		}
		
		try {
			Report.addNewReport(UserRole, Integer.parseInt(PcID), ReportNote);
			PCController.updatePCCondition(PcID, "Broken");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			view.showError("Error Adding Report");
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error Adding Report");
			return false;
		}
		return true;
	}
	
	public static ArrayList<Report> getAllReportData(IView view){
		try {
			return Report.getAllReportData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error fetching Reports");
			return new ArrayList<Report>();
		}
	}
}
