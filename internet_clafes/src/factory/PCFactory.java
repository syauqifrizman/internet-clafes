package factory;

import model.PC;

public class PCFactory {
	
	//method untuk membuat object PC baru
	public static PC createPC(String pc_ID) {
		Integer newPCID = Integer.parseInt(pc_ID);
		
		//buat object PC baru
		PC newPC = new PC();
		
		//masukkan data yang diinginkan ke object baru
		newPC.setPc_ID(newPCID);
		newPC.setPc_condition("Usable");
		
		//return object baru yang sudah dibuat
		return newPC;
	}
}
