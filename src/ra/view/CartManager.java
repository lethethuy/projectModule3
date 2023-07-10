package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.controller.CartController;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.model.CartItem;
import ra.model.Order;
import ra.model.Product;
import ra.model.User;


public class CartManager {
    private static CartController cartController;
    private static ProductController productController;

    public CartManager() {
        productController = new ProductController();
        cartController = new CartController(Main.userLogin);
        while (true) {
            System.out.println("-------------Menu-Cart-------------");
            System.out.println("1. Show Cart");
            System.out.println("2. Change quantity");
            System.out.println("3. Delete item");
            System.out.println("4. Delete all");
            System.out.println("5. Check out");
            System.out.println("6. Back");
            System.out.println("Please enter options: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // xem danh sach gio hang
                    showCart();
                    break;
                case 2:
                    // chinh sua so luong gio hang
                    changeQuantity();
                    break;

                case 3:
                    // Delete item
                    deleteItem();
                    break;

                case 4:
                    // Xoa het
                    cartController.clearAll();
                    break;
                case 5:
                    // Xoa het
                    checkout(productController);
                    break;
                case 6:
                    Main.menuUser();
                    break;
                default:
                    System.err.println("Please enter number from 1 to 5");
            }
        }
    }


    // Hien thi tat ca cac san pham trong cart
    public static void showCart() {
        User userLogin = Main.userLogin;
        // đầu tiên nó lấy đối tượng `User` đang đăng nhập từ `Navbar.Login`
        // và gán cho biến `userLogin`

        // tiến hành kiểm tra giỏ hàng của user đó có trống hay ko?
        // bằng cách sử dụng phương thức isEmpty(); duyệt qua từng phần tử
        // nếu nó trống thì in ra dòng chữ Cart is empty và kết thúc bằng lệnh return
        if (userLogin.getCart().isEmpty()) {
            System.err.println("Cart is Empty");
            return;
        }

        // nếu giỏ hàng ko trống duyệt qua từng phần tử và in thông tin về từng mục
        for (CartItem ci : userLogin.getCart()
        ) {
            System.out.println(ci);
        }
    }

    // Thay doi so luong trong cart
    public void changeQuantity() {
        // nhap id cua cart
        System.out.println("Enter cartItemId");
        int cartItemId = InputMethods.getInteger();

        // Khai báo biến cartItem có kiểu trả về là CartItem
        // Dựa vào cartItemId đó để kiểm tra xem có item đó trong giỏ hàng ko
        // bằng cách sử dụng method findById

        CartItem cartItem = cartController.findById(cartItemId);

        // Nếu trả về null thì ko có sản phẩm đó trong cart
        // In ra loi not_found
        // Dừng chạy bằng lệnh return
        if (cartItem == null) {
            System.out.println(Constants.NOT_FOUND);
            return;
        }
        // Nhập số lượng cần tăng thông qua biến quantity
        // cập nhật lại thông tin của số lượng sản phẩm trong cart thông qua
        // phương thức setQuantity()
        System.out.println("Enter Quantity");
        int quantity = InputMethods.getInteger();
        cartItem.setQuantity(quantity);

        //Lưu lại thông tin của cartItem thông qua phương thức save
        cartController.save(cartItem);
    }

    public static void addToCart() {
        cartController = new CartController(Main.userLogin);
        // Khởi tạo 1 đối tượng productcontroller để thực hiện các hoạt
        // động liên quan đến sản phẩm
        ProductController productController = new ProductController();
        System.out.println("Enter Product Id");
        int productId = InputMethods.getInteger();
        // Dựa vào productId vừa cung cấp ở trên
        // tìm phần tử đó qua phương thức findById
        Product pro = productController.findById(productId);

        // nếu trả về bằng null -> không có sản phẩm trong Product
        // lệnh return để dừng
        if (pro == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        // nếu sản phẩm đó trong cart rồi, thì cộng số lượng của nó lên


        // nếu có sản phẩm trong product
        // Khởi tạo đối tượng cartItem

        CartItem cartItem = new CartItem();
        // sử dụng nó để tiến hành thêm sản phẩm mới
        // Lấy Id mới bằng phương thức getNewId
        cartItem.setId(cartController.getNewId());
        // gán sản phẩm pro cho cartItem thông qua phương thức set
        cartItem.setProduct(pro);
        System.out.println("Enter Quantity");
        int quantity = InputMethods.getInteger();
        // lấy giá trị số nguyên từ người dùng nhập vào và hhasn cho thuộc tính quantity
        cartItem.setQuantity(quantity);
        // sử dụng phương thức save để lưu vào giỏ hàng
        cartController.save(cartItem);
    }

    public void deleteItem() {
        // Nhập vào id muốn xoá
        System.out.println("Enter delete Item");
        int cartITemId = InputMethods.getInteger();

        //dua vao id no vừa cung cấp ở trên
        // tìm phần tử qua phương thức findById
        // Nếu không có sản phẩm => trả về null => hiện thông báo not_found
        if (cartController.findById(cartITemId) == null) {
            System.out.println(Constants.NOT_FOUND);
            return;
        }
        // nếu có thì tiến hành xoá thông qua phương thức delete
        cartController.delete(cartITemId);


    }

    public void checkout(ProductController productController) {
        cartController = new CartController(Main.userLogin);
        User userLogin = Main.userLogin;
        if (userLogin.getCart().isEmpty()) {
            System.out.println("Cart is Empty");
            return;
        }

        // kiểm tra số lượng trong kho
        for (CartItem ci : userLogin.getCart()
        ) {
            Product p = productController.findById(ci.getProduct().getProductId());
            if (ci.getQuantity() > p.getStock()) {
                System.err.println(ci.getProduct().getProductName() + " a only has " + p.getStock() + " products. Please try again...");
                return;
            }
        }
        Order newOrder = new Order();
        OrderController orderController = new OrderController();
        newOrder.setId(orderController.getNewId());

        // copy sản phẩm giỏ hàng sang hoá đơn
        newOrder.setOrderDetail(userLogin.getCart());
        // cập nhật tổng tiền
        double total = 0;
        for (CartItem item : userLogin.getCart()
        ) {
            total += (item.getQuantity()) * (item.getProduct().getProductPrice());
        }
        newOrder.setTotalPrice(total);
        newOrder.setUseId(userLogin.getId());
        System.out.println("Enter receiver name");
        newOrder.setReceiver(InputMethods.getString());
        System.out.println("Enter phone number: ");
        newOrder.setPhoneNumber(InputMethods.getString());
        System.out.println("Enter address: ");
        newOrder.setAddress(InputMethods.getString());
        orderController.save(newOrder);
        // gỉam số lượng trong stock
        for(CartItem item:userLogin.getCart()){
            Product product = productController.findById(item.getProduct().getProductId());
            product.setStock(product.getStock() - item.getQuantity());
            productController.save(product);
        }
        cartController.clearAll();

    }
}
