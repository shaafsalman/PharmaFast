import Dao.CategoryDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoTest {

    private static CategoryDao categoryDao;

    @BeforeAll
    static void setUp() {
        categoryDao = new CategoryDao();
    }

    @Test
    void addCategory() {
        assertTrue(categoryDao.addCategory("TestCategory"));
    }

    @Test
    void editCategoryName() {
        int categoryId = categoryDao.getCategoryIDByName("TestCategory");
        assertTrue(categoryDao.editCategoryName(categoryId, "NewTestCategory"));
    }

    @Test
    void deleteCategory() {
        int categoryId = categoryDao.getCategoryIDByName("NewTestCategory");
        assertTrue(categoryDao.deleteCategory(categoryId));
    }

    @Test
    void getCategoryData() {
        Map<Integer, String> categoryData = new HashMap<>();
        assertTrue(categoryDao.getCategoryData(categoryData));

        assertFalse(categoryData.isEmpty());
    }

    @Test
    void getNumberOfProductsInCategory() {
        int categoryId = categoryDao.getCategoryIDByName("TestCategory");
        assertEquals(0, categoryDao.getNumberOfProductsInCategory(categoryId));
    }

    @Test
    void getCategoryIDByName() {
        int categoryId = categoryDao.getCategoryIDByName("TestCategory");
        assertNotEquals(-1, categoryId);
    }

    @Test
    void getLargestCategoryID() {
        assertTrue(categoryDao.getLargestCategoryID() >= 0);
    }

    @Test
    void isCategoryExists() {
        int categoryId = categoryDao.getCategoryIDByName("TestCategory");
        assertTrue(categoryDao.isCategoryExists(categoryId));
    }
}
