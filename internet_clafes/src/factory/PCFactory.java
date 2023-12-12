package factory;

import model.PC;

public class PCFactory {
	
	public static PC createPC(String pc_ID) {
		PC newPC = new PC();
		
		newPC.setPc_ID(pc_ID);
		newPC.setPc_condition("Usable");
		
		
		return newPC;
	}
}
