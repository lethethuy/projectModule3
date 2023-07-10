package ra.service;

import ra.model.Product;
import ra.database.DataBase;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IGenericService<Product, Integer> {
    private List<Product> productList;
    private DataBase<Product> productDataBase = new DataBase();

    public ProductService() {
        List<Product> list = productDataBase.readFromFile(DataBase.PRODUCT_PATH);
        if (list == null) {
            list = new ArrayList<>();
        }
        this.productList = list;
    }


    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        if (findById(product.getProductId()) == null) {
            //add
            productList.add(product);

        } else {
            // update
            productList.set(productList.indexOf(findById(product.getProductId())), product);
        }
        // luu vao file
        productDataBase.writeToFile(productList, DataBase.PRODUCT_PATH);


    }



    @Override
    public void delete(Integer id) {
        productList.remove(findById(id));
        productDataBase.writeToFile(productList, DataBase.PRODUCT_PATH);

    }

    @Override
    public Product findById(Integer id) {
        for (Product product : productList
        ) {
            if (product.getProductId()==id) {
                return product;
            }
        }
        return null;
    }

    public int getNewId(){
        int max = 0;
        for (Product product:productList
             ) {
            if (product.getProductId()>max){
                max=product.getProductId();
            }
        }
        return max +1;
    }
}
