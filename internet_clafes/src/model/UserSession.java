package model;

public class UserSession {

	private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    
    public static String getCurrentUserRole() {
        if (currentUser != null) {
            return currentUser.getUserRole();
        } else {
            return null; // Handle the case where no user is logged in
        }
    }
    
	public static void logout() {
		currentUser = null; // Clear the user data on logout
	}

}