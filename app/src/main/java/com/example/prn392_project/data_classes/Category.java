package com.example.prn392_project.data_classes;

import com.example.prn392_project.R;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int CategoryId;
    private String CategoryName;
    private int CategoryImageId;

    public Category(int categoryId, String categoryName, int categoryImageId) {
        CategoryId = categoryId;
        CategoryName = categoryName;
        CategoryImageId = categoryImageId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getCategoryImageId() {
        return CategoryImageId;
    }

    public void setCategoryImageId(int categoryImageId) {
        CategoryImageId = categoryImageId;
    }

    public static List<Category> GetAllCategories() {
        var list = new ArrayList<Category>();
        list.add(new Category(1, "T-Shirt", R.drawable.categort_tshirt));
        list.add(new Category(2, "Polo", R.drawable.categort_polo));
        list.add(new Category(3, "Jacket", R.drawable.categort_jacket));
        list.add(new Category(4, "Hoodie", R.drawable.categort_hoodie));
        list.add(new Category(5, "Pants", R.drawable.categort_pants));
        list.add(new Category(6, "Shorts", R.drawable.categort_shorts));
        list.add(new Category(7, "Shoes", R.drawable.categort_shoes));
        list.add(new Category(8, "All Products", R.drawable.categort_all));

        return list;
    }
}
