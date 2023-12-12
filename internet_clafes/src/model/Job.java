package model;

public class Job {
	
	private Integer job_ID;
	private Integer userID;
	private Integer pc_ID;
	private String jobStatus;
	
	public Job(Integer job_ID, Integer userID, Integer pc_ID, String jobStatus) {
		this.job_ID = job_ID;
		this.userID = userID;
		this.pc_ID = pc_ID;
		this.jobStatus = jobStatus;
	}

	public Job() {
		// TODO Auto-generated constructor stub
	}

	public Integer getJob_ID() {
		return job_ID;
	}

	public void setJob_ID(Integer job_ID) {
		this.job_ID = job_ID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getPc_ID() {
		return pc_ID;
	}

	public void setPc_ID(Integer pc_ID) {
		this.pc_ID = pc_ID;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
}
