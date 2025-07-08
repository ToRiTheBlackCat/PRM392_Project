package com.example.prn392_project.data_classes;

import com.example.prn392_project.R;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int ProductId;
    private String ProductName;
    private int ProductPrice;
    private String Description;
    private int ProductImageId;

    public Product() {
        ProductId = -1;
        ProductName = "Ex ProductName";
        ProductPrice = 999999999;
        ProductImageId = R.drawable.ic_filter;
        Description = "Placeholder Description";
    }

    public Product(String productName, int productPrice, String description, int productImageId) {
        ProductId = -1;
        ProductName = productName;
        ProductPrice = productPrice;
        Description = description;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getProductImageId() {
        return ProductImageId;
    }

    public void setProductImageId(int productImageId) {
        ProductImageId = productImageId;
    }

    public static List<Product> getExampleProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Áo khoác da màu đen", 100000, "Áo khoác da cao cấp, chống gió hiệu quả", R.drawable.ic_filter));
        productList.add(new Product("Giày thể thao nam", 250000, "Giày thể thao êm ái, thích hợp chạy bộ", R.drawable.ic_filter));
        productList.add(new Product("Túi xách nữ da thật", 320000, "Túi xách thời trang bằng da thật, nhiều ngăn tiện lợi", R.drawable.ic_filter));
        productList.add(new Product("Đồng hồ thời trang", 450000, "Đồng hồ dây da sang trọng, chống nước nhẹ", R.drawable.ic_filter));
        productList.add(new Product("Mũ lưỡi trai", 80000, "Mũ vải kaki bền đẹp, thích hợp mùa hè", R.drawable.ic_filter));
        productList.add(new Product("Áo sơ mi trắng", 120000, "Áo sơ mi cotton thoáng mát, phù hợp công sở", R.drawable.ic_filter));
        productList.add(new Product("Quần jean nam", 180000, "Quần jean co giãn, phong cách trẻ trung", R.drawable.ic_filter));
        productList.add(new Product("Áo thun tay ngắn", 95000, "Áo thun cotton 100%, nhiều màu sắc", R.drawable.ic_filter));
        productList.add(new Product("Giày cao gót nữ", 270000, "Giày cao gót 5cm, kiểu dáng thanh lịch", R.drawable.ic_filter));
        productList.add(new Product("Balo học sinh", 150000, "Balo vải dù chống thấm, phù hợp đi học", R.drawable.ic_filter));
        productList.add(new Product("Kính mát thời trang", 110000, "Kính chống UV, kiểu dáng hiện đại", R.drawable.ic_filter));

        return productList;
    }
}
