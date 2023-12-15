package repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import sqlConnect.Connect;

public class UserRepository {
	public static User getUserDetail(Integer userID) {
		Connect db = Connect.getConnection();
		
		String query = "SELECT * FROM user WHERE userID = '%d'";
		String queryExecute = String.format(query, userID);
		
		ResultSet res = db.executeQuery(queryExecute);
		
		User getUser = null; 
		try {
			while(res.next()) {
				Integer currUserID = res.getInt(1);
				String currUsername = res.getString(2);
//				String currUserPassword = res.getString(3);
				Integer currUserAge = res.getInt(4);
				String currUserRole = res.getString(5);
				
				getUser = new User(currUserID, currUsername, currUserAge, currUserRole);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getUser;
	}
}
