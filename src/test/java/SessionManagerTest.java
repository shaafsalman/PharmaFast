import Helpers.SessionManager;
import Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


//verified
class SessionManagerTest {

    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManager();
    }

    @Test
    void setCurrentUser() {
        User user = new User(1, "FarhanJafri", "1337", "Admin");
        sessionManager.setCurrentUser(user);
        assertEquals(user, sessionManager.getCurrentUser());
    }

    @Test
    void getCurrentUser()
    {

        User user = new User(1, "FarhanJafri", "1337", "Admin");
        sessionManager.setCurrentUser(user);

        assertEquals(user, sessionManager.getCurrentUser());
    }

    @Test
    void isLoggedIn() {

        User user = new User(1, "FarhanJafri", "1337", "Admin");
        sessionManager.setCurrentUser(user);

        assertTrue(sessionManager.isLoggedIn());
    }

    @Test
    void logout() {
        User user = new User(1, "FarhanJafri", "1337", "Admin");
        sessionManager.setCurrentUser(user);

        assertTrue(sessionManager.isLoggedIn());

        sessionManager.logout();

        assertFalse(sessionManager.isLoggedIn());
        assertNull(sessionManager.getCurrentUser());
    }

    @Test
    void getName() {

        User user = new User(1, "FarhanJafri", "1337", "Admin");
        sessionManager.setCurrentUser(user);

        assertEquals("FarhanJafri", sessionManager.getName());
    }

    @Test
    void getRole() {

        User user = new User(1, "FarhanJafri", "1337", "Admin");
        sessionManager.setCurrentUser(user);

        assertEquals("Admin", sessionManager.getRole());
    }

    @Test
    void getUserID() {

        User user = new User(1, "FarhanJafri", "1337", "Admin");
        sessionManager.setCurrentUser(user);

        assertEquals(1, sessionManager.getUserID());
    }
}
