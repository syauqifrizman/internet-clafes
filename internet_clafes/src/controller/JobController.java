package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.Job;
import model.PC;
import model.PCBook;
import model.User;
import repository.JobRepository;

public class JobController {
	
	//untuk menambah job baru
	public static void addNewJob(String userID, String pc_ID, String newPC) {
//		validasi frontend
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC ID Cannot be empty");
			return;
		}
		
		if(newPC.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "New PC ID Cannot be empty");
			return;
		}
		
//		di sini harus check dari getUserData
//		check user nya technician atau bukan
		User getUser = UserController.getUserDetail(Integer.parseInt(userID));
		
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
		
		PC backupPC = PCController.getPCDetail(newPC);
		if(backupPC == null) {
			Helper.showAlert(AlertType.ERROR, "New PC ID must exists on database / PC doesn't exists");
			return;
		}
		
//		getPCBookedData	dari PCBookController
//		check pc nya lagi book atau engga
		PCBook getPCBook = null;
		try {
			getPCBook = PcBookController.getPCBookedData(Integer.parseInt(pc_ID), Date.valueOf(java.time.LocalDate.now()));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			Helper.showAlert(AlertType.ERROR, "Error fetching Booking List");
			return;
		}
		
//		jika sudah ada book PC yang ingin dibuatkan job nya, user yg book akan lgsg dipindahkan
		//ke PC yang sudah di input oleh admin
		if(getPCBook != null) {
			Helper.showAlert(AlertType.INFORMATION, "A User has booked this PC. Assigning them to the entered PC ID");
			try {
				PcBookController.assignUsertoNewPC(getPCBook.getBookID(), 
						Integer.parseInt(newPC), Date.valueOf(java.time.LocalDate.now()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//buat job baru ke database
		String addMSG = Job.addNewJob(userID, pc_ID);
		Helper.showAlert(AlertType.INFORMATION, addMSG);
		
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
		Job getJob = Job.getJobDetail(job_ID);
		
		if(getJob.getJobStatus().equals("Complete")) {
			//update kondisi PC dari maintenance menjadi Usable
			PCController.updatePCCondition(getJob.getPc_ID().toString(), "Usable");
			return;
		}
		else if(getJob.getJobStatus().equals("UnComplete")){
			// update kondisi PC dari maintenance menjadi Maintenance
			PCController.updatePCCondition(getJob.getPc_ID().toString(), "Maintenance");
			return;
		}
		
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
	public static ArrayList<Job> getTechnicianJob(String userID){
		return Job.getTechnicianJob(userID);
	}
	
	//ngambil semua job yg ada
	public static ArrayList<Job> getAllJobData(){
		return Job.getAllJobData();
	}
	
	public static Job getTechnicianJobByID(String job_id) {
		return Job.getJobDetail(job_id);
	}
	
	
}
