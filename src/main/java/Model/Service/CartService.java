package Model.Service;

import Model.Object.Cart;
import Model.Object.CartItem;

import java.util.Map;

public class CartService {

    public void addToCart(Cart cart, CartItem newItem) {
        Map<Integer, CartItem> items = cart.getCartItems();

        if (items.containsKey(newItem.getProductId())) {
            CartItem item = items.get(newItem.getProductId());
            item.setQuantity(item.getQuantity() + 1);
        } else {
            items.put(newItem.getProductId(), newItem);
        }
    }

    public void increaseQuantity(Cart cart, int productId) {
        CartItem item = cart.getCartItems().get(productId);
        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    public void decreaseQuantity(Cart cart, int productId) {
        CartItem item = cart.getCartItems().get(productId);
        if (item != null && item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        }
    }

    public void removeItem(Cart cart, int productId) {
        cart.getCartItems().remove(productId);
    }

    public double getTotalPrice(Cart cart) {
        double total = 0;
        for (CartItem item : cart.getCartItems().values()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public int getTotalQuantity(Cart cart) {
        int total = 0;
        for (CartItem item : cart.getCartItems().values()) {
            total += item.getQuantity();
        }
        return total;
    }

    public void updateQuantity(Cart cart, int productId, int quantity) {
        if (quantity < 1) {
            quantity = 1;
        }

        CartItem item = cart.getCartItems().get(productId);

        if (item != null) {
            item.setQuantity(quantity);
        }
    }
}
