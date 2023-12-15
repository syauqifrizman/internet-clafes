package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PC;
import model.Report;
import view.IView;

public class ReportController {
	
	public static boolean addNewReport(String UserRole, String PcID, String ReportNote) {
		if(PcID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC ID can't be empty");
			return false;
		}
		
		if(PCController.getPCDetail(PcID)==null) {
			Helper.showAlert(AlertType.ERROR, "PC not found");
			return false;
		}
		if(ReportNote.isBlank()) {
			Helper.showAlert(AlertType.ERROR, "Report Note can't be empty");
			return false;
		}
		
		if(!UserRole.equals("Customer") && !UserRole.equals("Technician")) {
			Helper.showAlert(AlertType.ERROR, "Only Users and Technician can make reports");
			return false;
		}
		
		try {
			Report.addNewReport(UserRole, Integer.parseInt(PcID), ReportNote);
			PCController.updatePCCondition(PcID, "Broken");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Adding Report");
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error Adding Report");
			return false;
		}
		return true;
	}
	
	public static ArrayList<Report> getAllReportData(){
		try {
			return Report.getAllReportData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error fetching Reports");
			return new ArrayList<Report>();
		}
	}
}
