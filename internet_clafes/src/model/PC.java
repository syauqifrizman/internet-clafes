package model;

import java.util.ArrayList;

import factory.PCFactory;
import repository.PCRepository;

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
	
	public static PC getPCDetail(String pc_ID) {
		return PCRepository.getPCDetail(pc_ID);
	}
	
	public static String insertNewPC(String pc_ID) {
		PC getPC = PCFactory.createPC(pc_ID);
		return PCRepository.insertNewPC(getPC);
	}
	
	public static String deletePC(String pc_ID) {
		return PCRepository.deleteNewPC(pc_ID);
	}
	
	public static String updatePCCondition(String pc_id, String pc_condition) {
		return PCRepository.updatePCCondition(pc_id, pc_condition);
	}
	
	public static ArrayList<PC> getAllPCData(){
		return PCRepository.getAllPCData();
	}
	
}
