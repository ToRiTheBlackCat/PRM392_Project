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
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;
import com.example.prn392_project.category.CategoryFragment;
import com.example.prn392_project.data_classes.Category;
import com.example.prn392_project.data_classes.ProductDataAdapter;
import com.example.prn392_project.data_classes.Product;
import com.example.prn392_project.database_helper.ProductDatabaseHelper;

import java.util.ArrayList;

public class StoreFragment extends Fragment {
    private StoreViewModel mViewModel;
    private ProductDatabaseHelper databaseHelper;
    private RecyclerView rvProducts;
    private ProductDataAdapter dataAdapter;

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

        databaseHelper = new ProductDatabaseHelper(getContext());

        // References Assignment
        rvProducts = view.findViewById(R.id.rvStoreProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ImageButton btnFilter = view.findViewById(R.id.btnStoreFilter);
        GridLayout gridLayout = view.findViewById(R.id.gridCategories);

        // Init category grid
        for (Category category : Category.GetAllCategories()) {
            View itemView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_category, gridLayout, false);
            itemView.setOnClickListener(v -> {
                var mainActivity = (MainActivity) getActivity();

                CategoryFragment cateFragment = new CategoryFragment();
                mainActivity.setCurrentFragment(cateFragment);
            });

            TextView textView = itemView.findViewById(R.id.tvCategoryName);
            ImageView imageView = itemView.findViewById(R.id.ivCategoryImage);

            textView.setText(category.getCategoryName());
            imageView.setImageResource(category.getCategoryImageId());

            gridLayout.addView(itemView);
        }

        // Init list
        databaseHelper.generateInitData();
        var productList = databaseHelper.getAllUsers();
//        if (productList.isEmpty()) {
//            databaseHelper.generateInitData();
//            productList = databaseHelper.getAllUsers();
//        }

        dataAdapter = new ProductDataAdapter(getContext(), productList);
        rvProducts.setAdapter(dataAdapter);

        // Set when press filter
        btnFilter.setOnClickListener(v -> {
            var mainActivity = (MainActivity) getActivity();

            CategoryFragment cateFragment = new CategoryFragment();
            mainActivity.setCurrentFragment(cateFragment, true);
        });
    }
}