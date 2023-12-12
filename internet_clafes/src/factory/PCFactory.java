package factory;

import model.PC;

public class PCFactory {
	
	public static PC createPC(String pc_ID) {
		Integer newPCID = Integer.parseInt(pc_ID);
		
		PC newPC = new PC();
		newPC.setPc_ID(newPCID);
		newPC.setPc_condition("Usable");
		
		
		return newPC;
	}
}
