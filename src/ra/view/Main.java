package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.config.Validation;
import ra.controller.CategoryController;
import ra.controller.ProductController;
import ra.controller.UserController;
import ra.model.Product;
import ra.model.RoleName;
import ra.model.User;


import java.util.Arrays;
import java.util.HashSet;


public class Main {
    private static UserController userController = new UserController();
    private static CategoryController categoryController = new CategoryController();
    private static ProductController productController = new ProductController();
    public static User userLogin;


    // tat ca menu dao dien dieu huong


    // ===============================HOME PAGE===============================
    public static void menuStore() {
        while (true) {
            System.out.println("-------------Menu-Store-------------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. View product");
            System.out.println("4. Search product by name");
            System.out.println("5. Exit");
            System.out.println("Enter your choice");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    viewProducts();
                    System.out.println("Please login to purchase.");
                    break;
                case 4:
                    searchProductByName();
                    System.out.println("Please login to purchase.");
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.err.println("please enter number from 1 to 4");
            }

        }
    }


    // Store: 1.-------SIGN IN---------- Start
    public static void login() {
        System.out.println("-------------Sign-In-------------");
        System.out.println("Enter username");
        String username;
        while (true) {
            username = InputMethods.getString();
            if (!Validation.validateSpaces(username)) {
                System.err.println(Constants.ERROR_SPACE);
                continue;
            }
            if (Validation.validateUserName(username)) {
                break;
            }
        }
        System.out.println("Enter password");
        String password;
        while (true) {
            password = InputMethods.getString();
            if (!Validation.validatePassword(password)) {
                System.err.println(Constants.ERROR_SPACE);
                continue;
            }
            if (Validation.validatePassword(password)) {
                break;
            }
            System.err.println(Constants.ERROR_PASSWORD);

        }

        // kiem tra dang nhap
        User user = userController.checkExistedAccount(username, password);
        if (user == null) {
            System.err.println("Account does not exist");
        } else {
            if (user.getRoles().contains(RoleName.ADMIN)) {
                userLogin = user;
                menuAdmin();

            } else {
                if (user.isStatus()) {
                    userLogin = user;
                    System.out.println("Hi " + userLogin.getName().toUpperCase() + " , " + "Welcome  to Hải Sản online, ĂN CHẤT LƯỢNG - SỐNG THỊNH VƯỢNG ");
                    menuUser();
                } else {
                    System.err.println("Your account is blocked");
                    login();
                }
            }
        }

    }
    // -------SIGN IN---------- END


    // Store: 2.---------SIGN UP-------- START
    public static void register() {
        System.out.println("-------------Sign_Up-------------");
        User user = new User();
        user.setId(userController.getNewId());
        System.out.println("ID : " + user.getId());
        System.out.println("Enter Name");
        user.setName(InputMethods.getString());
        System.out.println("Enter Username");
        String username;
        while (true) {
            username = InputMethods.getString();
            if (!Validation.validateSpaces(username)) {
                System.err.println(Constants.ERROR_SPACE);
                continue;
            }
            if (userController.checkExistedUsername(username)) {
                System.err.println(Constants.ERROR_EXISTED);
                continue;
            }
            if (Validation.validateUserName(username)) {
                user.setUsername(username);
                break;
            }
            System.err.println(Constants.ERROR_USERNAME);

        }
        System.out.println("Enter Password");
        String password;
        while (true) {
            password = InputMethods.getString();
            if (!Validation.validateSpaces(password)) {
                System.err.println(Constants.ERROR_SPACE);
                continue;
            }
            if (Validation.validatePassword(password)) {
                user.setPassword(password);
                break;
            }
            System.err.println(Constants.ERROR_PASSWORD);
        }

        System.out.println("Enter email: ");
        String email;
        while (true) {
            email = InputMethods.getString();
            if (!Validation.validateSpaces(email)) {
                System.err.println(Constants.ERROR_SPACE);
                continue;
            }
            if (userController.checkExistedEmail(email)) {
                System.err.println(Constants.ERROR_EXISTED);
                continue;
            }
            if (Validation.validateEmail(email)) {
                user.setEmail(email);
                break;
            }
            System.err.println(Constants.ERROR_EMAIL);
        }

//        System.out.println("Enter Roles: (etc: user,admin,...)");
//        String roles = InputMethods.getString();
//        String[] stringRoles = roles.split(",");
//        List<String> listRoles = Arrays.asList(stringRoles);
//        for (String r : stringRoles) {
//            // loi dụng co che break
//            switch (r) {
//                case "admin":
//                    user.getRoles().add(RoleName.ADMIN);
//                case "manager":
//                    user.getRoles().add(RoleName.MANAGER);
//                case "user":
//                    user.getRoles().add(RoleName.USER);
//                default:
//                    user.getRoles().add(RoleName.USER);
//            }
//        }
        user.setRoles(new HashSet<>(Arrays.asList(RoleName.USER)));
        userController.save(user);
        System.out.println("Đăng kí thành công");
        System.out.println("Vui long đăng nhập");
        login();

    }
    // ---------SIGN UP-------- END


    // Store: 3.---------View Products-------- START
    public static void viewProducts() {
        if (productController.findAll().size() == 0) {
            System.out.println("The category does not exist.");
        }
        for (Product product : productController.findAll()) {
            System.out.println("------------------------------------------");
            System.out.println(product);
        }

    }
    // Store: 3.---------View Products-------- END


    // Store: 4.---------Search Product By Name-------- START

    public static void searchProductByName() {
        boolean flag = false;
        System.out.println("Enter product name you want to find");
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
    // Store: 4.---------Search Product By Name-------- END


    // ---------MENU USER-------- START
    public static void menuUser() {
        while (true) {
            System.out.println("-------------Menu-User-------------");
            System.out.println("1. Show list product");
            System.out.println("2. Add to cart");
            System.out.println("3. View cart");
            System.out.println("4. View Profile");
            System.out.println("5. Edit Profile");
            System.out.println("6. Change password");
            System.out.println("7. Purchase History");
            System.out.println("0. Log Out");
            System.out.println("Enter your choice");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // hien thi danh sach san pham
                    ProductManager.showAllProduct();
                    break;
                case 2:
                    // add to card
                    CartManager.addToCart();
                    break;
                case 3:
                    // quan li gio hang
                    new CartManager();
                    break;
                case 4:
                    // view profile
                    viewProfile();
                    break;
                case 5:
                    //edit profile
                    editProfile();
                    break;
                case 6:
                    // Change password
                    changePassword();
                    break;
                case 7:
                    // Purchase History
                    new OrderManager();
                    break;
                case 0:
                    logOut();
                    break;
                default:
                    System.err.println("please enter number from 0 to 6");
            }
            if (choice == 0) {
                break;
            }
        }
    }


    public static void viewProfile() {
        System.out.println("Name: " + userLogin.getName() + "\n" + "UserName: " + userLogin.getUsername() + "\n" + "PassWord: " + userLogin.getPassword() + "\n" + "Email: " + userLogin.getEmail() + "\n" + "Phone: " + userLogin.getPhoneNumber());

    }

    public static void editProfile() {
        System.out.println("Enter Name: ");
        userLogin.setName(InputMethods.getString());
        System.out.println("Enter Phone Number");
        String phoneNumber;
        while (true) {
            phoneNumber = InputMethods.getString();
            if (Validation.validateTel(phoneNumber)) {
                break;
            }
            if (!Validation.validateTel(phoneNumber)) {
                System.out.println(Constants.ERROR_TEL);
            }
        }
        // setup phoneNumber
        userLogin.setPhoneNumber(phoneNumber);
        // save lai o database
        userController.save(userLogin);

    }

    public static void changePassword() {
        System.out.println("Enter password");
        String password;
        while (true) {
            password = InputMethods.getString();
            if (Validation.validatePassword(password)) {
                break;
            }
            if (!Validation.validatePassword(password)) {
                System.err.println(Constants.ERROR_PASSWORD);
            }
            System.err.println("The old password is not correct. Please try again...");
        }
        System.out.println("Enter new password");
        String newPass;
        while (true) {
            newPass = InputMethods.getString();
            if (newPass.equals(password)) {
                System.err.println("The new password cannot be the same as the old password. Please try again ...");
                continue;
            }
            if (Validation.validatePassword(newPass)) {
                break;
            }
        }
        // setup lai passWord
        userLogin.setPassword(newPass);
        // saveAs dataBase
        userController.save(userLogin);
        System.out.println(Constants.SUCCESS);
    }
    // ---------MENU USER-------- END


    // ---------MENU ADMIN-------- START
    public static void menuAdmin() {
        while (true) {
            System.out.println("-------------Menu-Admin-------------");
            System.out.println("1. Account Manager");
            System.out.println("2. Category Manager");
            System.out.println("3. Product Manager");
            System.out.println("4. Order Manager");
            System.out.println("0. Logout");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // Quản lí tài khoản người dùng
                    new UserMananger(userController);
                    break;
                case 2:
                    new CategoryManager(categoryController);
                    break;
                case 3:
//                    menuProductManager//
                    new ProductManager(productController);
                    break;
                case 4:
                    new OrderManager();
                    break;
                case 0:
                    ///
                    logOut();
                    break;
                default:
                    System.err.println("please enter number from 1 to 4");
            }
            if (choice == 0) {
                break;
            }
        }
    }
    // ---------MENU ADMIN-------- END


    // MENU ADMIN, MENU USER -> 0. Logout
    public static void logOut() {
        userLogin = null;
        menuStore();
        // con tro ddung
    }

    public static void main(String[] args) {
        menuStore();
    }

}
