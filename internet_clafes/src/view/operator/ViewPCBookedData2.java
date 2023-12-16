package view.operator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import controller.PcBookController;
import helper.Helper;
import javafx.util.Callback;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.MainStage;
import model.PCBook;
import model.User;
import view.MenuOperator;

public class ViewPCBookedData2 {
	
	private static ViewPCBookedData2 pcBookedData;
	private static User user;
	private Scene scene;
	private TableView<PCBook> tv;
	private DatePicker datePicker;
	private Label newPCTitle;
	private TextField newPCInput;
	
	public static ViewPCBookedData2 getInstance(User staff) {
		user = staff;
		return new ViewPCBookedData2();
	}
	
	private ViewPCBookedData2() {
		initTable();
		addEventListener();
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
	
	Button finishButton = new Button("FINISH");
	ArrayList<PCBook> pcBookSelectedList = new ArrayList<PCBook>();
	private void initTable() {
		VBox containerTitle = new VBox(1);
		Label titleInfo = new Label("Click checkbox in the table to 'FINISH' for selected PC (you can also select multiple pc)");
		containerTitle.getChildren().add(titleInfo);
		containerTitle.setAlignment(Pos.TOP_CENTER);
		
		VBox containerHeader = new VBox(2);
	    Label finishLabel = new Label("'FINISH' button to add booking into transaction customer");
	    
	    containerHeader.getChildren().addAll(finishLabel, finishButton);
	    containerHeader.setAlignment(Pos.TOP_RIGHT);
		
	    VBox cont = new VBox();
	    tv = new TableView<>();
	    datePicker = new DatePicker();

	    TableColumn<PCBook, Integer> id = new TableColumn<>("Booking ID");
	    id.setCellValueFactory(new PropertyValueFactory<>("bookID"));

	    TableColumn<PCBook, Integer> idPC = new TableColumn<>("PC ID");
	    idPC.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));

	    TableColumn<PCBook, Integer> idUser = new TableColumn<>("User ID");
	    idUser.setCellValueFactory(new PropertyValueFactory<>("userID"));

	    TableColumn<PCBook, String> date = new TableColumn<>("booking Date");
	    date.setCellValueFactory(new PropertyValueFactory<>("bookedDate"));

	    TableColumn<PCBook, Void> changePCColumn = new TableColumn<>("Change PC");
	    changePCColumn.setCellFactory(param -> new CustomActionCell("Change PC", pcBook -> {
	        try {
	            handleChangePC(pcBook);
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }));

	    Map<Integer, Boolean> selectedStateMap = new HashMap<>();
	    
	    TableColumn<PCBook, Boolean> checkBoxColumn = new TableColumn<>("Select PC");
	    tv.setEditable(true);

	    checkBoxColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
	    checkBoxColumn.setCellValueFactory(param -> {
	        PCBook pcBook = param.getValue();
	        
	        // Create a BooleanProperty for the checkbox
	        BooleanProperty selectedProperty = new SimpleBooleanProperty(selectedStateMap.getOrDefault(pcBook.getBookID(), false));
	        
	        // Add listener to handle changes to the selected property
	        selectedProperty.addListener((observable, oldValue, newValue) -> {
	        	// Update the map with the new selected state
	            selectedStateMap.put(pcBook.getBookID(), newValue);
	            
	            // You can handle the selected PCBook directly here
	            if (newValue) {
	            	pcBookSelectedList.add(pcBook);
	            } else {
	            	pcBookSelectedList.remove(pcBook);
	            }
	        });
	        
	        return selectedProperty;
	    });

	    TableColumn<PCBook, Void> deleteColumn = new TableColumn<>("Actions");
	    deleteColumn.setPrefWidth(200);
	    Callback<TableColumn<PCBook, Void>, TableCell<PCBook, Void>> cellFactory = param -> new TableCell<PCBook, Void>() {
	    	
            private final Button cancelButton = new Button("Cancel");
            
            {
            	cancelButton.setOnAction((ActionEvent event) -> {
                    PCBook pcbook = getTableView().getItems().get(getIndex());
                    try {
						PcBookController.DeleteBookData(pcbook.getBookID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                });
            }

	        @Override
	        public void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setGraphic(null);
	            } else {
	                HBox buttons = new HBox(cancelButton);
	                setGraphic(buttons);
	            }
	        }
	    };
	    deleteColumn.setCellFactory(cellFactory);

	    tv.getColumns().addAll(checkBoxColumn, id, idPC, idUser, date, changePCColumn, deleteColumn);

	    cont.getChildren().addAll(MenuOperator.createMenu(user), containerTitle, containerHeader, tv);

	    scene = new Scene(cont, 800, 600);
	}
	
