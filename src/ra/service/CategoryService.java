package ra.service;

import ra.model.Category;
import ra.database.DataBase;

import java.util.ArrayList;
import java.util.List;

public class CategoryService implements IGenericService<Category, Integer> {
    private List<Category> categoryList;
    private DataBase<Category> categoryDataBase = new DataBase();

    public CategoryService() {
        List<Category> list = categoryDataBase.readFromFile(DataBase.CATEGORY_PATH);
        if (list==null){
            list = new ArrayList<>();
        }
        this.categoryList = list;
    }

    @Override
    public List<Category> findAll() {
        return categoryList;
    }

    @Override
    public void save(Category category) {
        if (findById(category.getCategoryId())==null) {
            //add
            categoryList.add(category);

        }else {
            // update
            categoryList.set(categoryList.indexOf(findById(category.getCategoryId())),category);
        }
        // luu vao file
        categoryDataBase.writeToFile(categoryList,DataBase.CATEGORY_PATH);
    }

    @Override
    public void delete(Integer id) {
        categoryList.remove(findById(id));
        categoryDataBase.writeToFile(categoryList,DataBase.CATEGORY_PATH);
    }

    @Override
    public Category findById(Integer id) {
        for (Category category:categoryList
             ) {
            if (category.getCategoryId()==id){
                return category;
            }
        }
        return null;
    }
    public int getNewId(){
        int max = 0;
        for (Category category:categoryList
             ) {
            if (category.getCategoryId()>max){
                max = category.getCategoryId();
            }
        }
        return max +1;
    }
}




