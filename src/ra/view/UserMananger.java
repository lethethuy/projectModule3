package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.controller.UserController;
import ra.model.User;

public class UserMananger {
    private UserController userController;

    public UserMananger(UserController userController) {
        this.userController = userController;
        while (true) {
            System.out.println("-------------Menu-Account-Manager-------------");
            System.out.println("1. Show All Acount");
            System.out.println("2. Block/Unblock Acount");
            System.out.println("3. Search Account by name");
            System.out.println("4. Back");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    showAllAccount();
                    break;
                case 2:
                    changeStatus();
                    break;
                case 3:
                    searchUser();
                    break;

                case 4:
                    Main.menuAdmin();
                    break;
                default:
                    System.err.println("please enter number from 1 to 4");
            }

        }
    }

    public void showAllAccount() {
        for (User u : userController.findAll()) {
            System.out.println("-------------------------------------------");
            System.out.println(u);
        }
    }

    public void changeStatus() {
        // lấy ra userlogin để check quyền xem có được quyền khóa tài khoản kia không
        System.out.println("Enter Account ID");
        int id = InputMethods.getInteger();
        User user = userController.findById(id);
        if (user == null) {
            System.err.println(Constants.NOT_FOUND);
        } else {
            user.setStatus(!user.isStatus());
            userController.save(user);
        }

    }

    public void searchUser(){
        this.userController = userController;
        boolean flag = false;
        System.out.println("Enter the name you want to find ");
        String name = InputMethods.getString();
        for (User u:userController.findAll()
             ) {
            if (u.getName().toLowerCase().contains(name.toLowerCase())){
                System.out.println(u);
                flag = true;
            }
        }
        if (!flag){
            System.err.println("The name does not exist");
        }
    }
}
