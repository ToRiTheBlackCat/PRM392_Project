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

public class CategoryFragment extends Fragment implements IProductListFragment {
    public static final String KEY_CATEGORY = "CategoryId";
    public static final String KEY_PRODUCT_NAME = "ProductName";
    private CategoryViewModel mViewModel;
    private List<Product> productList;
    private RecyclerView rvProducts;
    private ProductDataAdapter dataAdapter;
    private ProductDatabaseHelper databaseHelper;
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

        // Init list
        databaseHelper = new ProductDatabaseHelper(this.getContext());
        productList = databaseHelper.getAllProducts();

        // Set pass-in arguments
        Bundle bundle = getArguments();
        int categoryId = -1;
        if (bundle != null) {
            var name = bundle.getString(KEY_PRODUCT_NAME);
            name = name != null ? name : "";
            categoryId = bundle.getInt(KEY_CATEGORY);
            etFilterName.setText(name);
        }

        dataAdapter = new ProductDataAdapter(CategoryFragment.this, productList);
        rvProducts.setAdapter(dataAdapter);

        // Handles filter input
        // TODO: Implement product filtering
        int finalCategoryId = categoryId;
        etFilterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterProducts(s.toString(), finalCategoryId);
            }
        });
    }

    private void filterProducts(String filter,int categoryId) {
        String[] words = filter.trim().toLowerCase().split("\\s+");

        List<Product> filteredList = new ArrayList<>();
        List<Product> originalList = databaseHelper.getAllProducts();

        // Filter base on name
        for (Product product : originalList) {
            for (var word : words) {
                if(product.getProductName().toLowerCase().contains(word)) {
                    filteredList.add(product);
                }
            }
        }

        // TODO: Filter on other conditions (price range, category) in a popup menu <Hiện popup menu cho người dùng chọn khoảng giá và loại sản phẩm>
        // TODO: (Optional) Order the list (price, name) ascending or descending

        // Update the view
        dataAdapter.setProductList(filteredList);
        dataAdapter.notifyDataSetChanged();
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