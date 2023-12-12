package controller;

import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PC;
import repository.PCRepository;

public class PCController {
	
	public ArrayList<PC> getAllPCData(){
		return PCRepository.getAllPCData();
	}
	
	public void updatePCCondition(Integer pc_ID, String pc_condition) {
		
		if(pc_ID.equals(null)) {
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
	
	public void deletePC(Integer pc_ID) {
		
	}
}

Helper.showAlert(AlertType.ERROR, "Credential Invalid");
