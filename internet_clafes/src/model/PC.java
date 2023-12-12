package model;

public class PC {
	private Integer pc_ID;
	private String pc_condition;
	
	public PC(Integer pc_ID, String pc_condition) {
		this.pc_ID = pc_ID;
		this.pc_condition = pc_condition;
	}

	public PC() {
		// TODO Auto-generated constructor stub
	}

	public Integer getPc_ID() {
		return pc_ID;
	}

	public void setPc_ID(Integer pc_ID) {
		this.pc_ID = pc_ID;
	}

	public String getPc_condition() {
		return pc_condition;
	}

	public void setPc_condition(String pc_condition) {
		this.pc_condition = pc_condition;
	}
	
}
