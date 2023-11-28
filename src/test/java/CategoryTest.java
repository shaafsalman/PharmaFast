import Models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @BeforeEach
    void setUp() {
        Category.getCategories().clear();
    }

    @Test
    void getCategoryID() {
        Category category = new Category(1, "TestingCategory");
        assertEquals(1, category.getCategoryID());
    }

    @Test
    void getCategoryName() {
        Category category = new Category(1, "TestingCategory");
        assertEquals("TestingCategory", category.getCategoryName());
    }


    @Test
    void addCategory() {
        Category category = new Category(1, "TestingCategory");
        Category.addCategory(category);

        assertEquals(1, Category.getCategories().size());
        assertTrue(Category.getCategories().contains(category));
    }
}
