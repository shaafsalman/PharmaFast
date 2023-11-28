package Controllers;

import Dao.UserDao;
import Helpers.SessionManager;
import Models.User;
import javax.swing.JOptionPane;
import java.sql.SQLException;

public class UserController {

    public static AdminController adminController;

    static {
        try {
            adminController = new AdminController();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static final int ADMIN_CODE = AdminController.getAdminCode();
    private static final UserDao userDao = new UserDao();

    public UserController() throws SQLException {
    }

    public static boolean registerUser(User user, int adminCode) {

        if (adminCode != ADMIN_CODE)
        {
            JOptionPane.showMessageDialog(null,"Invalid Admin Code");return false;
        }
        if(userDao.userExsists(user.getUsername()))
        {
            JOptionPane.showMessageDialog(null,"User Already Exists Please Choose a different Username");return false;
        }
        if(!userDao.addUser(user))
        {
            JOptionPane.showMessageDialog(null,"Database Error");return false;
        }
        return userDao.addUser(user);
    }

    public static boolean loginUser(String username, String password) {
        User user = userDao.authenticateUser(username, password);
        if (user != null) {
            Helpers.SessionManager.setCurrentUser(user);
            return true;
        }
        return false;

    }


}
