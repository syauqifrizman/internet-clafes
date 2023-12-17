package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PC;
import model.PCBook;
import view.ViewPC;

public class PCController {
	
	//mengambil semua PC dari database
	public static ArrayList<PC> getAllPCData(){
		return PC.getAllPCData();
	}
	
	//method untuk mengupdate kondisi PC 
	public static void updatePCCondition(String pc_ID, String pc_condition) {
		
		//validasi jika PC ID kosong, maka akan error
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC must be choosen");
			return;
		}
		
		//validasi kalau input Condition bukan Usable/Maintenance/Error
		else if(!pc_condition.equals("Usable") && !pc_condition.equals("Maintenance") && !pc_condition.equals("Broken")) {
			Helper.showAlert(AlertType.ERROR, "Must be either 'Usable', 'Maintenance' or 'Broken'.");
			return;
		}
		
		//lakukan update condition ke database
		String updateStatus = PC.updatePCCondition(pc_ID, pc_condition);
		Helper.showAlert(AlertType.INFORMATION, updateStatus);
		
		return;
	}
	
	//method untuk menghapus PC
	public static void deletePC(String pc_ID) {
		
		//validasi kalau PC ID nya kosong, maka akan error
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "PC must be choosen");
			return;
		}
		
		Integer newPC_ID = Integer.parseInt(pc_ID);
		
		//mencari apakah PC yang ingin dihapus masih di-booking
		PCBook pcbook = null;
		try {
			pcbook = PcBookController.getPCBookedByID(newPC_ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//if PC have any book list in the future, then show error
		if(pcbook != null) {
			Helper.showAlert(AlertType.ERROR, "PC is booked, can't delete the PC");
			return;
		}
		
		//otherwise perform delete
		String deleteStatus = PC.deletePC(pc_ID);
		Helper.showAlert(AlertType.INFORMATION, deleteStatus);
    	
		ViewPC viewPCPage = new ViewPC();
    	viewPCPage.show();
    	
		return;
	}
	
	//method utk add PC baru
	public static void addNewPC(String pc_ID) {
		
		//validasi kalau PC ID kosong maka akan error
		if(pc_ID.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Cannot be empty");
			return;
		}
		
		//validasi kalau PC ID nya 0 maka akan error
		else if(Integer.parseInt(pc_ID) == 0) {
			Helper.showAlert(AlertType.ERROR, "PC ID can't be 0");
			return;
		}
		
		//validasi kalau PC ID nya bukan angka maka akan error
		else if(!pc_ID.matches("[1-9]+")) {
			Helper.showAlert(AlertType.ERROR, "PC ID must be number only");
			return;
		}

		//cari pc_id ada di database atau engga
		PC getPC = PC.getPCDetail(pc_ID);
		
		//	getPC != null, pc udah ada di database
		if(getPC != null) {
			Helper.showAlert(AlertType.ERROR, "PC already exist, PC ID must be unique");
			return;
		}
		
		//buatin object pc baru, insert ke database, pc_condition nya mungkin nanti bisa dari parameter
		String insertStatus = PC.insertNewPC(pc_ID);
		
		if(insertStatus.equals("Success add new PC!")) {
			Helper.showAlert(AlertType.INFORMATION, insertStatus);
			ViewPC viewPCPage = new ViewPC();
			viewPCPage.show();
		}
		
		return;
	}
	
	//method untuk mengambil PC berdasarkan ID nya
	public static PC getPCDetail(String pc_ID) {
		
		//memanggil method nya di model
		PC getPC = PC.getPCDetail(pc_ID);
		
		//kalau hasilnya null, berarti tidak ada PC nya
		if(getPC == null) {
			Helper.showAlert(AlertType.ERROR, "PC doesn't exist");
			return null;
		}
		return getPC;
	}
}