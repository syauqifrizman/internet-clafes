package model;

public class TransactionHeader {
	private int TransactionID;
	private int StaffID;
	private String StaffName;
	private String TransactionDate;
	
	public TransactionHeader(int transactionID, int staffID, String staffName, String transactionDate) {
		super();
		TransactionID = transactionID;
		StaffID = staffID;
		StaffName = staffName;
		TransactionDate = transactionDate;
	}

	public int getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
	}

	public int getStaffID() {
		return StaffID;
	}

	public void setStaffID(int staffID) {
		StaffID = staffID;
	}

	public String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		StaffName = staffName;
	}

	public String getTransactionDate() {
		return TransactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		TransactionDate = transactionDate;
	}
	
	
}
