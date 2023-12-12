package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.PC;
import sqlConnect.Connect;

public class PCRepository {
	
	public static ArrayList<PC> getAllPCData(){
		ArrayList<PC> pcList = new ArrayList<PC>();
		Connect db = Connect.getConnection();
		
		String queryExecute = "SELECT * FROM pcs";
		ResultSet res = db.executeQuery(queryExecute);
		
//		pcList = null;
		try {
			while(res.next()) {
				Integer currPC_ID = res.getInt(1);
				String currPC_condition = res.getString(2);
				
				PC currPC = new PC(currPC_ID, currPC_condition);
				pcList.add(currPC);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pcList;
		
	}
	
	public static String updatePCCondition(String pc_ID, String pc_condition) {
		Integer curr_pc_ID = Integer.parseInt(pc_ID);
		
		Connect db = Connect.getConnection();
		
		String query = "UPDATE pcs SET pc_condition = '%s' WHERE pc_ID = '%d'";
		String queryExecute = String.format(query, pc_condition, curr_pc_ID);
		
		db.executeUpdate(queryExecute);
		
		return "Success updated the PC condition!";
	}
	
//	public static String generatePCID() {
//		// TODO Auto-generated method stub
//		Connect connect = Connect.getConnection();
//		String query = "SELECT * FROM pcs ORDER BY pc_ID DESC LIMIT 1";
//		ResultSet rs = connect.executeQuery(query);
//		
//		String nextID = "PC001";
//		
//		try {
//			if(rs.next()) {
//				String lastID = rs.getString("pc_ID");
//				String lastDigitID = lastID.substring(2);
//				Integer lastDigitIDInt = Integer.parseInt(lastDigitID);
//				nextID = "PC" +String.format("%03d", lastDigitIDInt + 1);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return nextID;
//		
//	}
	
	public static String deleteNewPC(String pc_ID) {
		Integer curr_pc_ID = Integer.parseInt(pc_ID);
		
		Connect db = Connect.getConnection();
		
		String query = "DELETE FROM pcs WHERE pc_ID = '%d'";
		String queryExecute = String.format(query, curr_pc_ID);
		
		db.executeUpdate(queryExecute);
		
		return "Success remove the PC ID: " + pc_ID;
	}
	
	public static String insertNewPC(PC newPC) {
		Connect db = Connect.getConnection();
		
		String query = "INSERT INTO pcs VALUES ('%d', %s')";
		String queryExecute = String.format(query, newPC.getPc_ID(), newPC.getPc_condition());
		
		db.executeUpdate(queryExecute);
		
		return "Success add new PC!";
	}
	
	public static PC getPCDetail(String pc_ID) {
		Integer curr_pc_ID = Integer.parseInt(pc_ID);
		
		Connect db = Connect.getConnection();
		
		String query = "SELECT * FROM pcs WHERE pc_ID = '%d'";
		String queryExecute = String.format(query, curr_pc_ID);
		
		ResultSet res = db.executeQuery(queryExecute);
		
		PC getPC = null;
		try {
			while(res.next()) {
				Integer currPC_ID = res.getInt(1);
				String currPC_condition = res.getString(2);
				
				getPC = new PC(currPC_ID, currPC_condition);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getPC;
	}
}
