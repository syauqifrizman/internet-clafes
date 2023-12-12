package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Job;
import model.PC;
import sqlConnect.Connect;

public class JobRepository {
	
	public static String addNewJob(Job newJob) {
		Connect db = Connect.getConnection();
		
		String query = "INSERT INTO jobs VALUES ('%d', '%d', '%s', %s')";
		String queryExecute = String.format(query, newJob.getJob_ID(), newJob.getUserID(), newJob.getPc_ID(), newJob.getJobStatus());
		
		db.executeUpdate(queryExecute);
		
		return "Success add new job!";
	}
	
	public static String updateJobStatus(Integer job_ID, String jobStatus) {
		Connect db = Connect.getConnection();
		
		String query = "UPDATE jobs SET jobStatus = '%s' WHERE pc_ID = '%s'";
		String queryExecute = String.format(query, jobStatus, job_ID);
		
		db.executeUpdate(queryExecute);
		
		return "Success updated the job!";
	}
	
	public ArrayList<PC> getPcOnWorkingList(Integer pc_ID) {
		ArrayList<PC> pcList = new ArrayList<PC>();
		
		
		return pcList;
	}
	
	public static Job getTechnicianJob(Integer userID) {
		Connect db = Connect.getConnection();
		
		String query = "SELECT * FROM jobs WHERE userID = '%d'";
		String queryExecute = String.format(query, userID);
		
		ResultSet res = db.executeQuery(queryExecute);
		
		Job getJob = null;
		try {
			while(res.next()) {
				Integer currJob_ID = res.getInt(1);
				Integer currUserID = res.getInt(2);
				String currPC_ID = res.getString(3);
				String currJobStatus = res.getString(4);
				
				getJob = new Job(currJob_ID, currUserID, currPC_ID, currJobStatus);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getJob;
		
	}
	
	public static ArrayList<Job> getAllJobData(){
		ArrayList<Job> jobList = new ArrayList<Job>();
		Connect db = Connect.getConnection();
		
		String queryExecute = "SELECT * FROM jobs";
		db.executeQuery(queryExecute);
		
		return jobList; 
	}
	
	
}
