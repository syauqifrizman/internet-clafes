package view;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.MainStage;
import model.PC;
import repository.PCRepository;

public class ViewPC implements IView{
	private static ViewPC viewpc;
	private static IView iview;
	private Scene scene;
	private TableView<PC> tv;
	
	public static ViewPC getInstance() {
		return viewpc = viewpc == null ? new ViewPC() : viewpc;
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
		id.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));
		
		TableColumn<PC, Integer> cond = new TableColumn<>("PC Condition");
		cond.setCellValueFactory(new PropertyValueFactory<>("pc_condition"));
		
		tv.getColumns().add(id);
		tv.getColumns().add(cond);
		
		cont.getChildren().add(tv);
		scene = new Scene(cont, 800, 600);
	}
	
	private void repaint() {
		tv.getItems().clear();
		ArrayList<PC> pc = PCRepository.getAllPCData();
		
		for (PC pc2 : pc) {
			tv.getItems().add(pc2);
		}
	}
	
	
	@Override
	public void showError(String error) {
		// TODO Auto-generated method stub
		
	}

}
