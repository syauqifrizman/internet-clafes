package view.admin;

import java.sql.SQLException;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.MainStage;
import model.User;
import view.menu.HeaderLayout;
import view.menu.MenuAdmin;

public class ChangeRole extends HeaderLayout{
	private static User user;
	Stage primaryStage;
	Scene scene;
	Button ChangeRoleButton, BackButton;
	private TextField newRoleInput;
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
	}
	
	public static void setScene(Stage primaryStage) {
		ChangeRole changeRole = new ChangeRole(user);
		changeRole._setScene(primaryStage);
	}
	
	public static void setScene(Stage primaryStage, User user) {
		setScene(primaryStage);
	}
	
	public ChangeRole(User user) {
		initialize(user);
		addEventListener(user);
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
	}
	
	//method untuk 'menggambar' label, text field, dkk
	private void initialize(User user) {
		VBox containerHeader = getUserHeader();
		
	    VBox container = new VBox(20);  // Set vertical spacings between elements
	    container.setPadding(new Insets(50, 20, 20, 20));  // Set padding

	    // Title
	    Label pageTitle = new Label("About The Staff");
	    pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    
	    // User ID
	    Label userIDLabel = new Label("User ID: ");
	    Label currentUserID = new Label(user.getUserID().toString());
	    currentUserID.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerUserID = new HBox();
	    containerUserID.getChildren().addAll(userIDLabel, currentUserID);
	    
	    //  Username
	    Label usernameLabel = new Label("Username: ");
	    Label currentUsername = new Label(user.getUserName());
	    currentUsername.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerUsername = new HBox();
	    containerUsername.getChildren().addAll(usernameLabel, currentUsername);
	    
	    //  Role
	    Label roleLabel = new Label("Role: ");
	    Label currentRole = new Label(user.getUserRole());
	    currentRole.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    HBox containerRole = new HBox();
	    containerRole.getChildren().addAll(roleLabel, currentRole);
	    
	    //  New Role
	    Label newRoleLabel = new Label("New Role: ");
	    newRoleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
	    
	    //Text field untuk memasukkan Role baru
	    newRoleInput = new TextField();
		newRoleInput.setPromptText("Input New Role for this user");
	    
	    HBox containerNewRole = new HBox();
	    containerNewRole.getChildren().addAll(newRoleLabel, newRoleInput);

	    // Add Button
	    ChangeRoleButton = new Button("Change Role");
	    BackButton = new Button("Back");
	    
	    HBox containerButton = new HBox();
	    containerButton.getChildren().addAll(ChangeRoleButton, BackButton);

	    // Add elements to the container
	    container.getChildren().addAll(MenuAdmin.createMenu(), containerHeader, pageTitle, containerUserID, containerUsername,containerRole, containerNewRole,  containerButton);

	    // Align container to the top left
	    container.setAlignment(Pos.TOP_LEFT);

	    // Create the scene
	    scene = new Scene(container, 800, 600);
	}

	//method fungsionalitas dari Change Role button
	private void addEventListener(User user) {
		
		//saat diklik, role dari User akan langsung diganti
		ChangeRoleButton.setOnMouseClicked(e ->{
			String newRole = newRoleInput.getText();
			UserController.changeUserRole(user.getUserID(), newRole);
			
			try {
				//setelah role diganti, laman akan di refresh sehingga memunculkan data yang baru
				User newUser = UserController.getUserData(user.getUserName(), user.getUserPassword());
				ChangeRole changeRole = ChangeRole.getInstance(newUser);
				changeRole.show();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		});
		
		//button agar bisa kembali ke laman View Staff
		BackButton.setOnMouseClicked(e ->{
			ViewAllStaff viewStaff = ViewAllStaff.getInstance();
	    	viewStaff.show();
		});
		
	}
	
    public static ChangeRole getInstance(User fromViewAllStaff) {
    	return new ChangeRole(fromViewAllStaff);    	
    }
}
