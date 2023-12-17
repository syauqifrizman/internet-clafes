package view.menu;

import controller.UserSessionController;
import view.ViewPC;
import view.login_register.Login;

public abstract class UserMenu {
//	
	protected static void navigateToHome() {
		// Implement logic to navigate to the "View All PC" page
		ViewPC viewpc = ViewPC.getInstance();
    	viewpc.show();
	}

	protected static void handleLogout() {
        // Add logic for handling logout
    	UserSessionController.logout();
    	Login loginPage = new Login();
		loginPage.show();
    }
    
}
