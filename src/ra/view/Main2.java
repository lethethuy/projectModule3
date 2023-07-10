package ra.view;

import ra.model.Product;
import ra.model.RoleName;
import ra.model.User;
import ra.service.UserService;
import ra.database.DataBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main2 {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Set<RoleName> set = new HashSet<>();
        Set<RoleName> set2 = new HashSet<>();
//        set2.add(RoleName.USER);
        set2.add(RoleName.ADMIN);
        set.add(RoleName.USER);
        User user = new User(2,"Hung","hunghx","111111","HaNoi","0987654321",true,set, "hanoi");
        User admin = new User();
        admin.setId(1);
        admin.setStatus(true);
        admin.setUsername("admin123");
        admin.setPassword("admin123");
        admin.setRoles(set2);
        userService.save(admin);
        userService.save(user);
        DataBase<User> data= new DataBase<>();
        for (User u: data.readFromFile(DataBase.USER_PATH)) {
            System.out.println("-------------------------------------------");
            System.out.println(u);
        }

//         fix cứng product
        List<Product> list = new ArrayList<>();
        Product p1 = new Product(1,"Áo khoác 1",199,"",100,true);
        Product p2 = new Product(2,"Áo dài 2",199,"",100,true);
        Product p3 = new Product(3,"Quần hoa 3",199,"",100,true);
        Product p4 = new Product(4,"Quần đùi 4",199,"",100,true);
        Product p5 = new Product(5,"Vòng cổ 5",199,"",100,true);
        Product p6 = new Product(6,"Xích chó 6",199,"",100,true);
        Product p7 = new Product(7,"Thắt lưng 7",199,"",100,true);
        Product p8 = new Product(8,"Tạ tay 8",199,"",100,true);
        Product p9 = new Product(9,"Mũ lưỡi trai 9",199,"",100,true);
        Product p10 = new Product(10,"Nhẫn 10",199,"",100,true);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        list.add(p6);
        list.add(p7);
        list.add(p8);
        list.add(p9);
        list.add(p10);
        new DataBase<Product>().writeToFile(list,DataBase.PRODUCT_PATH);
    }
}
