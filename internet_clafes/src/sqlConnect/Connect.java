package sqlConnect;

import java.sql.*;

public final class Connect {
	
	private final String USERNAME = "root"; // change with your MySQL username, the default username is 'root'
	private final String PASSWORD = ""; // change with your MySQL password, the default password is empty
	private final String DATABASE = "internet_clafes"; // change with the database name that you use
	private final String HOST = "localhost:3306"; // change with your MySQL host, the default port is 3306
	private final String CONECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection connection;
	private Statement statement;
	private static Connect connect;
	
//  singleton design pattern
    private Connect() {
    	try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CONECTION, USERNAME, PASSWORD);  
            statement = connection.createStatement(); 
        } catch(Exception e) {
        	e.printStackTrace();
        	System.out.println("Failed to connect the database");
        	System.exit(0);
        }  
    }
    
    public static synchronized Connect getConnection() {
    	if(connect == null) {
    		return connect = new Connect();
    	}
//		return connect = (connect == null) ? new Connect() : connect;
    	return connect;
    }

//  use this method for query SELECT
    public ResultSet executeQuery(String query) {
        ResultSet res = null;
    	try {
    		res = statement.executeQuery(query);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return res;
    }

//  use this method for query INSERT, UPDATE, DELETE
    public void executeUpdate(String query) {
    	try {
    		statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
//  use this method for query SELECT, INSERT, UPDATE, DELETE
    public PreparedStatement prepareStatement(String query) {
    	PreparedStatement ps = null;
    	try {
			ps = connection.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ps;
    }
}
