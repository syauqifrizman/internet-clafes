package model;

public class Job {
	
	//mendeklarasikan atribut yang dibutuhkan oleh job
	//id unik untuk setiap job
	private Integer job_ID;
	//id user(Computer Technician) yang seharusnya melakukan tugas ini
	private Integer userID;
	//id pc untuk pekerjaan ini
	private Integer pc_ID;
	//status job "Complete" selesai dikerjakan atau "UnComplete" belum dikerjakan
	private String jobStatus;
	
	//membuat kontruksi kelas job sesuai atributnya
	public Job(Integer job_ID, Integer userID, Integer pc_ID, String jobStatus) {
		this.job_ID = job_ID;
		this.userID = userID;
		this.pc_ID = pc_ID;
		this.jobStatus = jobStatus;
	}

	public Job() {
		// TODO Auto-generated constructor stub
	}

	//karena atribut dibuat private maka tidak dapat diakses secara langsung oleh kelas luar
	//maka untuk diakses kelas luar, dibutuhkan public getter(untuk mendapatkan atribut)
	//dan setter(untuk menaruh value ke atribut) untuk setiap atribut private
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
