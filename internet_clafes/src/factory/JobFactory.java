package factory;

import model.Job;

public class JobFactory {
	public static Job createJob(String userID, String pc_ID) {
		Integer newUserID = Integer.parseInt(userID);
		Integer newPc_ID = Integer.parseInt(pc_ID);
		
		Job newJob = new Job();
		
		newJob.setJob_ID(0);
		newJob.setUserID(newUserID);
		newJob.setPc_ID(newPc_ID);
		newJob.setJobStatus("UnComplete");
		
		return newJob;
	}
}
