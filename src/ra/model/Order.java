package ra.model;

import ra.config.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class    Order {
    private int id;
    private int useId;
    private double totalPrice;
    private Date buyDate = new Date();
    private String receiver;
    private String phoneNumber;
    private String address;
    private byte status = 0;
    private List<CartItem> orderDetail = new ArrayList<>();

    public Order() {
    }

    public Order(int id, int useId, double totalPrice, Date buyDate, String receiver, String phoneNumber, String address, byte status, List<CartItem> orderDetail) {
        this.id = id;
        this.useId = useId;
        this.totalPrice = totalPrice;
        this.buyDate = buyDate;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public List<CartItem> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<CartItem> orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public String toString() {
        return "ID : " + id + " | Name : " + receiver + " | Total: " + totalPrice + " | Date : " + buyDate + " | Status: " + Message.getStatusByCode(status);
    }
}
