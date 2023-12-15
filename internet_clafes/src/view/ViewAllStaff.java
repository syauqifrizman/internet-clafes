package view;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import main.MainStage;
import model.User;


public class ViewAllStaff {

	private static ViewAllStaff viewAllStaff;
	private Scene scene;
	private TableView<User> tv;
	
	public static ViewAllStaff getInstance() {
		return new ViewAllStaff();
	}
	
	private ViewAllStaff() {
		initTable();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
		try {
			repaint();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initTable() {
		VBox cont = new VBox();
		tv = new TableView<User>();
		
		TableColumn<User, Integer> id = new TableColumn<>("User ID");
		id.setCellValueFactory(new PropertyValueFactory<>("userID"));
		
		TableColumn<User, String> username = new TableColumn<>("Username");
		username.setCellValueFactory(new PropertyValueFactory<>("userName"));
		
		TableColumn<User, String> pass = new TableColumn<>("Password");
		pass.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
		
		TableColumn<User, Integer> age = new TableColumn<>("Age");
		age.setCellValueFactory(new PropertyValueFactory<>("userAge"));
		
		TableColumn<User, String> role = new TableColumn<>("Role");
		role.setCellValueFactory(new PropertyValueFactory<>("userRole"));
		
		tv.getColumns().addAll(id, username, pass, age, role);
		
		cont.getChildren().addAll(MenuAdmin.createMenu(), tv);
		
		scene = new Scene(cont, 800, 600);
    }
	
	private void repaint() throws SQLException {
	    tv.getItems().clear();
	    ArrayList<User> users = User.getAllUserData();
	    
	    for (User user : users) {
	        if ("Admin".equals(user.getUserRole()) 
	                || "Operator".equals(user.getUserRole()) 
	                || "ComputerTechnician".equals(user.getUserRole())) {
	            tv.getItems().add(user);
	        }
	    }
	}
}
