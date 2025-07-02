package com.example.prn392_project.data_classes;

public class Product {
    private int ProductId;
    private String ProductName;
    private int ProductPrice;
    private int ProductImageId;

    public Product(String productName, int productPrice, int productImageId) {
        ProductId = -1;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductImageId = productImageId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public int getProductImageId() {
        return ProductImageId;
    }

    public void setProductImageId(int productImageId) {
        ProductImageId = productImageId;
    }
}
