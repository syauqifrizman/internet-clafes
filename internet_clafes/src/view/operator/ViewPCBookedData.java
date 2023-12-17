package view.operator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import controller.PcBookController;
import controller.UserSessionController;
import helper.Helper;
import javafx.util.Callback;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import view.menu.HeaderLayout;
import view.menu.MenuOperator;

public class ViewPCBookedData extends HeaderLayout{
	
	private static User user;
	private Scene scene;
	private TableView<PCBook> tv;
	private Label newPCTitle;
	private TextField newPCInput;
	
	public static ViewPCBookedData getInstance(User staff) {
		user = staff;
		return new ViewPCBookedData();
	}
	
	private ViewPCBookedData() {
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
		VBox containerHeader = getUserHeader();
		
		VBox containerTitle = new VBox(1);
		
		//pembuatan label
		Label titleInfo = new Label("Click checkbox in the table to 'FINISH' for selected PC (you can also select multiple pc)");
		containerTitle.getChildren().add(titleInfo);
		containerTitle.setAlignment(Pos.TOP_RIGHT);
		
		VBox containerDesc = new VBox(2);
	    Label finishLabel = new Label("'FINISH' button to add booking into transaction customer");
	    
	    containerDesc.getChildren().addAll(finishLabel, finishButton);
	    containerDesc.setAlignment(Pos.TOP_RIGHT);
		
	    VBox cont = new VBox();
	    tv = new TableView<>();//membuat tabel baru

	    //mengisi tabel dengan kolom2
	    TableColumn<PCBook, Integer> id = new TableColumn<>("Booking ID");
	    id.setCellValueFactory(new PropertyValueFactory<>("bookID"));

	    TableColumn<PCBook, Integer> idPC = new TableColumn<>("PC ID");
	    idPC.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));

	    TableColumn<PCBook, Integer> idUser = new TableColumn<>("User ID");
	    idUser.setCellValueFactory(new PropertyValueFactory<>("userID"));

	    TableColumn<PCBook, String> date = new TableColumn<>("booking Date");
	    date.setCellValueFactory(new PropertyValueFactory<>("bookedDate"));

	    //membuat kolom yang isinya button Change PC
	    TableColumn<PCBook, Void> changePCColumn = new TableColumn<>("Change PC");
	    changePCColumn.setCellFactory(param -> new CustomActionCell("Change PC", pcBook -> {
	        try {
	            handleChangePC(pcBook); //jika dipencet, akan menjalankan method handleChangePC()
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }));

	    Map<Integer, Boolean> selectedStateMap = new HashMap<>();
	    
	    TableColumn<PCBook, Boolean> checkBoxColumn = new TableColumn<>("Select PC");
	    tv.setEditable(true);

	    //untuk funsionalitas checklist
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
	            	//kalau di ceklis, dimasukin ke arraylist sementara
	            	pcBookSelectedList.add(pcBook);
	            } else {
	            	//kalau di uncheck, dikeluarin dari arraylist
	            	pcBookSelectedList.remove(pcBook);
	            }
	        });
	        
	        return selectedProperty;
	    });

	  //membuat kolom yang isinya button Cancel
	    TableColumn<PCBook, Void> deleteColumn = new TableColumn<>("Actions");
	    deleteColumn.setPrefWidth(200);
	    Callback<TableColumn<PCBook, Void>, TableCell<PCBook, Void>> cellFactory = param -> new TableCell<PCBook, Void>() {
	    	
            private final Button cancelButton = new Button("Cancel");//deklarasi Button
            
            {
            	cancelButton.setOnAction((ActionEvent event) -> {
            		//saat dipencet, booking di row tsb akan didelete/cancel
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

	    //memasukkan kolom2 ke table
	    tv.getColumns().addAll(checkBoxColumn, id, idPC, idUser, date, changePCColumn, deleteColumn);
	    
		newPCTitle = new Label("New PC ID: ");
		newPCInput = new TextField();
		newPCInput.setPromptText("Input New PC ID for this user");

		HBox additionalControls = new HBox(newPCTitle, newPCInput);

	    cont.getChildren().addAll(MenuOperator.createMenu(UserSessionController.getCurrentUser()), containerHeader, containerTitle, containerDesc, additionalControls, tv);

	    scene = new Scene(cont, 800, 600);
	}
	
	//method utk refresh data
	private void repaint() throws SQLException {
		tv.getItems().clear();
		
		//mengambil data dengan memanggil controller dan memasukannya ke arraylist
		ArrayList<PCBook> pcBook = PcBookController.getAllPCBookedData();
		
		for (PCBook pcBook2 : pcBook) {
			//memasukkan data dari arraylist ke tabel
			tv.getItems().add(pcBook2);
		}
		
	}
	
	private void addEventListener() {
		finishButton.setOnAction(event -> {
			//kalau Finish button diklik, akan memanggil method finishbook dari controller
            PcBookController.finishBook(pcBookSelectedList, user.getUserID());
	        try {
				repaint(); //refresh
			} catch (SQLException e) {
				e.printStackTrace();
			} 
	    });
	}
	
	//method utk change PC
	private void handleChangePC(PCBook pcBook) throws SQLException {
	    try {
	    	//mendapatkan ID dari object
	        Integer newPCID = Integer.parseInt(newPCInput.getText().trim());
	        
	        //id digunakan untuk meng-assign user ke PC baru lewat controller
	        PcBookController.assignUsertoNewPC(pcBook.getBookID(), newPCID, pcBook.getBookedDate());
	        repaint(); // Refresh the table after the change
	    } catch (NumberFormatException e) {
	    	Helper.showAlert(AlertType.ERROR, "Invalid PC ID. Please enter a valid number.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Helper.showAlert(AlertType.ERROR, "Error changing PC.");
	    }
	}
	
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
	
}
