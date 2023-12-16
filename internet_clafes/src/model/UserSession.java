package model;

//kelas yang bertanggung jawab atas sesi user yang sedang login dalam sistem
public class UserSession {

	//data User yang sedang login
	private static User currentUser;

	//mendapatkan data User yang sedag login
    public static User getCurrentUser() {
        return currentUser;
    }

    //memasukan data User ke dalam currentUser untuk digunakan saat login
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    
    //method untuk mengambil nama yang sedang login
    public static String getCurrentUsername() {
        if (currentUser != null) {
            return currentUser.getUserName();
        } else {
            return null; // Handle the case where no user is logged in
        }
    }
    
    //method untuk mengambill role yang sedang login
    public static String getCurrentUserRole() {
        if (currentUser != null) {
            return currentUser.getUserRole();
        } else {
            return null; // Handle the case where no user is logged in
        }
    }
    
    //method untuk logout
    //metod ini akan mengosongkan kembali data di currentUser
	public static void logout() {
		currentUser = null; // Clear the user data on logout
	}

}