package com.example.prn392_project.category;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prn392_project.R;
import com.example.prn392_project.data_classes.ProductDataAdapter;
import com.example.prn392_project.data_classes.Product;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    private CategoryViewModel mViewModel;

    private RecyclerView rvProducts;
    private ProductDataAdapter dataAdapter;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        // TODO: Use the ViewModel

        // References Assignment
        rvProducts = view.findViewById(R.id.rvCategoryProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Init list
        var productList = Product.getExampleProducts();

        dataAdapter = new ProductDataAdapter(getContext(), productList);
        rvProducts.setAdapter(dataAdapter);
    }
}