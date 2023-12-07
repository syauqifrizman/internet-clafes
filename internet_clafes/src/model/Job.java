package model;

public class Job {
	
	private int Job_ID;
	private int UserID;
	private int PC_ID;
	private String JobStatus;
	
	public Job(int job_ID, int userID, int pC_ID, String jobStatus) {
		super();
		Job_ID = job_ID;
		UserID = userID;
		PC_ID = pC_ID;
		JobStatus = jobStatus;
	}

	public int getJob_ID() {
		return Job_ID;
	}

	public void setJob_ID(int job_ID) {
		Job_ID = job_ID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public int getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(int pC_ID) {
		PC_ID = pC_ID;
	}

	public String getJobStatus() {
		return JobStatus;
	}

	public void setJobStatus(String jobStatus) {
		JobStatus = jobStatus;
	}
	
	
	
}
