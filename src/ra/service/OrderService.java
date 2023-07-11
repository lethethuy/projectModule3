package ra.service;

import ra.model.CartItem;
import ra.model.Order;
import ra.database.DataBase;
import ra.view.Main;

import java.util.ArrayList;
import java.util.List;

import static ra.view.Main.userLogin;

public class OrderService {
    private DataBase<Order> orderDataBase;
    private List<Order> orders;

    public OrderService() {
        orderDataBase = new DataBase<>();
        List<Order> orderList = orderDataBase.readFromFile(DataBase.ORDER_PATH);
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        this.orders = orderList;
    }

    public List<Order> findAll() {
        return this.orders;
    }


    // tìm ra tất cả các order dựa theo idUser
    public List<Order> findOrderByUserId() {
        // tao ra biến findList để lưu trữ các order
        List<Order> findList = new ArrayList<>();
        for (Order o : orders
        ) {
            // Dùng foreach duyệt qua tất cả các đơn hàng
            // Nếu ID user của đơn hàng = với Id của người đang đăng nhập thì add vào biến findList vừa tạo ở trên
            if (o.getUseId() == userLogin.getId()) {
                findList.add(o);
            }
        }
        return findList;
    }


    // Tìm kiếm order dựa trên IdProduct
    public Order findById(int id) {
        //  phương thức findOrderByUserId để lấy danh sách các đơn hàng đang đăng nhập
        // sử dụng foreach để duyệt qua từng đối tượng Order trong danh sách của người dùng

        for (Order o : findOrderByUserId()
        ) {
            // trong mỗi lần lặp, kiểm tra nếu Id của đơn hàng trùng với id được truyền vào
            if (o.getId() == id) {
                // nếu điều kiện đúng  => tìm thấy đơn hàng có id tương ứng
                return o; // =< trả về đối tượng Order đó
            }
        }
        return null; // => ko thấy, trả về null

    }


    // Phuong thuc save  luu tru thong tin ve đơn hàng vào cơ sở dữ liệu
    // Phương thức truyền  một đối tượng order với thông tin cần lưu trữ hoặc cập nhật
    public void save(Order o) {
        if (findById(o.getId()) == null) {
            // sử dụng phương thức findById để tìm kiếm đơn hàng trong danh sách dựa trên id của đơn hàng được truyền vào
            // xem nó có nẳm trong danh sách đơn hàng user đang đăng nhập ko
            // Kiểm tra kết quả tìm kiếm bằng cách so sánh null
            // Nếu null thì kết quả là không tìm thấy trong danh sách đơn hàng user đang đăng nhập
            // Tiến hành thêm mới
            orders.add(o);
        } else {
            // nếu kết quả là có
            // tiến hành update
            // tìm vị trí của nó thông qua indexOf để xác định vị trí trong orders
            // orders.set(vị trí, element thêm mới)
            // indexOf(element) => vị trí index trong danh sách
            // findById(id) => element
            orders.set(orders.indexOf(findById(o.getId())), o);
        }
        orderDataBase.writeToFile(orders,DataBase.ORDER_PATH);

    }


    public int getNewId() {
        int max = 0;
        for (Order o : orders
        ) {
            if (o.getId() > max) {
                max = o.getId();
            }

        }
        return max + 1;

    }
    public Order findByIdForAdmin(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }


}
