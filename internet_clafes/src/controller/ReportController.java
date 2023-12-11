package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.PC;
import model.Report;
import view.IView;

public class ReportController {
	
	//nunggu uqi kelar model PC
//	public boolean addNewReport(String UserRole, int PcID, String ReportNote, IView view) {
//		if(PcID==0) {
//			view.showError("PC ID can't be empty");
//			return false;
//		}
//		
//		if(PC.getPCDetail(PcID)==null) {
//			view.showError("PC not found");
//			return false;
//		}
//		if(ReportNote.isBlank()) {
//			view.showError("Report Note can't be empty");
//			return false;
//		}
//		
//		Report.addNewReport(UserRole, PcID, ReportNote);
//		return true;
//	}
	
	public ArrayList<Report> getAllReportData(IView view){
		try {
			return Report.getAllReportData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error fetching Reports");
			return new ArrayList<Report>();
		}
	}
}
