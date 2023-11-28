import Dao.CategoryDao;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoTest {

    private final CategoryDao categoryDao = new CategoryDao();

    @Test
    void addCategory() {
        assertTrue(categoryDao.addCategory("TestCategory"));
    }

    @Test
    void editCategoryName() {
        assertTrue(categoryDao.editCategoryName(1, "EditTest"));
    }

    @Test
    void deleteCategory() {
        assertTrue(categoryDao.deleteCategory(1));
    }

    @Test
    void getCategoryData() {
        Map<Integer, String> categoryData = new HashMap<>();
        assertTrue(categoryDao.getCategoryData(categoryData));
        assertFalse(categoryData.isEmpty());
    }

    @Test
    void getNumberOfProductsInCategory() {
        int productCount = categoryDao.getNumberOfProductsInCategory(1);
        assertTrue(productCount >= 0);
    }

    @Test
    void getCategoryIDByName() {
        int categoryId = categoryDao.getCategoryIDByName("TestCategory");
        assertTrue(categoryId >= 0);
    }

    @Test
    void getLargestCategoryID() {
        int largestCategoryId = categoryDao.getLargestCategoryID();
        assertTrue(largestCategoryId >= 0);
    }

    @Test
    void isCategoryExists() {
        assertTrue(categoryDao.isCategoryExists(1));
        assertFalse(categoryDao.isCategoryExists(-1));
    }
}