	private void repaint() throws SQLException {
		tv.getItems().clear();
		ArrayList<PCBook> pcBook = PCBook.getAllPCBookedData();
		
//		if (datePicker.getValue() != null) {
//			java.sql.Date sqlDate = java.sql.Date.valueOf(datePicker.getValue());
//	        pcBook = PCBook.getPCBookedByDate(sqlDate);
//        } else {
//            pcBook = PCBook.getAllPCBookedData();
//        }
		
		for (PCBook pcBook2 : pcBook) {
			tv.getItems().add(pcBook2);
		}
		
	}
	
	private void addEventListener() {
		finishButton.setOnAction(event -> {
            PcBookController.finishBook(pcBookSelectedList, user.getUserID());
	    });
	}
	
	private void handleChangePC(PCBook pcBook) throws SQLException {
	    try {
	        Integer newPCID = Integer.parseInt(newPCInput.getText().trim());
	        PcBookController.assignUsertoNewPC(pcBook.getBookID(), newPCID, pcBook.getBookedDate());
	        repaint(); // Refresh the table after the change
	    } catch (NumberFormatException e) {
	    	Helper.showAlert(AlertType.ERROR, "Invalid PC ID. Please enter a valid number.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Helper.showAlert(AlertType.ERROR, "Error changing PC.");
	    }
	}



//	private void handleDelete(PCBook pcBook) {
//	    LocalDate today = LocalDate.now();
//	    LocalDate bookedDate = pcBook.getBookedDate().toLocalDate();
//
//	    if (bookedDate.isBefore(today)) {
//	        try {
//	            PCBook.deleteBookData(pcBook.getBookID());
//	            repaint(); // Refresh the table after the cancel
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	            Helper.showAlert(AlertType.ERROR, "Error cancel booking.");
//	        }
//	    } else {
//	        try {
//	            // Create a list with a single PCBook object
//	            ArrayList<PCBook> pcBookList = new ArrayList<PCBook>();
//	            pcBookList.add(pcBook);
//
//	            PcBookController.finishBook(pcBookList, user.getUserID());
//	            repaint(); // Refresh the table after the finish
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	            Helper.showAlert(AlertType.ERROR, "Error finish booking.");
//	        }
//	    }
//	}
	
//	private class ButtonCell extends TableCell<PCBook, Void> {
//	    private final Button button;
//	    private final ButtonAction action;
//
//	    ButtonCell(String label, ButtonAction action) {
//	        this.button = new Button(label);
//	        this.action = action;
//	        this.button.setOnAction(event -> action.handle(getCurrentItem()));
//	    }
//
//	    private PCBook getCurrentItem() {
//	        return getTableView().getItems().get(getIndex());
//	    }
//
//	    @Override
//	    protected void updateItem(Void item, boolean empty) {
//	        super.updateItem(item, empty);
//	        if (empty) {
//	            setGraphic(null);
//	        } else {
//	            PCBook pcBook = getCurrentItem();
//	            LocalDate today = LocalDate.now();
//	            LocalDate bookedDate = pcBook.getBookedDate().toLocalDate();
//
//	            // Set button label based on the date
//	            if (bookedDate.isBefore(today)) {
//	                button.setText("Finish");
//	            } else {
//	                button.setText("Cancel");
//	            }
//
//	            setGraphic(button);
//	        }
//	    }
//	}
	
	public class CustomActionCell extends TableCell<PCBook, Void> {
	    private final Button actionButton;
	    private final Consumer<PCBook> action;

	    public CustomActionCell(String label, Consumer<PCBook> action) {
	        this.actionButton = new Button(label);
	        this.action = action;

	        actionButton.setOnAction(event -> {
	            PCBook pcBook = getTableRow().getItem();
	            if (pcBook != null) {
	                action.accept(pcBook);
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


    private interface ButtonAction {
        void handle(PCBook pcBook);
    }
	
}
