package ra.view;

import ra.config.InputMethods;
import ra.controller.CategoryController;
import ra.model.Category;

public class CategoryManager {
    private CategoryController categoryController;
    public CategoryManager(CategoryController categoryController) {
        this.categoryController = categoryController;
        while (true){
            System.out.println("-------------Menu-Category-Manager-------------");
            System.out.println("1. Show Category");
            System.out.println("2. Create Category");
            System.out.println("3. Edit Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Exit ");
            System.out.println("Please enter options: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    showAllCategory();
                    break;
                case 2:
                    addCategory();
                    break;
                case 3:
                    editCategory();
                    break;
                case 4:
                    deleteCategory();
                    break;
                case 5:
                    Main.menuAdmin();
                    break;
                default:
                    System.err.println("please enter number from 1 to 5");
            }

        }


    }
    public void showAllCategory(){
        for (Category category: categoryController.findAll()) {
            System.out.println("-------------------------------------------");
            System.out.println(category);
        }
    }

    public void addCategory() {

        System.out.println("How many categories do you want to enter: ");
        int n = InputMethods.getInteger();
        for (int i = 0; i < n; i++) {
            Category category = new Category();
            category.setCategoryId(categoryController.getNewId());
            System.out.println("Category NO." + category.getCategoryId());
            category.inputData();
            categoryController.save(category);
        }
    }

    public void editCategory(){
        System.out.print("You want to edit the category whose ID is: ");
        int id = InputMethods.getInteger();
        Category catalog = categoryController.findById(id);
        if (catalog == null) {
            System.err.println("This category does not exist.");
            return;
        }
        Category newCatalog = new Category();
        newCatalog.setCategoryId(catalog.getCategoryId());
        newCatalog.inputData();
        categoryController.save(newCatalog);
    }
    public void deleteCategory() {
        System.out.print("You want to delete the category whose ID is: ");
        int id = InputMethods.getInteger();
        categoryController.delete(id);
    }

}
