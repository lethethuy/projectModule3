package ra.controller;

import ra.model.CartItem;
import ra.model.User;
import ra.service.CartService;

import java.util.List;

public class CartController {
    private  CartService cartService;

    public CartController(User userLogin) {
        this.cartService = new CartService(userLogin);
    }
//    Trong phương thức khởi tạo CartController,
//    một đối tượng CartService được tạo ra
//    và khởi tạo bằng cách truyền vào một tham số userLogin kiểu User.
//    Điều này cho phép CartController sử dụng CartService để thực hiện các hoạt động trên giỏ hàng của người dùng được đăng nhập.


    public List<CartItem> findAll() {
        return cartService.findAll();
    }


    public void save(CartItem cartItem) {
        cartService.save(cartItem);

    }


    public void delete(Integer id) {
        cartService.delete(id);
    }

    public CartItem findById(Integer id) { // kiem tra trong cart da ton tai hay chua
        return cartService.findById(id);
    }

    public CartItem findByProductId(int productId) { // kiem tra trong cart da ton tai product hay chua
        return cartService.findByProductId(productId);
    }

    public int getNewId() {
        return cartService.getNewId();
    }

    public void clearAll() {
        cartService.clearAll();
    }


}
