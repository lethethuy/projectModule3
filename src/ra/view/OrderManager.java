package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.config.Message;
import ra.config.Validation;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.controller.UserController;
import ra.database.DataBase;
import ra.model.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private OrderController orderController;

    public OrderManager() {
        orderController = new OrderController();
        if (Main.userLogin.getRoles().contains(RoleName.ADMIN)) {
            while (true) {
                System.out.println("=========ORDER MANAGER==========");
                System.out.println("1. Show all Order");
                System.out.println("2. Show order waiting ...");
                System.out.println("3. Show order accepted ");
                System.out.println("4. Show order canceled ");
                System.out.println("5. Show order detail ");
                System.out.println("6. Cancel order ");
                System.out.println("7. Change of status ");
                System.out.println("0. Back ");
                System.out.println("Enter choice: ");
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        // View all orders
                        showAllOrder();
                        break;
                    case 2:
                        showOrderByCode((byte) 1);
                        break;
                    case 3:
                        showOrderByCode((byte) 2);

                        break;
                    case 4:
                        showOrderByCode((byte) 3);
                        break;
                    case 5:
                        showOrderDetail();
                        break;
                    case 6:
                        cancelOrder();
                        break;
                    case 7:
                        changeOfStatus();
                        break;
                    case 0:
                        break;
                    default:
                        System.err.println("Please enter from 0 to 6");
                }
                if (choice == 0) {
                    break;
                }
            }
        } else {
            orderController = new OrderController();
            while (true) {
                System.out.println("-------------Order History-------------");
                System.out.println("1. Show all Order");
                System.out.println("2. Show order waiting ...");
                System.out.println("3. Show order accepted");
                System.out.println("4. Show order canceled");
                System.out.println("5. Show order detail");
                System.out.println("0. Back");
                System.out.println("Enter your choice");
                int choice = InputMethods.getInteger();
                switch (choice) {
                    case 1:
                        // hiển thị tất cả
                        showAllOrder();
                        break;
                    case 2:
                        // chờ xác nhận
                        showOrderByCode((byte) 1);
                        break;
                    case 3:
                        showOrderByCode((byte) 2);
                        break;
                    case 4:
                        showOrderByCode((byte) 3);
                        break;
                    case 5:
                        // chi tiết hóa đơn
                        showOrderDetail();
                        break;
                    case 0:
                        break;
                    default:
                        System.err.println("Please enter number from 1 to 4");
                }
                if (choice == 0) {
                    break;
                }
            }

        }
    }

    public void showAllOrder() {
        List<Order> orderList;
        if (Main.userLogin.getRoles().contains(RoleName.ADMIN)) {

            orderList = orderController.findAll();
        } else {
            orderList = orderController.findOrderByUserId();
        }
        if (orderList.isEmpty()) {
            System.err.println("Empty");
            return;
        }
        if (Main.userLogin.getRoles().contains(RoleName.ADMIN)) {
            for (Order order : orderList) {
                UserController userController = new UserController();
                User orderUser = userController.findById(order.getUseId());
                orderAdmin(order, orderUser);
            }
        } else {
            for (Order order : orderList) {
                System.out.println(order);
            }
        }
    }

    public void showOrderByCode(byte code) {
        List<Order> orderList;
        if(Main.userLogin.getRoles().contains(RoleName.ADMIN)){
            orderList = orderController.findAll();
        } else {
            orderList = orderController.findOrderByUserId();
        }
        List<Order> filter = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == code) {
                filter.add(order);
            }
        }
        if (filter.isEmpty()) {
            System.out.println("Empty");
            return;
        }
        if(Main.userLogin.getRoles().contains(RoleName.ADMIN)){
            for (Order order : filter) {
                UserController userController = new UserController();
                User orderUser = userController.findById(order.getUseId());
                orderAdmin(order,orderUser);
            }
        }
        else {
            for (Order order : filter) {
                System.out.println(order);
            }
        }
    }

    public void showOrderDetail() {
        List<Order> orderList;
        if(Main.userLogin.getRoles().contains(RoleName.ADMIN)){
            orderList = orderController.findAll();
        } else {
            orderList = orderController.findOrderByUserId();
        }
        List<Order> filter = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getStatus() == 2) {
                filter.add(order);
            }
        }
        if(filter.isEmpty()){
            System.out.println("Empty");
            return;
        }

        if(Main.userLogin.getRoles().contains(RoleName.ADMIN)){
            for (Order order : filter) {
                UserController userController = new UserController();
                User orderUser = userController.findById(order.getUseId());
                orderAdmin(order, orderUser);
            }
        }else {
            for (Order order : filter) {
                System.out.println("\n");
                System.out.printf("----------Invoice----------\n");
                System.out.printf("          Id:%5d                   \n", order.getId());
                System.out.printf("          Date:%15s                  \n", order.getBuyDate());
                System.out.println("          Infomation                   ");
                System.out.printf("Receiver: " + order.getReceiver() + " | Phone: " + order.getPhoneNumber() + "\n");
                System.out.println("Address " + order.getAddress());
                System.out.println("----------Detail----------");
                for (CartItem item : order.getOrderDetail()) {
                    System.out.println(item);
                }
                System.out.println("Total: " + Validation.formatPrice(order.getTotalPrice()));
                System.out.println("--------Thank you---------");
            }
        }


    }

    public void orderAdmin(Order order, User orderUser) {
        System.out.println("=======================");
        System.out.println("ID: " + order.getId() + " | Date: " + order.getBuyDate());
        System.out.println("Detail: " +
                order.getOrderDetail().toString().replace(", ", "\n").replace("[", "\n").replace("]", "\n").replace("ID: ", ""));
        System.out.println("Total: " + order.getTotalPrice() + " | Status: " + Message.getStatusByCode(order.getStatus()));
        System.out.println("Receiver: " + order.getReceiver());
        System.out.println("Phone number: " + order.getPhoneNumber());
        System.out.println("Address: " + order.getAddress());
    }

    public void changeOfStatus() {
        System.out.println("Enter order ID: ");
        int changeOrderStatusId = InputMethods.getInteger();
        Order changeOrderStatus = orderController.findByIdForAdmin(changeOrderStatusId);

        if (changeOrderStatus == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        if (changeOrderStatus.getStatus() == 3) {
            System.err.println("This order has been canceled");
            return;
        }
        if (changeOrderStatus.getStatus() == 2) {
            System.err.println("This order is delivering, can't change status");
            return;
        }
        if (changeOrderStatus.getStatus() == 1) {
            System.out.println("Choose status: ");
            System.out.println("1.Accept");
            System.out.println("2.Deny");
            System.out.println("Enter your choice: ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                changeOrderStatus.setStatus((byte) 2);
                orderController.save(changeOrderStatus);
                System.out.println(Constants.SUCCESS);
            }
            else if (choice==2){
                changeOrderStatus.setStatus((byte) 3);
                for (CartItem item : changeOrderStatus.getOrderDetail()) {
                    ProductController productController = new ProductController();
                    Product product = productController.findById(item.getProduct().getProductId());
                    product.setStock(product.getStock() + item.getQuantity());
                    productController.save(product);
                }
                orderController.save(changeOrderStatus);
                System.out.println(Constants.SUCCESS);
            } else {
                System.err.println("Invalid format! Please try again....");
            }
        }
    }
    public void cancelOrder() {
        System.out.println("Enter order ID: ");
        int cancelOrderId = InputMethods.getInteger();
        Order order = orderController.findById(cancelOrderId);
        if (order == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        if (order.getStatus() == 3) {
            System.err.println("This order has been canceled");
            return;
        }
        if (order.getStatus() == 1) {
            System.out.println("Are you sure want to cancel ?");
            System.out.println("1.Yes");
            System.out.println("2.No");
            System.out.println("Enter your choice: ");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                order.setStatus((byte) 3);
                for (CartItem item : order.getOrderDetail()) {
                    ProductController productController = new ProductController();
                    Product product = productController.findById(item.getProduct().getProductId());
                    product.setStock(product.getStock() + item.getQuantity());
                    productController.save(product);
                }
                orderController.save(order);
                System.out.println(Constants.SUCCESS);
            }
        }
    }


}

