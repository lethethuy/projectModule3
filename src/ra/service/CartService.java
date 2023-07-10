package ra.service;

import ra.model.CartItem;
import ra.model.User;

import java.util.ArrayList;
import java.util.List;

public class CartService implements IGenericService<CartItem, Integer> {

    private User userLogin;
    // userLogin được sử dụng để lưu trữ thông tin về người dùng đang đăng nhập
    // đây có thể đây có thể là đối tượng User đại diện cho người dùng hoặc thông tin đăng nhập của người dùng

    private UserService userService;
    // userService được sử dụng để thực hiện các hoạt động liên quan đến người dùng
    // chẳng hạn như việc lưu trữ thông tin người dùng vào cơ sở dữ liệu


    //Cả hai biến userLogin và userService đều được sử dụng trong phương thức CartService
    // để thực hiện các hoạt động trên giỏ hàng, chẳng hạn như thêm, xoá và lưu giỏ hàng của người dùng.

    public CartService(User userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public List<CartItem> findAll() {
        return userLogin.getCart();
    }

    @Override
    public void save(CartItem cartItem) {
        userService = new UserService();
        List<CartItem> cart = userLogin.getCart();
        // tao ra 1 cartItem moi

        if (findById(cartItem.getId()) == null) { // kiem tra trong gio hang da co hay chua
            // neu chua co, them moi
            CartItem ci = findByProductId(cartItem.getProduct().getProductId());

            if (ci != null) {
                // san pham da co trong gio hang
                ci.setQuantity(ci.getQuantity() + cartItem.getQuantity());
            } else {
                // san pham chua co trong gio hang
                cart.add(cartItem);
            }
        } else {
            // sua gio hang
            // truy cap vao car muon sua
            // su dung phuong phap set truyen vao thang id can sua, thang them vao
            // tim thang can sua bang findById thong qua
            cart.set(cart.indexOf(findById(cartItem.getId())), cartItem);
            // cart.set(id, gia tri) -> id = indexOf,  gia tri = cartItem
            // indexOf(element) -> id
            // findById(id) -> element

        }
        userService.save(userLogin);
    }

    @Override
    public void delete(Integer id) {
        userLogin.getCart().remove(findById(id));
        userService.save(userLogin);

    }

    @Override
    public CartItem findById(Integer id) { // kiem tra trong cart da ton tai hay chua
        for (CartItem ci : userLogin.getCart()
        ) {
            if (ci.getId() == id) {
                return ci;
            }
        }
        return null;
    }

    public CartItem findByProductId(int productId) { // kiem tra trong cart da ton tai product hay chua

        for (CartItem ci : userLogin.getCart()
        ) {
            if (ci.getProduct().getProductId() == productId) {
                return ci;
            }
        }
        return null;
    }

    public int getNewId() {
        // id tự tăng
        int max = 0;
        for (CartItem ci : userLogin.getCart()
        ) {
            // Duyệt từng thằng và lấy id trong từng thằng
            // Nếu id > max, gán nó lại bằng max
            if (ci.getId() > max) {
                max = ci.getId();
            }
        }
        return max + 1; // -> id tự tăng là max +1
    }

    public void clearAll() {
        userService = new UserService();
        // xoá tất cả các item trong product
        // xoá bằng cách tạo setCart bằng cách tạo new ArrayList
        userLogin.setCart(new ArrayList<>());
        // lưu lại bằng cách
        userService.save(userLogin);
    }


}
