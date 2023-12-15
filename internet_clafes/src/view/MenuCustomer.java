package view;

import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import model.UserSession;

public class MenuCustomer{
	
	public static Parent createMenu() {
        // Create a menu bar
        MenuBar menuBar = new MenuBar();

        // Create an Actions menu with menu items
        Menu mainMenu = new Menu("Menu");
        
        // "Home" menu item
        MenuItem homeMenuItem = new MenuItem("Home");
        homeMenuItem.setOnAction(e -> navigateToHome());

        // "Transaction History" menu item
        MenuItem transactionHistoryMenuItem = new MenuItem("Transaction History");
        transactionHistoryMenuItem.setOnAction(e -> navigateToTransactionHistory());

        // "Logout" menu item
        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(e -> handleLogout());

        // Add menu items to the Actions menu
        mainMenu.getItems().addAll(homeMenuItem, transactionHistoryMenuItem, logoutMenuItem);

        // Add the Actions menu to the menu bar
        menuBar.getMenus().add(mainMenu);

        // return menubar
        return menuBar;
    }
    
    private static void navigateToHome() {
        // Implement logic to navigate to the "Book PC" page
    	ViewPC viewpc = ViewPC.getInstance();
		viewpc.show();
    }

    private static void navigateToTransactionHistory() {
        // Implement logic to navigate to the "Transaction History" page
    	TransactionHistory tHistory = TransactionHistory.getInstance();
    	tHistory.show();
    }
    

    private static void handleLogout() {
        // Add logic for handling logout
    	UserSession.logout();
    	Login loginPage = new Login();
		loginPage.show();
    }
}
