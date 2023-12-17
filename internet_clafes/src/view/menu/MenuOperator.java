package view.menu;

import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import model.User;
import controller.UserSessionController;
import view.ViewPC;
import view.login_register.Login;
import view.operator.ViewPCBookedData;

public class MenuOperator {
	public static User user;

	//memunculkan menu bar khusus Operator
	public static Parent createMenu(User staff) {
		user = staff;
        // Create a menu bar
        MenuBar menuBar = new MenuBar();

        // Create an Actions menu with menu items
        Menu mainMenu = new Menu("Menu");
        
        // "Home" menu item
        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> navigateToHome());

        // "PC Booked Data" menu item
        MenuItem PCBookedDataMenuItem = new MenuItem("PC Booked Data");
        PCBookedDataMenuItem.setOnAction(e -> navigateToPCBookedData());

        // "Logout" menu item
        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> handleLogout());

        // Add menu items to the Actions menu
        mainMenu.getItems().addAll(homeMenuItem, PCBookedDataMenuItem, logoutMenuItem);

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

    private static void navigateToPCBookedData() {
        // Implement logic to navigate to the "View PC Booked Data" page
    	User staffBook = user; 
    	ViewPCBookedData pcBookedData = ViewPCBookedData.getInstance(staffBook);
    	pcBookedData.show();
    }
    

    private static void handleLogout() {
        // Add logic for handling logout
    	UserSessionController.logout();
    	Login loginPage = new Login();
		loginPage.show();
    }

}
