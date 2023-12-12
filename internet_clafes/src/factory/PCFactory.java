package factory;

import model.PC;

public class PCFactory {
	
	public static PC createPC(String pc_ID, String pc_condition) {
		Integer newPCID = Integer.parseInt(pc_ID);
		
		PC newPC = new PC();
		newPC.setPc_ID(newPCID);
		newPC.setPc_condition(pc_condition);
		
		return newPC;
	}
}
