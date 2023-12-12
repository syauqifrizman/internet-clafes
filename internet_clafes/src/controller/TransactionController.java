package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;
import view.IView;

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
	
	public boolean AddTransaction(Integer transactionID, ArrayList<PCBook> pcbooks, Integer StaffID, IView view) {
		
		try {
			TransactionHeader.addNewTransactionHeader(StaffID, java.time.LocalDate.now().toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			view.showError("Error in Transaction Header");
			return false;
		}
		
		try {
			TransactionDetail.addTransactionDetail(transactionID, pcbooks);
		} catch (SQLException e) {
			view.showError("Error in Transaction Detail");
			return false;
		}
		
		return true;
	}
	
}
