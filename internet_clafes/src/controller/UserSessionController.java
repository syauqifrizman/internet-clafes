package controller;

import model.User;
import model.UserSession;

public class UserSessionController {
	//mendapatkan data User yang sedag login
    public static User getCurrentUser() {
        return UserSession.getCurrentUser();
    }

    //memasukan data User ke dalam currentUser untuk digunakan saat login
    public static void setCurrentUser(User user) {
        UserSession.setCurrentUser(user);
    }
    
    //method untuk mengambil nama yang sedang login
    public static String getCurrentUsername() {
    	return UserSession.getCurrentUsername();
    }
    
    //method untuk mengambill role yang sedang login
    public static String getCurrentUserRole() {
    	return UserSession.getCurrentUserRole();
    }
    
    //method untuk logout
    //metod ini akan mengosongkan kembali data di currentUser
	public static void logout() {
		UserSession.logout();
	}
}
