package com.example.prn392_project.store;

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
import android.widget.Button;
import android.widget.ImageButton;

import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;
import com.example.prn392_project.category.CategoryFragment;
import com.example.prn392_project.data_classes.ItemDataAdapter;
import com.example.prn392_project.data_classes.Product;

import java.util.ArrayList;

public class StoreFragment extends Fragment {
    private StoreViewModel mViewModel;

    private RecyclerView rvProducts;
    private ItemDataAdapter dataAdapter;

    public static StoreFragment newInstance() {
        return new StoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StoreViewModel.class);

        // References Assignment
        rvProducts = view.findViewById(R.id.rvStoreProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ImageButton btnFilter = view.findViewById(R.id.btnStoreFilter);

        // Init list
        var productList = new ArrayList<Product>();
        productList.add(new Product("Áo thun đen.", 10000, R.drawable.ic_filter));
        productList.add(new Product("Quần jean.", 660000, R.drawable.ic_filter));
        productList.add(new Product("Áo khoác hoodie.", 75000, R.drawable.ic_filter));
        productList.add(new Product("Quần kaki xám.", 90000, R.drawable.ic_filter));
        productList.add(new Product("Áo khoác da đen.", 700000, R.drawable.ic_filter));

        dataAdapter = new ItemDataAdapter(getContext(), productList);
        rvProducts.setAdapter(dataAdapter);

        // Set when press filter
        btnFilter.setOnClickListener(v -> {
            var mainActivity = (MainActivity)getActivity();

            CategoryFragment cateFragment = new CategoryFragment();
            mainActivity.setCurrentFragment(cateFragment);
        });
    }
}