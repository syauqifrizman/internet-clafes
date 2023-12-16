package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PC;
import model.Report;

public class ReportController {
	
	public static boolean addNewReport(String userRole, String pcID, String reportNote) {
		if(pcID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC ID can't be empty");
			return false;
		}
		else if(PCController.getPCDetail(pcID)==null) {
			Helper.showAlert(AlertType.ERROR, "PC not found");
			return false;
		}
		else if(reportNote.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Report Note can't be empty");
			return false;
		}
		else if(!userRole.equals("Customer") && !userRole.equals("Operator")) {
			Helper.showAlert(AlertType.ERROR, "Only Customer and Operator can make reports");
			return false;
		}
		
		try {
			Report.addNewReport(userRole, Integer.parseInt(pcID), reportNote);
//			PCController.updatePCCondition(pcID, "Broken");
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
