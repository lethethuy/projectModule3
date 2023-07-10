package ra.model;

import ra.config.InputMethods;

import java.io.Serializable;

public class Category implements Serializable {
    private int categoryId;
    private String categoryName;

    public Category() {
    }

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void inputData() {
        System.out.print("Enter the category name: ");
        this.categoryName = InputMethods.getString();
    }

    @Override
    public String toString() {
        return "ID: " + categoryId + " | Catalog: " + categoryName;
    }
}
