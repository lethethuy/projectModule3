package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.config.Message;
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
                System.out.println("6. Change of status ");
                System.out.println("0. Back ");
                System.out.println("Enter choice: ");
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        // View all orders
                        showAllOrder();
                        break;
                    case 2:
                        showOrderByCode((byte) 0);
                        break;
                    case 3:
                        showOrderByCode((byte) 1);

                        break;
                    case 4:
                        showOrderByCode((byte) 2);
                        break;
                    case 5:
                        showOrderDetail();
                        break;
                    case 6:
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
                        showOrderByCode((byte) 0);
                        break;
                    case 3:
                        showOrderByCode((byte) 1);
                        break;
                    case 4:
                        showOrderByCode((byte) 2);
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
        List<Order> orderList = orderController.findOrderByUserId();
        if (orderList.isEmpty()) {
            System.err.println("Empty");
            return;
        }
        for (Order order : orderList) {
            System.out.println(order);
        }
    }

    public void showOrderByCode(byte code) {
        orderController = new OrderController();
        // gọi phương thức findOrdeByUserId() để lấy danh sách các đơn hàng của user thông qua UserId
        List<Order> orders = orderController.findOrderByUserId();

        // Khởi tạo một danh sách mới tên là filter để lưu trữ các đơn hàng được chọn lọc thông qua code
        List<Order> filter = new ArrayList<>();
        //  Dùng vòng lặp for để duyệt qua từng đơn hàng của user thông qua code
        // nếu status của order đó == với code của mình truyền vào thì add nó vào filter
        for (Order o : orders
        ) {
            if (o.getStatus() == code) {
                filter.add(o);
            }
        }

        // sau khi kết thúc vòng lặp, nếu danh sách fillter rỗng
        // -> in ra thông báo rỗng và dừng chương trình
        if (filter.isEmpty()) {
            System.err.println("Order is empty");
            return;
        }
        // Nếu danh sach không rỗng, nó lặp qua mỗi đơn hàng trong danh sách filter và
        // in ra thông tin của đơn hàng.
        for (Order o : filter
        ) {
            System.out.println(o);
        }
    }

    public void showOrderDetail() {
        // Yêu cầu người dùng nhâp id đơn hàng
        System.out.println("Enter order ID");
        int orderId = InputMethods.getInteger();
        // tìm đơn hàng tương ứng bằng id tương ứng và phương thức findById
        Order order = orderController.findById(orderId);
        // Nếu order bằng null -> không có sản phẩm, thì in ra thông báo lỗi
        if (order == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }

        // Nếu hoá đơn không rỗng, in ra chi tiet hoa don
        System.out.printf("---------------------OrderDetail-----------------------\n");
        System.out.printf("                    Id:%5d                              \n", order.getId());
        System.out.println("--------------------Infomation--------------------------");
        System.out.println("Receiver: " + order.getReceiver() + "| Phone : " + order.getPhoneNumber() + "\n");
        System.out.println("Address : " + order.getAddress());
        System.out.println("---------------------Detail--------------------------");
        for (CartItem ci : order.getOrderDetail()
        ) {
            System.out.println(ci);
        }
        System.out.println("Total : " + order.getTotalPrice());
        System.out.println("---------------------END------------------");
        if (order.getStatus() == 0) {
            System.out.println("Do you want to cancel this order?");
            System.out.println("1. yes");
            System.out.println("2. no");
            System.out.println("Enter your choice");
            int choice = InputMethods.getByte();
            if (choice == 1) {
                // huỷ
                order.setStatus((byte) 2);
                orderController.save(order);
            }
        }

    }

    public void orderAdmin(Order order, User orderUser) {
        System.out.println("=======================");
        System.out.println("ID: " + order.getId() + " | Date: " + order.getBuyDate());
        System.out.println("Total: " + order.getTotalPrice() + " | Status: " + Message.getStatusByCode(order.getStatus()));
        System.out.println("Receiver: " + order.getReceiver());
        System.out.println("Phone number: " + order.getPhoneNumber());
        System.out.println("Address: " + order.getAddress());
    }

    public void changeOfStatus() {
        System.out.println("Enter order ID: ");
        int changeOrderStatusId = InputMethods.getInteger();
        Order changeOrderStatus = orderController.findById(changeOrderStatusId);
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
            } else if (choice == 2) {
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
                System.err.println("Please enter from 1 to 3");
            }
        }
    }


}

