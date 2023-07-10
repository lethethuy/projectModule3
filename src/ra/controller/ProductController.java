package ra.controller;

import ra.model.Category;
import ra.model.Product;
import ra.service.CategoryService;
import ra.service.ProductService;

import java.util.List;

public class ProductController {
    private ProductService productService;

    public ProductController() {
        productService = new ProductService();
    }

    public List<Product> findAll() {
        return productService.findAll();
    }


    public void save(Product product) {
        productService.save(product);
    }


    public void delete(Integer id) {
        productService.delete(id);
    }

    public Product findById(Integer id) {
        return productService.findById(id);
    }

    public int getNewId() {

        return productService.getNewId();
    }

}
