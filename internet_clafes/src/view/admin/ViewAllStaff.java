package view.admin;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

import controller.UserController;
import main.MainStage;
import model.User;

import view.menu.MenuAdmin;



public class ViewAllStaff {

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
			//method untuk 'refresh' table data
			repaint();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initTable() {
		VBox cont = new VBox();
		
		//membuat table data baru
		tv = new TableView<User>();
		
		//mengisi kolom2 table data nya
		TableColumn<User, Integer> id = new TableColumn<>("User ID");
		id.setCellValueFactory(new PropertyValueFactory<>("userID"));
		
		TableColumn<User, String> username = new TableColumn<>("Username");
		username.setCellValueFactory(new PropertyValueFactory<>("userName"));
		
		TableColumn<User, Integer> age = new TableColumn<>("Age");
		age.setCellValueFactory(new PropertyValueFactory<>("userAge"));
		
		TableColumn<User, String> role = new TableColumn<>("Role");
		role.setCellValueFactory(new PropertyValueFactory<>("userRole"));
		
		//membuat kolom yang isinya tombol Change Role
		TableColumn<User, Void> cRoleColumn = new TableColumn<>("Change Role");
		cRoleColumn.setCellFactory(param -> new ButtonCell("Change Role", t -> {
			try {
				//memanggil method handleChange
				handleChange(t);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}));
		
		//memasukkan kolom2 tadi ke tableview
		tv.getColumns().addAll(id, username, age, role, cRoleColumn);
		
		//memasukkan item2 ke vbox
		cont.getChildren().addAll(MenuAdmin.createMenu(), tv);
		
		scene = new Scene(cont, 800, 600);
    }
	
	//method yang mengarahkan user ke laman ChangeRole
	private void handleChange(User user) throws SQLException {
		ChangeRole changeRole = ChangeRole.getInstance(user);
		changeRole.show();
	}
	
	//method untuk me-refresh table
	private void repaint() throws SQLException {
		//clear dulu isi dari tabel
	    tv.getItems().clear();
	    
	    //mengambil data dari database ke sebuah arraylist
	    ArrayList<User> users = UserController.getAllUserData();
	    
	    //memilih data User yang role nya Admin, Operator, atau Computer Technician
	    for (User user : users) {
	        if ("Admin".equals(user.getUserRole()) 
	                || "Operator".equals(user.getUserRole()) 
	                || "Computer Technician".equals(user.getUserRole())) {
	        	
	        	//masukkan ke table
	            tv.getItems().add(user);
	        }
	    }
	}
	
	//method untuk mengambil User dari row yang dipilih untuk Change Role
	public class ButtonCell extends TableCell<User, Void> {
        private final Button actionButton;

        public ButtonCell(String label, Consumer<User> action) {
            this.actionButton = new Button(label);

            actionButton.setOnAction(event -> {
            	
            	//mengambil data dari row
                User user = getTableRow().getItem();
                if (user != null) {
                    action.accept(user);
                }
            });
        }
        
        //memunculkan button di table 
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(actionButton);
            }
        }
    }
}
