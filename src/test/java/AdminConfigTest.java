import Helpers.AdminConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Verified

class AdminConfigTest {

    @Test
    void getVatRate()
    {
        float expectedVatRate = 0.15F;
        AdminConfig adminConfig = new AdminConfig(expectedVatRate, 1337);

        float actualVatRate = adminConfig.getVatRate();

        assertEquals(expectedVatRate, actualVatRate);
    }

    @Test
    void setVatRate()
    {
        AdminConfig adminConfig = new AdminConfig();
        float expectedVatRate = 0.20F;

        adminConfig.setVatRate(expectedVatRate);
        float actualVatRate = adminConfig.getVatRate();

        assertEquals(expectedVatRate, actualVatRate);
    }

    @Test
    void getAdminCode()
    {
        int expectedAdminCode = 1337;
        AdminConfig adminConfig = new AdminConfig(0.15F, expectedAdminCode);

        int actualAdminCode = adminConfig.getAdminCode();

        assertEquals(expectedAdminCode, actualAdminCode);
    }

    @Test
    void setAdminCode() {
        AdminConfig adminConfig = new AdminConfig();
        int expectedAdminCode = 456;

        adminConfig.setAdminCode(expectedAdminCode);
        int actualAdminCode = adminConfig.getAdminCode();

        assertEquals(expectedAdminCode, actualAdminCode);
    }
}
