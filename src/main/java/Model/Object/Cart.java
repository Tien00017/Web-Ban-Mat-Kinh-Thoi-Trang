package Model.Object;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    private Map<Integer,CartItem> cartItems = new HashMap<Integer,CartItem>();
    private User user;

    public Cart() {
    }

    public Cart(Map<Integer, CartItem> cartItems, User user) {
        this.cartItems = cartItems;
        this.user = user;
    }

    public Map<Integer, CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Integer, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
