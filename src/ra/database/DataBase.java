package ra.database;

import ra.model.RoleName;
import ra.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DataBase<T> {
    public static final String USER_PATH ="/Users/lethethuy/IdeaProjects/module 3/Demo_Final_Project_Module3/src/ra/database/users.txt";
    public static final String CATEGORY_PATH ="/Users/lethethuy/IdeaProjects/module 3/Demo_Final_Project_Module3/src/ra/database/category.txt";
    public static final String PRODUCT_PATH ="/Users/lethethuy/IdeaProjects/module 3/Demo_Final_Project_Module3/src/ra/database/product.txt";
    public static final String ORDER_PATH ="/Users/lethethuy/IdeaProjects/module 3/Demo_Final_Project_Module3/src/ra/database/order.txt";
    public static final User admin = new User(0,"admin", "admin", "Admin123", "Ha Noi", new HashSet<>(Arrays.asList(RoleName.ADMIN, RoleName.USER)));



    public  void writeToFile(List<T> o, String path){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos= new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(oos != null){
                    oos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                if(fos != null){
                    fos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public  List<T> readFromFile(String path){
        FileInputStream fis  = null;
        ObjectInputStream ois =null;
        List<T> o = new ArrayList<>();
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            o = (List<T>) ois.readObject();
        }catch (EOFException xx){


        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(ois != null){
                    ois.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                if(fis != null){
                    fis.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return o;
    }
}
