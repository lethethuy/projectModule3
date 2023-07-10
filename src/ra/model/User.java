package ra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;

    private boolean status = true;
    private Set<RoleName> roles = new HashSet<>();
    private String address;
    private List<CartItem> cart = new ArrayList<>();

    public User() {
    }

    // admin
    public User(int id, String name, String username, String password, String address, Set<RoleName> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.roles = roles;
    }


    // user

    public User(int id, String name, String username, String password, String email, String phoneNumber, boolean status, Set<RoleName> roles, String address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.roles = roles;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleName> roles) {
        this.roles = roles;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        String role;
        if (roles.contains(RoleName.ADMIN)) {
            role = "ADMIN";
        } else if (roles.contains(RoleName.MANAGER)) {
            role = "MANAGER";
        } else {
            role = "USER";
        }
        return "ID : " + id + " | Name : " + name + "| Username : " + username + " | Role : " + role + " | Address: " + address + " | Status : " + (status ? "UnLock" : "Lock");
    }
}
