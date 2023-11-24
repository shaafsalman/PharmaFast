import Controllers.AdminController;
import Controllers.UserController;
import Dao.UserDao;
import Models.User;
import Helpers.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.swing.JOptionPane;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void registerUserWithValidAdminCode() {
        User user = new User(1, "FarhanJafri", "1337", "User");
        int validAdminCode = AdminController.getAdminCode();

        when(userDao.addUser(user)).thenReturn(true);

        assertTrue(UserController.registerUser(user, validAdminCode));

        verify(userDao).addUser(user);
    }

    @Test
    void loginUserWithValidCredentials()
    {
        User user = new User(1, "FarhanJafri", "1337", "User");

        when(userDao.authenticateUser(user.getUsername(), user.getPassword())).thenReturn(user);

        assertEquals(user, UserController.loginUser(user.getUsername(), user.getPassword()));

    }

}
