import Models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUserID() {
        User user = new User(1, "FarhanJafri", "1337", "admin");
        assertEquals(1, user.getUserID());
    }

    @Test
    void setUserID() {
        User user = new User(1, "FarhanJafri", "1337", "admin");
        user.setUserID(2);
        assertEquals(2, user.getUserID());
    }

    @Test
    void getUsername() {
        User user = new User(1, "FarhanJafri", "1337", "admin");
        assertEquals("FarhanJafri", user.getUsername());
    }

    @Test
    void setUsername() {
        User user = new User(1, "FarhanJafri", "1337", "admin");
        user.setUsername("jane_doe");
        assertEquals("jane_doe", user.getUsername());
    }

    @Test
    void getPassword() {
        User user = new User(1, "FarhanJafri", "1337", "admin");
        assertEquals("1337", user.getPassword());
    }

    @Test
    void setPassword() {
        User user = new User(1, "FarhanJafri", "1337", "admin");
        user.setPassword("new_password");
        assertEquals("new_password", user.getPassword());
    }

    @Test
    void getRole() {
        User user = new User(1, "FarhanJafri", "1337", "admin");
        assertEquals("admin", user.getRole());
    }

    @Test
    void setRole() {
        User user = new User(1, "FarhanJafri", "1337", "admin");
        user.setRole("user");
        assertEquals("user", user.getRole());
    }

    @Test
    void print() {
        User user = new User(1, "FarhanJafri", "1337", "admin");
    }

}
