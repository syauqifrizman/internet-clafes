package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class Report {
	private int Report_ID;
	private String UserRole;
	private int PC_ID;
	private String ReportNote;
	private String ReportDate;
	
	public Report(int report_ID, String userRole, int pC_ID, String reportNote, String reportDate) {
		super();
		Report_ID = report_ID;
		UserRole = userRole;
		PC_ID = pC_ID;
		ReportNote = reportNote;
		ReportDate = reportDate;
	}

	public int getReport_ID() {
		return Report_ID;
	}

	public void setReport_ID(int report_ID) {
		Report_ID = report_ID;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

	public int getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(int pC_ID) {
		PC_ID = pC_ID;
	}

	public String getReportNote() {
		return ReportNote;
	}

	public void setReportNote(String reportNote) {
		ReportNote = reportNote;
	}

	public String getReportDate() {
		return ReportDate;
	}

	public void setReportDate(String reportDate) {
		ReportDate = reportDate;
	}
	
	public static void addNewReport(String role, int PcID, String ReportNote) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `Report` VALUES (?, '?', ?, '?', '?')");
		
		ps.setInt(1, 0);
		ps.setString(2, role);
		ps.setInt(3, PcID);
		ps.setString(4, ReportNote);
		ps.setString(5, java.time.LocalDate.now().toString());
		
		ps.executeUpdate();
	}
	
	public static ArrayList<Report> getAllReportData() throws SQLException{
		ArrayList<Report> reports = new ArrayList<Report>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `Report`");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int id;
			String role;
			int PcID;
			String note;
			String date;
			
			id = rs.getInt(1);
			role = rs.getString(2);
			PcID = rs.getInt(3);
			note = rs.getString(4);
			date = rs.getString(5);
			
			Report report = new Report(id, role, PcID, note, date);
			reports.add(report);
		}
		
		return reports;
	}
	
}
