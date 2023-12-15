package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;

public class TransactionController {
	
	public ArrayList<TransactionHeader> getAllTransactionHeaderData(){
		try {
			return TransactionHeader.getAllTransactionHeaderData();
		} catch (SQLException e) {
			return new ArrayList<TransactionHeader>();
		}
	}
	
	public ArrayList<TransactionDetail> getAllTransactionDetailData(Integer transactionID){
		try {
			return TransactionDetail.getAllTransactionDetail(transactionID);
		} catch (SQLException e) {
			return new ArrayList<TransactionDetail>();
		}
	}
	
	public ArrayList<TransactionDetail> getUserTransactionDetail(Integer UserID){
		try {
			return TransactionDetail.getUserTransactionDetail(UserID);
		} catch (SQLException e) {
			return new ArrayList<TransactionDetail>();
		}
	}
	
	public boolean AddTransaction(Integer transactionID, ArrayList<PCBook> pcbooks, Integer StaffID) {
		
		try {
			TransactionHeader.addNewTransactionHeader(StaffID, java.time.LocalDate.now().toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch blo
			Helper.showAlert(AlertType.ERROR, "Error in Transaction Header");
			return false;
		}
		
		try {
			TransactionDetail.addTransactionDetail(transactionID, pcbooks);
		} catch (SQLException e) {
			Helper.showAlert(AlertType.ERROR, "Error in Transaction Detail");
			return false;
		}
		
		return true;
	}
	
}
