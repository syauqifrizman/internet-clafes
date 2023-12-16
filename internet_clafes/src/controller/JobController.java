package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import factory.JobFactory;
import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.Job;
import model.PC;
import model.PCBook;
import model.User;
import repository.JobRepository;
import repository.UserRepository;

public class JobController {
	
	//untuk menambah job baru
	public static void addNewJob(String userID, String pc_ID) {
//		validasi frontend
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Cannot be empty");
			return;
		}
		
//		di sini harus check dari getUserData
//		check user nya technician atau bukan
		User getUser = UserRepository.getUserDetail(Integer.parseInt(userID));
		
		if(!getUser.getUserRole().equals("Computer Technician") && !getUser.getUserRole().equals("Admin")) {
			Helper.showAlert(AlertType.ERROR, "User Role must be 'Computer Technician'");
			return;
		}
		
//		check pc nya ada atau engga
		PC getPC = PCController.getPCDetail(pc_ID);
		if(getPC == null) {
			Helper.showAlert(AlertType.ERROR, "PC ID must exists on database / PC doesn't exists");
			return;
		}
		
//		getPCBookedData	dari PCBookController
//		check pc nya lagi book atau engga
		Integer newPC_ID = Integer.parseInt(pc_ID);
		PCBook getPCBook = null;
		try {
			getPCBook = PcBookController.getPCBookedData(newPC_ID, Date.valueOf(java.time.LocalDate.now()));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error fetching Booking List");
			return;
		}
		
//		jika sudah ada booked, error
		if(getPCBook != null) {
			Helper.showAlert(AlertType.ERROR, "A User has booked this PC. Please assign them to another PC");
			return;
		}
		
		//buat job baru ke database
		JobRepository.addNewJob(JobFactory.createJob(userID, pc_ID));
		
		//update PC status menjadi maintenance
		PCController.updatePCCondition(pc_ID, "Maintenance");

		return;
		
	}
	
	//untuk update job status
	public static void updateJobStatus(String job_ID, String jobStatus) {
		
		//validasi input, harus complete atau uncomplete
		if(!jobStatus.equals("Complete") && !jobStatus.equals("UnComplete")) {
			Helper.showAlert(AlertType.ERROR, "Must be either 'Complete' or 'UnComplete'");
			return;
		}
		
		//update status ke database
		String updateStatus = JobRepository.updateJobStatus(job_ID, jobStatus);
		Helper.showAlert(AlertType.INFORMATION, updateStatus);
		
		//ngambil PC ID nya
		Job getJob = JobRepository.getJobDetail(job_ID);
		
		//update kondisi PC dari maintenance menjadi Usable
		PCController.updatePCCondition(getJob.getPc_ID().toString(), "Usable");
		return;
	}
	
	//untuk dapetin PC yg udh dibuat job nya tapi belom di-finish sama CT/Admin
	public ArrayList<PC> getPcOnWorkingList(String pc_ID){
		ArrayList<PC> pcList = new ArrayList<PC>();
		
		//ambil semua data PC dan masukin ke arraylist
		pcList = PCController.getAllPCData();
		
		//dicari mana yg masih maintenance dan dimasukin ke arraylist baru
		ArrayList<PC> pcOnWorkingList = new ArrayList<PC>();
		for (PC pc : pcList) {
			if(pc.getPc_condition().equals("Maintenance")) {
				pcOnWorkingList.add(pc);				
			}
		}
		return pcOnWorkingList;
	}
	
	//ngambil job berdasarkan siapa yg buat
	public ArrayList<Job> getTechnicianJob(String userID){
		return JobRepository.getTechnicianJob(userID);
	}
	
	//ngambil semua job yg ada
	public ArrayList<Job> getAllJobData(){
		return JobRepository.getAllJobData();
	}
	
	
}
