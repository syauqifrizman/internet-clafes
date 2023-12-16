package view;

import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import model.UserSession;
import view.login_register.Login;

public class MenuComputerTechnician {

	//memunculkan menu bar khusus Computer Technician
	public static Parent createMenu() {
        // Create a menu bar
        MenuBar menuBar = new MenuBar();

        // Create an Actions menu with menu items
        Menu mainMenu = new Menu("Menu");
        
        // "Home" menu item
        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> navigateToHome());

        // "View Technician Job" menu item
        MenuItem technicianJobMenuItem = new MenuItem("Technician Job");
        technicianJobMenuItem.setOnAction(e -> navigateToTechnicianJob());

        // "Logout" menu item
        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> handleLogout());

        // Add menu items to the Actions menu
        mainMenu.getItems().addAll(homeMenuItem, technicianJobMenuItem, logoutMenuItem);

        // Add the Actions menu to the menu bar
        menuBar.getMenus().add(mainMenu);

        // return menubar
        return menuBar;
    }
    
    private static void navigateToHome() {
        // Implement logic to navigate to the "View All PC" page
    	ViewPC viewpc = ViewPC.getInstance();
		viewpc.show();
    }

    private static void navigateToTechnicianJob() {
        // Implement logic to navigate to the "View Job" page
    	ViewJob viewjob = ViewJob.getInstance();
        viewjob.show();
    }
    

    private static void handleLogout() {
        // Add logic for handling logout
    	UserSession.logout();
    	Login loginPage = new Login();
		loginPage.show();
    }

}
