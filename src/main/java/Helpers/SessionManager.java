package Helpers;


import Models.User;

public class SessionManager {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }

    public String getName(){
        if (currentUser == null) {
         return"Shaaf Salman SS";
        }

        return currentUser.getUsername();}

    public String getRole() {
        if (currentUser == null) {
            return "Manager";
        }
        return currentUser.getRole();
    }

    public int getUserID() { if (currentUser == null) {
        return 0;
    }
        return currentUser.getUserID();
    }
}
