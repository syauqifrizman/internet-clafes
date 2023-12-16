package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sqlConnect.Connect;

public class Report {
	
	//mendeklarasikan atribut yang dibutuhkan oleh Report
	//id unik untuk setiap report
	private Integer report_ID;
	//role dari yang membuat report "Customer" atau "Operator"
	private String userRole;
	//id pc dari yang di report
	private Integer pc_ID;
	//catatan mengenai mengapa di report
	private String reportNote;
	//tanggal report
	private String reportDate;
	
	//membuat kontruksi kelas Report sesuai atributnya
	public Report(Integer report_ID, String userRole, Integer pc_ID, String reportNote, String reportDate) {
		this.report_ID = report_ID;
		this.userRole = userRole;
		this.pc_ID = pc_ID;
		this.reportNote = reportNote;
		this.reportDate = reportDate;
	}

	//karena atribut dibuat private maka tidak dapat diakses secara langsung oleh kelas luar
	//maka untuk diakses kelas luar, dibutuhkan public getter(untuk mendapatkan atribut)
	//dan setter untuk menaruh value ke atribut) untuk setiap atribut private
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

	//method untuk membuat report baru ke database
	public static void addNewReport(String role, Integer pc_ID, String reportNote) throws SQLException {
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("INSERT INTO `report` VALUES (?, ?, ?, ?, ?)");
		
		ps.setInt(1, 0);
		ps.setString(2, role);
		ps.setInt(3, pc_ID);
		ps.setString(4, reportNote);
		ps.setDate(5, Date.valueOf(java.time.LocalDate.now()));
		
		ps.executeUpdate();
	}
	
	//method untuk mendapatkan data dari semua report yang ada dari database
	public static ArrayList<Report> getAllReportData() throws SQLException{
		ArrayList<Report> reports = new ArrayList<Report>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `report`");
		
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
			date = rs.getDate(5).toString();
			
			Report report = new Report(id, role, pcID, note, date);
			reports.add(report);
		}
		
		return reports;
	}
	
}
