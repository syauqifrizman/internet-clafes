package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.Report;

public class ReportController {
	
	//method utk menambah report baru
	public static boolean addNewReport(String userRole, String pcID, String reportNote) {
		
		//validasi jika PC ID kosong akan error
		if(pcID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC ID can't be empty");
			return false;
		}
		
		//validasi jika tidak ada PC dengan ID yang ada dalam parameter maka akan error
		else if(PCController.getPCDetail(pcID)==null) {
			Helper.showAlert(AlertType.ERROR, "PC not found");
			return false;
		}
		
		//validasi jika report note kosong maka akan error
		else if(reportNote.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Report Note can't be empty");
			return false;
		}
		
		////validasi yang melakukan report hanya bisa Customer/Operator
		else if(!userRole.equals("Customer") && !userRole.equals("Operator")) {
			Helper.showAlert(AlertType.ERROR, "Only Customer and Operator can make reports");
			return false;
		}
		
		//memanggil method dari model untuk memasukkan ke database
		try {
			Report.addNewReport(userRole, Integer.parseInt(pcID), reportNote);
//			PCController.updatePCCondition(pcID, "Broken");
		} catch (NumberFormatException e) {
			Helper.showAlert(AlertType.ERROR, "Error Adding Report");
			return false;
		} catch (SQLException e) {
			Helper.showAlert(AlertType.ERROR, "Error Adding Report");
			return false;
		}
		
		return true;
	}
	
	//method untuk mengambil semua report dari database
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
