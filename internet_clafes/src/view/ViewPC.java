package view;

import java.util.ArrayList;

import controller.JobController;
import helper.Helper;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import main.MainStage;
import model.User;
import model.PC;
import model.UserSession;
import repository.PCRepository;
import view.admin.AddPC;
import view.admin.ViewPCDetail;
import view.customer.BookPC;

public class ViewPC {
	private Scene scene;
	private TableView<PC> tv;
	//private Button bookButton, reportButton;
	
	public static ViewPC getInstance() {
		return new ViewPC();
	}
	
	public ViewPC() {
		initTable();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
		repaint();
	}
	
	private void initTable() {
		String usernameLogin = UserSession.getCurrentUsername();
		String userRoleLogin = UserSession.getCurrentUserRole();
		
		HBox containerUsername = new HBox();
		Label usernameLabel = new Label("Login as: ");
		Label usernameLoginLabel = new Label(usernameLogin);
		usernameLoginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		containerUsername.getChildren().addAll(usernameLabel, usernameLoginLabel);
		
		
		HBox containerUserRole = new HBox();
		Label UserRoleLabel = new Label("User Role as: ");
		Label userRoleLoginLabel = new Label(userRoleLogin);
		userRoleLoginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		containerUserRole.getChildren().addAll(UserRoleLabel, userRoleLoginLabel);
		
		
		VBox containerHeader = new VBox(4);
		containerHeader.getChildren().addAll(containerUsername, containerUserRole);
		
		VBox cont = new VBox();
		
		tv = new TableView<PC>();
		
		TableColumn<PC, Integer> id = new TableColumn<>("PC ID");
		id.setPrefWidth(50);
		id.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));
		
		TableColumn<PC, Integer> cond = new TableColumn<>("PC Condition");
		cond.setCellValueFactory(new PropertyValueFactory<>("pc_condition"));
		
		TableColumn<PC, Void> action = new TableColumn<>("Actions");
		action.setPrefWidth(270);
		
		Callback<TableColumn<PC, Void>, TableCell<PC, Void>> cellFactory = new Callback<TableColumn<PC, Void>, TableCell<PC, Void>>() {
		    @Override
		    public TableCell<PC, Void> call(final TableColumn<PC, Void> param) {
		        final TableCell<PC, Void> cell = new TableCell<PC, Void>() {

		            private final Button reportButton = new Button("Report");
		            private final Button bookButton = new Button("Book");
		            private final Button detailButton = new Button("Detail");
		            private final Button addJobButton = new Button("Fix (Add Job)");

		            {
		                
		                
		                if (UserSession.getCurrentUserRole().equals("Customer") || UserSession.getCurrentUserRole().equals("Operator")) {
		                    reportButton.setOnAction((ActionEvent event) -> {
		                        User getUser = UserSession.getCurrentUser();
		                        PC getPC = getTableView().getItems().get(getIndex());
		                        ReportPC reportpc = ReportPC.getInstance(getUser, getPC);
		                        reportpc.show();
		                    });
		                }

		                if (UserSession.getCurrentUserRole().equals("Customer")) {
		                    bookButton.setOnAction((ActionEvent event) -> {
		                        User getUser = UserSession.getCurrentUser();
		                        PC getPC = getTableView().getItems().get(getIndex());
		                        BookPC bookpc = BookPC.getInstance(getUser, getPC);
		                        bookpc.show();
		                    });
		                }
		                
		                if (UserSession.getCurrentUserRole().equals("Admin")) {
		                    addJobButton.setOnAction((ActionEvent event) -> {
		                        User getUser = UserSession.getCurrentUser();
		                        PC getPC = getTableView().getItems().get(getIndex());
		                        JobController.addNewJob(getUser.getUserID().toString(), getPC.getPc_ID().toString());
		                        ViewJob viewjob = ViewJob.getInstance();
		                        viewjob.show();
		                    });
		                    
		                    detailButton.setOnAction((ActionEvent event) -> {
		                        PC getPC = getTableView().getItems().get(getIndex());
			                    ViewPCDetail viewPCDetail = ViewPCDetail.getInstance(getPC);
			                    viewPCDetail.show();
			                });
		                }
		            }

		            @Override
		            public void updateItem(Void item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty) {
		                    setGraphic(null);
		                } else {
		                    HBox containerButtons = new HBox();
		           
		                    if (UserSession.getCurrentUserRole().equals("Customer")) {
		                    	containerButtons.getChildren().addAll(reportButton, bookButton);
		                    }
		                    else if(UserSession.getCurrentUserRole().equals("Operator")) {
		                    	containerButtons.getChildren().add(reportButton);
		                    }
		                    
		                    else if(UserSession.getCurrentUserRole().equals("Admin")) {
		                    	containerButtons.getChildren().add(addJobButton);
		                    	containerButtons.getChildren().add(detailButton);
		                    }
		                    
		                    setGraphic(containerButtons);
		                }
		            }
		        };
		        return cell;
		    }
		};
		action.setCellFactory(cellFactory);
	        
		tv.getColumns().add(id);
		tv.getColumns().add(cond);
		tv.getColumns().add(action);
		
		Button addPCButton = new Button("ADD NEW PC");
		VBox containerHeadButton = new VBox();
		containerHeadButton.getChildren().add(addPCButton);
		
		containerHeadButton.setAlignment(Pos.TOP_RIGHT);
		
		addPCButton.setOnMouseClicked(e ->{
			AddPC.getInstance().show();
		});
		
		switch(UserSession.getCurrentUserRole()) {
			case "Customer":
				cont.getChildren().addAll(MenuCustomer.createMenu(), containerHeader, tv);
				break;
			case "Computer Technician":
				cont.getChildren().addAll(MenuComputerTechnician.createMenu(), containerHeader, tv);
				break;
			case "Operator":
				cont.getChildren().addAll(MenuOperator.createMenu(UserSession.getCurrentUser()), containerHeader, tv);
				break;
			case "Admin":
				cont.getChildren().addAll(MenuAdmin.createMenu(), containerHeader, containerHeadButton, tv);
				break;
			default:
				Helper.showAlert(AlertType.ERROR, "Invalid role");
		}
		
		scene = new Scene(cont, 800, 600);
    }
	
	private void repaint() {
		tv.getItems().clear();
		ArrayList<PC> pc = PCRepository.getAllPCData();
		
		for (PC pc2 : pc) {
			tv.getItems().add(pc2);
		}
	}
	
//	private void custButtons(TableColumn<PC, Void> action) {
//		action.setCellFactory(param -> new TableCell<>() {
//			private final Button bookButton = new Button("Book this PC");
//			private final Button reportButton = new Button("Report this PC");
//			
//			{
//				reportButton.setOnAction(event -> {
//					User reportUser = UserSession.getCurrentUser();
//					PC reportPC = getTableView().getItems().get(getIndex());
//					ReportPC reportpc = ReportPC.getInstance(reportUser, reportPC);
//					reportpc.show();
//					
//				});
//			}
//		});
//	}

}
