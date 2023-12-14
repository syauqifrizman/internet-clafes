package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import factory.PCFactory;
import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PC;
import model.PCBook;
import repository.PCRepository;

public class PCController {
	
	public static ArrayList<PC> getAllPCData(){
		return PCRepository.getAllPCData();
	}
	
	public static void updatePCCondition(String pc_ID, String pc_condition) {
		
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC must be choosen");
			return;
		}
		else if(!pc_condition.equals("Usable") && !pc_condition.equals("Maintenance") && !pc_condition.equals("Broken")) {
			Helper.showAlert(AlertType.ERROR, "Must be either 'Usable', 'Maintenance' or 'Broken'.");
			return;
		}
		
		String updateStatus = PCRepository.updatePCCondition(pc_ID, pc_condition);
		Helper.showAlert(AlertType.INFORMATION, updateStatus);
		return;
	}
	
	public static void deletePC(String pc_ID) {
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC must be choosen");
			return;
		}
		
//		getPCBookedData() dgn manggil PCBookController
//		nanti return PCBookedData di PCBookController
		
//		nanti hasil return PCBookedData yg di PCBookController itu, return ke sini
//		PCBook getPCBookData = PcBookController.get
		
		Integer newPC_ID = Integer.parseInt(pc_ID);
		
		PCBook pcbook = null;
		try {
			pcbook = PcBookController.getPCBookedByID(newPC_ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
////		lagi-lagi ini date dapet dari mana, di parameter method deletePC() ga ada date
//		PCBook getPcBook = PcBookController.getPCBookedData(newPC_ID, pcbook.);
		
//		if PC have any book list in the future, then show error
		if(pcbook != null) {
			Helper.showAlert(AlertType.ERROR, "PC is booked");
			return;
		}
//		otherwise perform delete
		String deleteStatus = PCRepository.deleteNewPC(pc_ID);
		Helper.showAlert(AlertType.INFORMATION, deleteStatus);
		return;
	}
	
	public static void addNewPC(String pc_ID) {
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Cannot be empty");
			return;
		}
		
//		cari pc_id ada di database atau engga
		PC getPC = PCRepository.getPCDetail(pc_ID);
//		getPC != null, pc udah ada di database
		if(getPC != null) {
			Helper.showAlert(AlertType.ERROR, "PC already exist, PC ID must be unique");
			return;
		}
//		getPC == null
//		buatin object pc baru, insert ke database, pc_condition nya mungkin nanti bisa dari parameter
		getPC = PCFactory.createPC(pc_ID);
		String insertStatus = PCRepository.insertNewPC(getPC);
		Helper.showAlert(AlertType.INFORMATION, insertStatus);
		return;
	}
	
	public static PC getPCDetail(String pc_ID) {
		PC getPC = PCRepository.getPCDetail(pc_ID);
		
		if(getPC == null) {
			Helper.showAlert(AlertType.ERROR, "PC doesn't exist");
			return null;
		}
		return getPC;
	}
}