package view.admin;

import java.sql.Date;
import java.util.ArrayList;

import controller.ReportController;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.MainStage;
import model.Report;
import view.MenuAdmin;

public class ViewAllReport {
	private Scene scene;
	private TableView<Report> tv;
	
	public static ViewAllReport getInstance() {
		return new ViewAllReport();
	}
	
	private ViewAllReport() {
		initTable();
	}
	
	public void show() {
		MainStage mainStage = MainStage.getInstance();
		mainStage.getStage().setScene(scene);
		repaint();
	}
	
	private void initTable() {
		VBox cont = new VBox();
		tv = new TableView<Report>();
		
		TableColumn<Report, Integer> id = new TableColumn<>("Report ID");
		id.setCellValueFactory(new PropertyValueFactory<>("report_ID"));
		
		TableColumn<Report, String> role = new TableColumn<>("User Role");
		role.setCellValueFactory(new PropertyValueFactory<>("userRole"));
		
		TableColumn<Report, Integer> pc_id = new TableColumn<>("PC ID");
		pc_id.setCellValueFactory(new PropertyValueFactory<>("pc_ID"));
		
		TableColumn<Report, String> note = new TableColumn<>("Report Note");
		note.setCellValueFactory(new PropertyValueFactory<>("reportNote"));
		
		TableColumn<Report, Date> report = new TableColumn<>("Report Date");
		report.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
		
		tv.getColumns().add(id);
		tv.getColumns().add(role);
		tv.getColumns().add(pc_id);
		tv.getColumns().add(note);
		tv.getColumns().add(report);
		
		cont.getChildren().addAll(MenuAdmin.createMenu(), tv);
		scene = new Scene(cont, 800, 600);
	}
	
	private void repaint() {
		tv.getItems().clear();
		ArrayList<Report> report = ReportController.getAllReportData();
		
		for (Report report2 : report) {
			tv.getItems().add(report2);
		}
	}
}

