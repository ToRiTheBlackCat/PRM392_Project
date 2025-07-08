package com.example.prn392_project.data_classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static List<CartItem> getExampleCartItems() {
        var products = Product.getExampleProducts();
        int minQty = 1;
        int maxQty = 5;
        Random random = new Random();

        List<CartItem> examples = new ArrayList<>();
        for (var item : products){
            int randomQuantity = random.nextInt(maxQty - minQty + 1) + minQty; // from 1 to 5
            CartItem cartItem = new CartItem(item, randomQuantity);
            examples.add(cartItem);
        }

        return examples;
    }
}
