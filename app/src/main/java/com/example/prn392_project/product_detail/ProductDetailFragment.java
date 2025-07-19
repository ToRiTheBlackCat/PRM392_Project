package com.example.prn392_project.product_detail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;
import com.example.prn392_project.cart.CartViewModel;
import com.example.prn392_project.checkout.CheckoutFragment;
import com.example.prn392_project.data_classes.CartItem;
import com.example.prn392_project.data_classes.Product;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailFragment extends Fragment {

    private ProductDetailViewModel mViewModel;
    public static final String KEY_PRODUCT = "Product";

    Spinner spinnerSize;

    public static ProductDetailFragment newInstance() {
        return new ProductDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        // TODO: Use the ViewModel

        // Setup View References
        ImageButton btnBack = view.findViewById(R.id.btnDetailBack);
        TextView tvName = view.findViewById(R.id.tvDetailProductName);
        TextView tvPrice = view.findViewById(R.id.tvDetailProductPrice);
        TextView tvDesc = view.findViewById(R.id.tvDetailProductDesc);
        ImageView imgImage = view.findViewById(R.id.imgDetailProductImage);
        TextView tvCartWarn = view.findViewById(R.id.tvDetailCartWarning);
        Button btnAddCart = view.findViewById(R.id.btnDetailAddCart);
        Button btnBuyNow = view.findViewById(R.id.btnDetailBuyNow);
        spinnerSize = view.findViewById(R.id.spinnerDetailSize);
        Spinner spinnerQuantity = view.findViewById(R.id.spinnerDetailQuantity);
        NumberPicker numpickQuantity = view.findViewById(R.id.numpickDetailQuantity);

        // Add Size & Quantity drop-down options
        String[] sizes = {"S", "M", "L", "XL", "XXL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getContext(), // use getContext() if in Fragment
                android.R.layout.simple_spinner_item,
                sizes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapter);

        // Setup data for Quantity Selector
        numpickQuantity.setMinValue(1);
        numpickQuantity.setMaxValue(10);
        numpickQuantity.setValue(1);

        // Get product data
        CartItem newCartItem = new CartItem(null, "Test", 1);
        Bundle args = getArguments();
        Product product;
        if (args != null) {
            product = (Product) args.getSerializable(KEY_PRODUCT);

            if (product != null) {
                tvName.setText(product.getProductName());
                tvDesc.setText(product.getDescription());
                imgImage.setImageResource(product.getProductImageId());
                // Format to Vietnamese Dong
                Locale vietnamLocale = new Locale("vi", "VN");
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);
                tvPrice.setText(currencyFormatter.format(product.getProductPrice()));

                newCartItem.setProduct(product);
            }
        } else {
            product = null;
        }

        // Handles back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.goBack();
            }
        });

        // Handles spinner size selection
        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (checkExitCartItem(product)) {
                    btnAddCart.setEnabled(false);
                    btnAddCart.setAlpha(0.5f);
                    tvCartWarn.setVisibility(View.VISIBLE);
                } else {
                    btnAddCart.setEnabled(true);
                    btnAddCart.setAlpha(1.0f);
                    tvCartWarn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        if (checkExitCartItem(product)) {
            btnAddCart.setEnabled(false);
            btnAddCart.setAlpha(0.5f);
            tvCartWarn.setVisibility(View.VISIBLE);
        }

        // Handles addToCart button
        btnAddCart.setOnClickListener(v -> {
            if (checkExitCartItem(product))
                return;

            newCartItem.setSize(spinnerSize.getSelectedItem().toString());
            newCartItem.setQuantity(numpickQuantity.getValue());

            CartViewModel cartViewModel = new ViewModelProvider(this.requireActivity()).get(CartViewModel.class);
            cartViewModel.addCartItem(newCartItem);

            // TODO: Implement logic for checking duplicated items (same Product and Size)
            // TODO: <Kiểm tra trong cart đã có sản phẩm này chưa (CartItem có cùng Product và Size), nếu có thì disable button addCart>

            // Navigate to Cart
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.navigate(R.id.cartFragment);
        });

        // Handles BuyNow button
        btnBuyNow.setOnClickListener(v -> {
            // TODO: Implement buy now logic <Đưa duy nhất sản phẩm của detail sang screen checkout>
            // Truyền bằng Bundle với key CheckoutFragment.KEY_BUY_NOW_ITEM
            // Implement buy now logic
            MainActivity mainActivity = (MainActivity) requireActivity();

            newCartItem.setSize(spinnerSize.getSelectedItem().toString());
            newCartItem.setQuantity(numpickQuantity.getValue());
            Bundle bundle = new Bundle();
            bundle.putSerializable(CheckoutFragment.KEY_BUY_NOW_ITEM, newCartItem);

            mainActivity.navigate(R.id.checkoutFragment, bundle);

        });
    }


    public boolean checkExitCartItem(Product product) {
        CartViewModel cartViewModel = new ViewModelProvider(this.requireActivity()).get(CartViewModel.class);
        var carItems = cartViewModel.getCartItems().getValue();
        if (carItems.isEmpty()) {
            return false;
        }

        var currentSize = spinnerSize.getSelectedItem().toString();
        var itemExists = carItems.stream()
            .filter(x ->
                x.getSize().equalsIgnoreCase(currentSize) &&
                x.getProduct().getProductId() == product.getProductId()
            )
            .findFirst();

        return itemExists.isPresent();
    }
}