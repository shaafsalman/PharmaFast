package Controllers;

import Dao.UserDao;
import Helpers.SessionManager;
import Models.User;
import javax.swing.JOptionPane;

public class UserController {

    private static final int ADMIN_CODE = AdminController.getAdminCode();
    private static UserDao userDao = new UserDao();

    public static boolean registerUser(User user, int adminCode) {

        if ( ADMIN_CODE != adminCode)
        {
            JOptionPane.showMessageDialog(null, "Invalid admin code for Admin.");
            return false;
        }

        boolean userAdded = userDao.addUser(user);
        if (userAdded)
        {
            JOptionPane.showMessageDialog(null, "Registration successful.");
            return true;
        } else
        {
            JOptionPane.showMessageDialog(null, "Registration failed.");
            return false;
        }
    }

    public static User loginUser(String username, String password) {
        User user = userDao.authenticateUser(username, password);
        if (user != null)
        {
            Helpers.SessionManager.setCurrentUser(user);
            return user;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
            return null;
        }
    }

}
