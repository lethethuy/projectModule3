package ra.controller;

import ra.model.Category;
import ra.service.CategoryService;

import java.util.List;

public class CategoryController {
    private CategoryService categoryService;

    public CategoryController() {
        categoryService = new CategoryService();
    }

    public List<Category> findAll() {
        return categoryService.findAll();
    }


    public void save(Category category) {
        categoryService.save(category);
    }


    public void delete(Integer id) {
        categoryService.delete(id);
    }

    public Category findById(Integer id) {
        return categoryService.findById(id);
    }

    public int getNewId() {
        return categoryService.getNewId();
    }
}
