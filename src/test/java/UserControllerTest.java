import Controllers.UserController;
import Dao.UserDao;
import Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//verified
class UserControllerTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void registerUserWithValidAdminCode() {
        User user = new User(0, "FarhanJafri", "1337", "Manager");
        int validAdminCode = UserController.ADMIN_CODE;
        assertTrue(UserController.registerUser(user, validAdminCode));

    }

    @Test
    void registerUserWithInvalidAdminCode() {
        User user = new User(0, "FarhanJafri", "1337", "Manager");
        int invalidAdminCode = UserController.ADMIN_CODE + 1;
        assertFalse(UserController.registerUser(user, invalidAdminCode));
    }

    @Test
    void loginUserWithValidCredentials() {
        User user = new User(0, "FarhanJafri", "1337", "Manager");
        assertTrue(UserController.loginUser(user.getUsername(), user.getPassword()));
    }

    @Test
    void loginUserWithInvalidCredentials() {
        User user = new User(0, "FarhanJafri2", "1337", "Manager");

        assertFalse(UserController.loginUser(user.getUsername(), user.getPassword()));

    }
}
