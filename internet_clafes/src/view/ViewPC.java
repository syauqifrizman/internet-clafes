package view;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.MainStage;
import model.User;
import model.PC;
import model.UserSession;
import repository.PCRepository;

public class ViewPC implements IView{
	private static ViewPC viewpc;
	private static IView iview;
	private Scene scene;
	private TableView<PC> tv;
	//private Button bookButton, reportButton;
	
	public static ViewPC getInstance() {
		return new ViewPC();
	}
	
	private ViewPC() {
		initTable();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
		repaint();
	}
	
	private void initTable() {
		VBox cont = new VBox();
		tv = new TableView<PC>();
		
		TableColumn<PC, Integer> id = new TableColumn<>("PC ID");
		id.setPrefWidth(50);
		id.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));
		
		TableColumn<PC, Integer> cond = new TableColumn<>("PC Condition");
		cond.setCellValueFactory(new PropertyValueFactory<>("pc_condition"));
		
		TableColumn<PC, Void> action = new TableColumn<>("Actions");
		action.setPrefWidth(270);
		if(UserSession.getCurrentUserRole().equals("Customer")) {
		 Callback<TableColumn<PC, Void>, TableCell<PC, Void>> cellFactory = new Callback<TableColumn<PC, Void>, TableCell<PC, Void>>() {
	            @Override
	            public TableCell<PC, Void> call(final TableColumn<PC, Void> param) {
	                final TableCell<PC, Void> cell = new TableCell<PC, Void>() {

	                    private final Button reportButton = new Button("Report");
	                    private final Button bookButton = new Button("Book");
	                    
	                    {
	                        reportButton.setOnAction((ActionEvent event) -> {
	                        	User reportUser = UserSession.getCurrentUser();
	                            PC reportPC = getTableView().getItems().get(getIndex());
	                            ReportPC reportpc = ReportPC.getInstance(reportUser, reportPC);
	    						reportpc.show();
	                        });
	                    }       
	                    @Override
	                    public void updateItem(Void item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (empty) {
	                            setGraphic(null);
	                        } else {
	                        	HBox buttons = new HBox(reportButton, bookButton);
	         	                setGraphic(buttons);
	                        }
	                    }
	                };
	                return cell;
	            }
	        };
	        action.setCellFactory(cellFactory);
		}    
	        
        
		
		tv.getColumns().add(id);
		tv.getColumns().add(cond);
		tv.getColumns().add(action);
		
		switch(UserSession.getCurrentUserRole()) {
			case "Customer":
				cont.getChildren().addAll(MenuCustomer.createMenu(), tv);
				break;
			case "Computer Technician":
				cont.getChildren().addAll(MenuComputerTechnician.createMenu(), tv);
				break;
			case "Operator":
				cont.getChildren().addAll(MenuOperator.createMenu(), tv);
				break;
			case "Admin":
				cont.getChildren().addAll(MenuAdmin.createMenu(), tv);
				break;
			default:
	            showError("Invalid role");
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
	
	@Override
	public void showError(String error) {
		// TODO Auto-generated method stub
		
	}

}
