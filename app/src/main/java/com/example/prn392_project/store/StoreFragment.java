package com.example.prn392_project.store;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.prn392_project.IProductListFragment;
import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;
import com.example.prn392_project.category.CategoryFragment;
import com.example.prn392_project.data_classes.Category;
import com.example.prn392_project.data_classes.ProductDataAdapter;
import com.example.prn392_project.database_helper.ProductDatabaseHelper;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class StoreFragment extends Fragment implements IProductListFragment {
    private StoreViewModel mViewModel;
    private ProductDatabaseHelper databaseHelper;
    private RecyclerView rvProducts;
    private ProductDataAdapter dataAdapter;
    private LinearLayout filterPanel;
    private TextView tvMaxPrice;
    private TextView tvMinPrice;
    private int maxPrice = 0;
    private int minPrice = 0;

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
        EditText etName = view.findViewById(R.id.etStoreSearchName);
        ImageView imgBanner = view.findViewById(R.id.imgBanner);
        imgBanner.setImageResource(R.drawable.banner);
        filterPanel = view.findViewById(R.id.filterPanel);
        tvMaxPrice = view.findViewById(R.id.tvMaxPrice);
        tvMinPrice = view.findViewById(R.id.tvMinPrice);
        RangeSlider sliderPriceRange = view.findViewById(R.id.rSliderPriceRance);

        // Setup Initial Price range
        var initialValues = sliderPriceRange.getValues();
        minPrice = (int) (initialValues.get(0) * 100_000);
        maxPrice = (int) (initialValues.get(1) * 100_000);
        tvMinPrice.setText(String.format("%,d VNĐ", minPrice));
        tvMaxPrice.setText(String.format("%,d VNĐ", maxPrice));

        // Handle PriceSlider Range Change
        sliderPriceRange.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> values = slider.getValues();

                minPrice = (int) (values.get(0) * 100_000);
                maxPrice = (int) (values.get(1) * 100_000);
                tvMinPrice.setText(String.format("%,d VNĐ", minPrice));
                tvMaxPrice.setText(String.format("%,d VNĐ", maxPrice));
            }
        });

        //Handle dropdown filterPanel
        btnFilter.setOnClickListener(v -> {
            if (filterPanel.getVisibility() == View.GONE) {
                filterPanel.setVisibility(View.VISIBLE);
            } else {
                filterPanel.setVisibility(View.GONE);
            }
        });

        // Init category grid
        for (Category category : Category.GetAllCategories()) {
            View itemView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_category, gridLayout, false);

            // Handles when a category is chosen
            itemView.setOnClickListener(v -> {
                var mainActivity = (MainActivity) getActivity();

                // Set data to pass into destination fragment
                Bundle bundle = new Bundle();
                int chosenCateId = category.getCategoryId();
                bundle.putInt(CategoryFragment.KEY_CATEGORY, chosenCateId);

                //Add text from search
                String searchText = etName.getText().toString().trim();
                if (!searchText.isEmpty()) {
                    bundle.putString(CategoryFragment.KEY_PRODUCT_NAME, searchText);
                }

                //Add max price from seekbar
                if (maxPrice != -1) {
                    bundle.putInt(CategoryFragment.KEY_PRODUCT_MIN_PRICE, minPrice);
                    bundle.putInt(CategoryFragment.KEY_PRODUCT_MAX_PRICE, maxPrice);
                }

                assert mainActivity != null;
                mainActivity.navigate(R.id.action_storeFragment_to_categoryFragment, bundle);
            });

            TextView textView = itemView.findViewById(R.id.tvCategoryName);
            ImageView imageView = itemView.findViewById(R.id.ivCategoryImage);

            textView.setText(category.getCategoryName());
            imageView.setImageResource(category.getCategoryImageId());

            gridLayout.addView(itemView);
        }

        dataAdapter = new ProductDataAdapter(this, new ArrayList<>());
        // Init list using multithreading
        Executors.newSingleThreadExecutor().execute(() -> {
            databaseHelper.generateInitData();
            var productList = databaseHelper.getAllProducts();
            dataAdapter.setProductList(productList);

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    rvProducts.setAdapter(dataAdapter);
                });
            }
        });
    }

    @Override
    public int GetProductDetailActionId() {
        return R.id.action_storeFragment_to_productDetailFragment;
    }

    @Override
    public Context GetContext() {
        return this.getContext();
    }
}