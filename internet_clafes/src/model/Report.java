package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sqlConnect.Connect;

public class Report {
	private Integer report_ID;
	private String userRole;
	private Integer pc_ID;
	private String reportNote;
	private String reportDate;
	
	public Report(Integer report_ID, String userRole, Integer pc_ID, String reportNote, String reportDate) {
		this.report_ID = report_ID;
		this.userRole = userRole;
		this.pc_ID = pc_ID;
		this.reportNote = reportNote;
		this.reportDate = reportDate;
	}

	public Integer getReport_ID() {
		return report_ID;
	}

	public void setReport_ID(Integer report_ID) {
		this.report_ID = report_ID;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Integer getPc_ID() {
		return pc_ID;
	}

	public void setPc_ID(Integer pc_ID) {
		this.pc_ID = pc_ID;
	}

	public String getReportNote() {
		return reportNote;
	}

	public void setReportNote(String reportNote) {
		this.reportNote = reportNote;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public static void addNewReport(String role, Integer pc_ID, String reportNote) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `Report` VALUES (?, '?', ?, '?', '?')");
		
		ps.setInt(1, 0);
		ps.setString(2, role);
		ps.setInt(3, pc_ID);
		ps.setString(4, reportNote);
		ps.setString(5, java.time.LocalDate.now().toString());
		
		ps.executeUpdate();
	}
	
	public static ArrayList<Report> getAllReportData() throws SQLException{
		ArrayList<Report> reports = new ArrayList<Report>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `Report`");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Integer id;
			String role;
			Integer pcID;
			String note;
			String date;
			
			id = rs.getInt(1);
			role = rs.getString(2);
			pcID = rs.getInt(3);
			note = rs.getString(4);
			date = rs.getString(5);
			
			Report report = new Report(id, role, pcID, note, date);
			reports.add(report);
		}
		
		return reports;
	}
	
}
