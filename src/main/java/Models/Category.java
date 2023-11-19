package Models;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private static List<Category> categories = new ArrayList<>();

    private int categoryID;
    private String categoryName;

    public Category(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public static void addCategory(Category category) {
        categories.add(category);
    }
}
