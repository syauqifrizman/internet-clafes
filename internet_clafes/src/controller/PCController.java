package controller;

import java.util.ArrayList;

import factory.PCFactory;
import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PC;
import model.PCBook;
import repository.PCRepository;

public class PCController {
	
	public ArrayList<PC> getAllPCData(){
		return PCRepository.getAllPCData();
	}
	
	public void updatePCCondition(String pc_ID, String pc_condition) {
		
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC must be choosen");
			return;
		}
		else if(pc_condition.equals("Usable") || pc_condition.equals("Maintenance") || pc_condition.equals("Broken")) {
			Helper.showAlert(AlertType.ERROR, "Must be either 'Usable', 'Maintenance' or 'Broken'.");
			return;
		}
		
		String updateStatus = PCRepository.updatePCCondition(pc_ID, pc_condition);
		Helper.showAlert(AlertType.INFORMATION, updateStatus);
	}
	
	public void deletePC(String pc_ID) {
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC must be choosen");
			return;
		}
		
//		getPCBookedData() dgn manggil PCBookController
//		nanti return PCBookedData di PCBookController
		
//		nanti hasil return PCBookedData yg di PCBookController itu, return ke sini
//		PCBook getPCBookData = PcBookController.get
		
//		if PC don't have any book list in the future, then perform delete
		String deleteStatus = PCRepository.deleteNewPC(pc_ID);
		Helper.showAlert(AlertType.INFORMATION, deleteStatus);
//		return;
		
//		else PC have some book list in the future
		Helper.showAlert(AlertType.ERROR, "PC have some book list in the future");
		return;
	}
	
	public void addNewPC(String pc_ID) {
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Cannot be empty");
			return;
		}
		
//		pc_ID = 1
//		1 belum ada
		PC getPC = PCRepository.getPCDetail(pc_ID);
//		getPC = null
//		buatin object pc baru, insert ke database
		if(getPC.equals(null)) {
			getPC = PCFactory.createPC(pc_ID);
			String insertStatus = PCRepository.insertNewPC(getPC);
			Helper.showAlert(AlertType.INFORMATION, insertStatus);
			return;
		}
		else {
			Helper.showAlert(AlertType.ERROR, "PC already exist, PC ID must be unique");
			return;
		}
	}
	
	public static PC getPCDetail(String pc_ID) {
		PC getPC = PCRepository.getPCDetail(pc_ID);
		
		if(getPC.equals(null)) {
			Helper.showAlert(AlertType.ERROR, "PC doesn't exist");
			return null;
		}
		return getPC;
	}
}