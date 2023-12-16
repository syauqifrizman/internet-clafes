package factory;

import model.Job;

public class JobFactory {
	
	//method untuk membuat job baru
	public static Job createJob(String userID, String pc_ID) {
		Integer newUserID = Integer.parseInt(userID);
		Integer newPc_ID = Integer.parseInt(pc_ID);
		
		//buat object job baru
		Job newJob = new Job();
		
		//masukkan data ke object tersebut
		newJob.setJob_ID(0);
		newJob.setUserID(newUserID);
		newJob.setPc_ID(newPc_ID);
		newJob.setJobStatus("UnComplete");
		
		//return object baru yang sudah dibuat
		return newJob;
	}
}
