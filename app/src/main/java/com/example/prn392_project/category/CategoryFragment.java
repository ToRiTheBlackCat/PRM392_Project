package com.example.prn392_project.category;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.prn392_project.IProductListFragment;
import com.example.prn392_project.R;
import com.example.prn392_project.data_classes.ProductDataAdapter;
import com.example.prn392_project.data_classes.Product;
import com.example.prn392_project.database_helper.ProductDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryFragment extends Fragment implements IProductListFragment {
    public static final String KEY_CATEGORY = "CategoryId";
    public static final String KEY_PRODUCT_NAME = "ProductName";
    public static final String KEY_PRODUCT_MAX_PRICE = "ProductMaxPrice";
    public static final String KEY_PRODUCT_MIN_PRICE = "ProductMinPrice";

    public CategoryViewModel mViewModel;
    private List<Product> productList;
    private RecyclerView rvProducts;
    private ProductDataAdapter dataAdapter;
    private ProductDatabaseHelper databaseHelper;
    private ExecutorService executorService;
    EditText etFilterName;

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
        etFilterName = view.findViewById(R.id.etCategorySearchName);
        executorService = Executors.newSingleThreadExecutor();

        // Init list
        databaseHelper = new ProductDatabaseHelper(this.getContext());
        productList = databaseHelper.getAllProducts();

        dataAdapter = new ProductDataAdapter(CategoryFragment.this, productList);
        rvProducts.setAdapter(dataAdapter);

        // Set pass-in arguments
        Bundle bundle = getArguments();
        int categoryId = -1;
        int maxPrice = -1;
        int minPrice = 0;
        if (bundle != null) {
            var name = bundle.getString(KEY_PRODUCT_NAME);
            name = name != null ? name : "";
            categoryId = bundle.getInt(KEY_CATEGORY);
            etFilterName.setText(name);
            maxPrice = bundle.getInt(KEY_PRODUCT_MAX_PRICE);
            minPrice = bundle.getInt(KEY_PRODUCT_MIN_PRICE);

            filterProducts(name, categoryId, minPrice, maxPrice);
        }


        // Handles filter input
        // TODO: Implement product filtering
        int finalCategoryId = categoryId;
        int finalMinPrice = minPrice;
        int finalMaxPrice = maxPrice;
        etFilterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterProducts(s.toString(), finalCategoryId, finalMinPrice, finalMaxPrice);
            }
        });
    }

    private void filterProducts(String filter, int categoryId, int minPrice, int maxPrice) {
        executorService.execute(() -> {
            String[] words = filter.trim().toLowerCase().split("\\s+");

            List<Product> filteredList = new ArrayList<>();
            List<Product> originalList = databaseHelper.getAllProducts();

            for (Product product : originalList) {
                boolean nameMatches = false;

                for (String word : words) {
                    if (product.getProductName().toLowerCase().contains(word)) {
                        nameMatches = true;
                        break;
                    }
                }

                boolean categoryMatches = (categoryId == 8) || product.getCategoryId() == categoryId;
                boolean priceMatches = product.getProductPrice() >= minPrice && product.getProductPrice() <= maxPrice;

                if (nameMatches && categoryMatches && priceMatches) {
                    filteredList.add(product);
                }
            }
            dataAdapter.setProductList(filteredList);

            // Update the dataAdapter on the UI thread
            requireActivity().runOnUiThread(() -> {
                dataAdapter.notifyDataSetChanged();
            });
        });
    }


    @Override
    public int GetProductDetailActionId() {
        return R.id.action_categoryFragment_to_productDetailFragment;
    }

    @Override
    public Context GetContext() {
        return this.getContext();
    }
}