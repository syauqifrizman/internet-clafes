package model;

public class TransactionDetail {
	private int TransactionID;
	private int PC_ID;
	private String CustomerName;
	private String BookedDate;
	
	public TransactionDetail(int transactionID, int pC_ID, String customerName, String bookedDate) {
		super();
		TransactionID = transactionID;
		PC_ID = pC_ID;
		CustomerName = customerName;
		BookedDate = bookedDate;
	}
	public int getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
	}
	public int getPC_ID() {
		return PC_ID;
	}
	public void setPC_ID(int pC_ID) {
		PC_ID = pC_ID;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getBookedDate() {
		return BookedDate;
	}
	public void setBookedDate(String bookedDate) {
		BookedDate = bookedDate;
	}
	
	
}
