package com.example.prn392_project.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prn392_project.data_classes.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>(new ArrayList<>());
    //private final MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>(new ArrayList<>(CartItem.getExampleCartItems()));

    public LiveData<List<CartItem>> getCartItems() {
//        if (this.cartItems.getValue().isEmpty()) {
//            setCartItems(CartItem.getExampleCartItems());
//        }

        return this.cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems.setValue(cartItems);
    }

    public void addCartItem(CartItem newCartItem) {
        List<CartItem> current = new ArrayList<>(cartItems.getValue());
        current.add(newCartItem);
        this.cartItems.setValue(current);
    }

    public void updateCartItem(CartItem cartItem) {
        List<CartItem> current = new ArrayList<>(this.cartItems.getValue());

        for (CartItem item : current) {
            var product = item.getProduct();
            if (product.getProductId() == cartItem.getProduct().getProductId()) {
                if (item.getSize().equals(cartItem.getSize())) {
                    item.setSize(cartItem.getSize());
                    item.setQuantity(cartItem.getQuantity());

                    this.cartItems.setValue(current);
                    return;
                }
            }
        }
    }

    public void removeCartItem(CartItem cartItem) {
        List<CartItem> current = new ArrayList<>(this.cartItems.getValue());

        for (CartItem item : current) {
            if (item.getProduct().getProductId() == cartItem.getProduct().getProductId()) {
                current.remove(item);
                this.cartItems.setValue(current);
                return;
            }
        }
    }
}
