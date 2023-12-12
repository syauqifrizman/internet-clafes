package model;

public class TransactionHeader {
	private Integer transactionID;
	private Integer staffID;
	private String staffName;
	private String transactionDate;
	
	public TransactionHeader(Integer transactionID, Integer staffID, String staffName, String transactionDate) {
		this.transactionID = transactionID;
		this.staffID = staffID;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}

	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public Integer getStaffID() {
		return staffID;
	}

	public void setStaffID(Integer staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
