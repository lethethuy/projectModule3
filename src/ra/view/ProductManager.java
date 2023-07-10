package ra.view;

import ra.config.InputMethods;
import ra.controller.CategoryController;
import ra.controller.ProductController;
import ra.model.Product;

public class ProductManager {
    private static ProductController productController = new ProductController();
    private CategoryController categoryController = new CategoryController();

    public ProductManager(ProductController productController) {
        this.productController = productController;
        while (true) {
            System.out.println("-------------Menu-Product-Manager-------------");
            System.out.println("1. Show Product");
            System.out.println("2. Create Product");
            System.out.println("3. Edit Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product By Name");
            System.out.println("6. Exit ");
            System.out.println("Please enter options: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    showAllProduct();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    searchProductByName();
                    break;
                case 6:
                    Main.menuAdmin();
                    break;
                default:
                    System.err.println("please enter number from 1 to 6");
            }

        }


    }

    public static void showAllProduct() {
        if (productController.findAll().size() == 0) {
            System.out.println("The product does not exist.");
        }
        for (Product product : productController.findAll()
        ) {

                System.out.println("-------------------------------------------");
                System.out.println(product);
        }
    }

    public void addProduct() {
        System.out.println("How many product do you want to enter");
        int n = InputMethods.getInteger();
        for (int i = 0; i < n; i++) {
            Product product = new Product();
            product.setProductId(productController.getNewId());
            System.out.println("Product NO." + product.getProductId());
            product.inputData(categoryController.findAll());
            productController.save(product);

        }
    }

    public void editProduct() {
        System.out.println("Enter product ID");
        int id = InputMethods.getInteger();
        Product product = productController.findById(id);
        if (product == null) {
            System.out.println("The product does not exist.");
            return;
        }
        Product newProduct = new Product();
        newProduct.setProductId(product.getProductId());
        newProduct.inputData(categoryController.findAll());
        productController.save(newProduct);


    }


    public void deleteProduct() {
        System.out.println("Enter delete product ID");
        int id = InputMethods.getInteger();
        productController.delete(id);
        System.out.println("Delete Successfully");

    }

    public void searchProductByName() {
        boolean flag = false;
        System.out.println("Enter the product name you want to find");
        String name = InputMethods.getString();
        for (Product p : productController.findAll()
        ) {
            if (p.getProductName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(p);
                flag = true;
            }
        }
        if (!flag) {
            System.err.println("The product does not exist.");
        }
    }

}
