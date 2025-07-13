package com.example.prn392_project.data_classes;

import com.example.prn392_project.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    private int ProductId;
    private String ProductName;
    private int ProductPrice;
    private String Description;
    private int CategoryId;
    private int ProductImageId;

    public Product() {
        ProductId = -1;
        ProductName = "Ex ProductName";
        ProductPrice = 999999999;
        ProductImageId = R.drawable.ic_filter;
        Description = "Placeholder Description";
        CategoryId = -1;
    }

    public Product(String productName, int productPrice, String description, int categoryId, int productImageId) {
        ProductId = -1;
        ProductName = productName;
        ProductPrice = productPrice;
        Description = description;
        CategoryId = categoryId;
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

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getProductImageId() {
        return ProductImageId;
    }

    public void setProductImageId(int productImageId) {
        ProductImageId = productImageId;
    }

    public static List<Product> getExampleProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Áo thun trắng trơn", 80000, "Chất liệu cotton 100%, thấm hút mồ hôi tốt", 1, R.drawable.ao_thun_trang));
        productList.add(new Product("Áo thun in hình mèo", 95000, "Áo thun unisex, họa tiết dễ thương", 1, R.drawable.ao_thun_meo));
        productList.add(new Product("Áo thun thể thao nam", 120000, "Vải co giãn, phù hợp hoạt động thể chất", 1, R.drawable.ao_thun_the_thao));

        productList.add(new Product("Áo polo đen đơn giản", 130000, "Phong cách lịch sự, chất vải mềm mại", 2, R.drawable.ao_polo_den));
        productList.add(new Product("Áo polo sọc caro", 140000, "Họa tiết trẻ trung, phù hợp đi chơi", 2, R.drawable.ao_polo_caro));
        productList.add(new Product("Áo polo công sở", 160000, "Form đứng, thích hợp đi làm", 2, R.drawable.ao_polo_cong_so));

        productList.add(new Product("Áo khoác jean xanh", 200000, "Kiểu dáng năng động, hợp thời trang", 3, R.drawable.ao_khoac_jean_xanh));
        productList.add(new Product("Áo khoác da màu đen", 250000, "Áo khoác da cao cấp, chống gió hiệu quả", 3, R.drawable.ao_khoac_da_den));
        productList.add(new Product("Áo khoác gió 2 lớp", 220000, "Chống thấm nhẹ, thích hợp trời lạnh", 3, R.drawable.ao_gio_2_lop));
        productList.add(new Product("Áo khoác bomber nam", 210000, "Thiết kế cá tính, phong cách đường phố", 3, R.drawable.ao_khoac_bomber));

        productList.add(new Product("Hoodie nỉ trơn", 170000, "Mềm mịn, giữ ấm tốt", 4, R.drawable.hoodie_ni_tron));
        productList.add(new Product("Hoodie có mũ in logo", 185000, "Thời trang đường phố, unisex", 4, R.drawable.hoodie_co_mu_logo));
        productList.add(new Product("Hoodie oversize", 190000, "Form rộng, thoải mái cho mọi dáng người", 4, R.drawable.hoodie_oversize));

        productList.add(new Product("Quần jean rách gối", 150000, "Chất jean bền, phong cách cá tính", 5, R.drawable.jean_rach_goi));
        productList.add(new Product("Quần kaki ống đứng", 160000, "Thích hợp công sở hoặc đi chơi", 5, R.drawable.jean_kaki_ong_dung));
        productList.add(new Product("Quần thể thao nam", 120000, "Co giãn, thoáng khí", 5, R.drawable.quan_the_thao_nam));

        productList.add(new Product("Quần short kaki", 110000, "Ngắn vừa phải, thích hợp mùa hè", 6, R.drawable.short_kaki));
        productList.add(new Product("Quần short jean nữ", 115000, "Thiết kế trẻ trung, năng động", 6, R.drawable.short_jean_nu));
        productList.add(new Product("Quần short thể thao", 105000, "Chất thun lạnh, mặc đi tập gym", 6, R.drawable.short_the_thao));

        productList.add(new Product("Giày sneaker trắng", 300000, "Thiết kế basic, dễ phối đồ", 7, R.drawable.sneaker_trang));
        productList.add(new Product("Giày thể thao chạy bộ", 320000, "Đế êm, hỗ trợ vận động", 7, R.drawable.giay_the_thao_chay_bo));
        productList.add(new Product("Giày boot nam", 350000, "Phong cách mạnh mẽ, lịch lãm", 7, R.drawable.boot_name));
        productList.add(new Product("Giày slip-on đen", 280000, "Dễ mang, phù hợp đi làm và đi chơi", 7, R.drawable.slip_on_den));

        return productList;
    }
}
