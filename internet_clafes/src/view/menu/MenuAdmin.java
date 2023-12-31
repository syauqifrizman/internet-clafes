package view.menu;

import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import view.admin.ViewAllReport;
import view.admin.ViewAllStaff;
import view.admin.ViewAllStaffJob;
import view.admin.ViewAllTransaction;

public class MenuAdmin extends UserMenu{

	//memunculkan menu bar khusus admin
	public static Parent createMenu() {
        // Create a menu bar
        MenuBar menuBar = new MenuBar();

        // Create an Actions menu with menu items
        Menu mainMenu = new Menu("Menu");
        
        // "Home" menu item
        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> navigateToHome());

        // "View All Staff" menu item
        MenuItem viewAllStaffMenuItem = new MenuItem("View All Staff");
        viewAllStaffMenuItem.setOnAction(e -> navigateToViewAllStaff());
        
        // "Job Management" menu item
        MenuItem jobManagementMenuItem = new MenuItem("View All Staff Job");
        jobManagementMenuItem.setOnAction(e -> navigateToJobManagement());
        
        // "All Transaction History" menu item
        MenuItem allTransactionHistoryMenuItem = new MenuItem("All Transaction History");
        allTransactionHistoryMenuItem.setOnAction(e -> navigateToAllTransactionHistory());
        
        // "View All Report" menu item
        MenuItem viewAllReportMenuItem = new MenuItem("View All Report");
        viewAllReportMenuItem.setOnAction(e -> navigateToViewAllReport());

        // "Logout" menu item
        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> handleLogout());

        // Add menu items to the Actions menu
        mainMenu.getItems().addAll(
        		homeMenuItem, 
        		viewAllStaffMenuItem,
        		jobManagementMenuItem, 
        		allTransactionHistoryMenuItem,
        		viewAllReportMenuItem,
        		logoutMenuItem
        	);

        // Add the Actions menu to the menu bar
        menuBar.getMenus().add(mainMenu);

        // return menubar
        return menuBar;
    }
    
	private static void navigateToViewAllStaff() {
		// Implement logic to navigate to the "View All Staff" page
    	ViewAllStaff viewStaff = ViewAllStaff.getInstance();
    	viewStaff.show();
    }
	
	private static void navigateToJobManagement() {
		// Implement logic to navigate to the "View Job" page
		ViewAllStaffJob viewjob = ViewAllStaffJob.getInstance();
        viewjob.show();
    }
	
	private static void navigateToAllTransactionHistory() {
        // Implement logic to navigate to the "Transaction History" page
		ViewAllTransaction alltHistory = ViewAllTransaction.getInstance();
		alltHistory.show();
    }
    
	private static void navigateToViewAllReport() {
		// Implement logic to navigate to the "View All Report" page
		ViewAllReport viewAllReport = ViewAllReport.getInstance();
		viewAllReport.show();
    }

}
