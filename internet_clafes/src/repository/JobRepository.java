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
	
	public static String updateJobStatus(String job_ID, String jobStatus) {
		Integer newJob_ID = Integer.parseInt(job_ID);
		
		Connect db = Connect.getConnection();
		
		String query = "UPDATE jobs SET jobStatus = '%s' WHERE pc_ID = '%d'";
		String queryExecute = String.format(query, jobStatus, newJob_ID);
		
		db.executeUpdate(queryExecute);
		
		return "Success updated the job!";
	}
	
	public ArrayList<PC> getPcOnWorkingList(Integer pc_ID) {
		ArrayList<PC> pcList = new ArrayList<PC>();
		
		
		return pcList;
	}
	
	public static ArrayList<Job> getTechnicianJob(String userID) {
		Integer newUser_ID = Integer.parseInt(userID);
		
		ArrayList<Job> jobList = new ArrayList<Job>();
		Connect db = Connect.getConnection();
		
		String query = "SELECT * FROM jobs WHERE userID = '%d'";
		String queryExecute = String.format(query, newUser_ID);
		
		ResultSet res = db.executeQuery(queryExecute);
		
//		jobList = null;
		try {
			while(res.next()) {
				Integer currJob_ID = res.getInt(1);
				Integer currUserID = res.getInt(2);
				Integer currPC_ID = res.getInt(3);
				String currJobStatus = res.getString(4);
				
				Job getJob = new Job(currJob_ID, currUserID, currPC_ID, currJobStatus);
				jobList.add(getJob);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jobList;
		
	}
	
	public static Job getJobDetail(String job_ID) {
		Integer newJob_ID = Integer.parseInt(job_ID);
		Connect db = Connect.getConnection();
		
		String query = "SELECT * FROM jobs WHERE job_ID = '%d'";
		String queryExecute = String.format(query, newJob_ID);
		
		ResultSet res = db.executeQuery(queryExecute);
		
		Job getJob = null;
		try {
			while(res.next()) {
				Integer currJob_ID = res.getInt(1);
				Integer currUserID = res.getInt(2);
				Integer currPC_ID = res.getInt(3);
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
		
		ResultSet res = db.executeQuery(queryExecute);
		
//		jobList = null;
		try {
			while(res.next()) {
				Integer currJob_ID = res.getInt(1);
				Integer currUserID = res.getInt(2);
				Integer currPC_ID = res.getInt(3);
				String currJobStatus = res.getString(4);
				
				Job getJob = new Job(currJob_ID, currUserID, currPC_ID, currJobStatus);
				jobList.add(getJob);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jobList; 
	}
	
	
}
