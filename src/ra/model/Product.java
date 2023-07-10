package ra.model;

import ra.config.InputMethods;

import java.io.Serializable;
import java.util.List;

public class Product implements Comparable<Product>, Serializable {
    private int productId;
    private String productName;
    private double productPrice;
    private String description;
    private int stock;
    private Category category;
    private boolean status = true;

    public Product() {
    }



    public Product(int productId, String productName, double productPrice, String description, int stock, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.stock = stock;
        this.category = category;
        this.status = status;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void inputData(List<Category> list) {
        System.out.print("Enter product name: ");
        this.productName = InputMethods.getString();
        System.out.print("Enter price (>0): ");
        this.productPrice = InputMethods.getPositiveNumber();
        System.out.print("Enter description: ");
        this.description = InputMethods.getString();
        System.out.print("Enter stock(> 10): ");
        this.stock = InputMethods.getStock();
        for (Category c : list) {
            System.out.println(c);
        }
        while (true) {
            boolean flag = false;
            System.out.print("Please enter category ID: ");
            int id = InputMethods.getInteger();
            for (Category c : list) {
                if (c.getCategoryId() == id) {
                    this.category = c;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            } else {
                System.err.println("The category does not exist, please choose again: ");
            }
        }
    }

    @Override
    public String toString() {
        return "ID: " + productId + " | Name: " + productName + " | Price: " + productPrice +
                "\nDescription: " + description + " | Stock: " + stock +
                "\nCategory: " + category.getCategoryName() + " | Status: " + (status ? "Buy" : "Not Buy");
    }

    @Override
    public int compareTo(Product o) {
        return Double.compare(o.getProductPrice(), this.productPrice);
    }

}
