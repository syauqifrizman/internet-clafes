package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

import helper.Helper;
import main.MainStage;
import model.User;


public class ViewAllStaff {

	private static ViewAllStaff viewAllStaff;
	private Scene scene;
	private TableView<User> tv;
	private Label newRoleLabel;
	private TextField newRoleInput;
//	private Button changeRoleButton;
	
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
		
//		Button changeRoleButton = new Button("Change Role");
		
		TableColumn<User, Void> cRoleColumn = new TableColumn<>("Change Role");
		cRoleColumn.setCellFactory(param -> new ButtonCell("Change Role", t -> {
			try {
				handleChange(t);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}));
		
		tv.getColumns().addAll(id, username, pass, age, role, cRoleColumn);
		
		newRoleLabel = new Label("New Role: ");
		newRoleInput = new TextField();
		newRoleInput.setPromptText("Input New Role for this user");

		HBox additionalControls = new HBox(newRoleLabel, newRoleInput);

		
		cont.getChildren().addAll(MenuAdmin.createMenu(),additionalControls, tv);
		
		scene = new Scene(cont, 800, 600);
    }
	
	private void handleChange(User user) throws SQLException {
	    try {
	        String newRole = newRoleInput.getText().trim();
	        User.ChangeUserRole(user.getUserID(), newRole);
	        repaint(); // Refresh the table after the change
	    } catch (NumberFormatException e) {
	    	Helper.showAlert(AlertType.ERROR, "Invalid Role. Please enter a valid role.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Helper.showAlert(AlertType.ERROR, "Error changing Role.");
	    }
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
	
	public class ButtonCell extends TableCell<User, Void> {
        private final Button actionButton;

        public ButtonCell(String label, Consumer<User> action) {
            this.actionButton = new Button(label);

            actionButton.setOnAction(event -> {
                User user = getTableRow().getItem();
                if (user != null) {
                    action.accept(user);
                }
            });
        }

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
