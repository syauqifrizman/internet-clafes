package controller;

import java.util.ArrayList;

import factory.JobFactory;
import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.Job;
import model.PC;
import model.PCBook;
import model.User;
import repository.JobRepository;
import repository.PCRepository;

public class JobController {
	public void addNewJob(String userID, String pc_ID) {
//		validasi frontend
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Cannot be empty");
			return;
		}
		
//		di sini harus check dari getUserData
//		check user nya technician atau bukan
		User getUser = UserController.getUserData(username, password);
		
		if(!getUser.getUserRole().equals("Technician")) {
			Helper.showAlert(AlertType.ERROR, "User Role must be 'Technician'");
			return;
		}
		
//		check pc nya ada atau engga
		PC getPC = PCController.getPCDetail(pc_ID);
//		getPC = null
		if(getPC == null)) {
			Helper.showAlert(AlertType.ERROR, "PC ID must exists on database / PC doesn't exists");
			return;
		}
		
//		getPCBookedData	dari PCBookController
//		check pc nya lagi book atau engga
		Integer newPC_ID = Integer.parseInt(pc_ID);
//		date gatau dapet dari mana ini? kalo dari user sih di parameter harusnya dikasih
		PCBook getPCBook = PcBookController.getPCBookedData(newPC_ID, date);
		
//		jika sudah ada booked, error
		if(getPCBook != null) {
			Helper.showAlert(AlertType.ERROR, "There is some technician doing the job");
			return;
		}
		
//		jika belum ada
		PcBookController.assignUsertoNewPC(getPCBook.getBookID(), newPC_ID, getPCBook.getBookedDate(), null);
		return;
		
//		kalo baca dari sequence diagram, cuma sampe assignUserToNewPC aja, ga ada suruh add new job
		
//		tapi kalo perlu add new job baru, bisa ngikutin method addNewJob();
//		Job newJob = JobFactory.createJob(userID, pc_ID);
//		JobRepository.addNewJob(newJob);
	}
	
	public void updateJobStatus(String job_ID, String jobStatus) {
		if(!jobStatus.equals("Complete") || !jobStatus.equals("UnComplete")) {
			Helper.showAlert(AlertType.ERROR, "Must be either 'Complete' or 'UnComplete'");
			return;
		}
		
		String updateStatus = JobRepository.updateJobStatus(job_ID, jobStatus);
		Helper.showAlert(AlertType.INFORMATION, updateStatus);
		
		Job getJob = JobRepository.getJobDetail(job_ID);
		
		PC getPC = PCController.getPCDetail(getJob.getPc_ID().toString());
		
		PCController.updatePCCondition(getPC.getPc_ID().toString(), getPC.getPc_condition());
		return;
	}
	
//	mungkin tampilin PC yg lagi maintenance?
	public ArrayList<PC> getPcOnWorkingList(String pc_ID){
		ArrayList<PC> pcList = new ArrayList<PC>();
		
		pcList = PCController.getAllPCData();
		
		ArrayList<PC> pcOnWorkingList = new ArrayList<PC>();
		for (PC pc : pcList) {
			if(pc.getPc_condition().equals("Maintenance")) {
				pcOnWorkingList.add(pc);				
			}
		}
		return pcOnWorkingList;
	}
	
	public ArrayList<Job> getTechnicianJob(String userID){
		return JobRepository.getTechnicianJob(userID);
	}
	
	public ArrayList<Job> getAllJobData(){
		return JobRepository.getAllJobData();
	}
	
	
}
