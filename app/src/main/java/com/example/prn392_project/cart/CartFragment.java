package com.example.prn392_project.cart;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;
import com.example.prn392_project.data_classes.CartDataAdapter;
import com.example.prn392_project.data_classes.CartItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    private static final String KEY_PRODUCT = "Product";

    private static final String KEY_SIZE = "Size";

    private static final String KEY_QUANTITY = "Quantity";

    private CartViewModel mViewModel;
    private List<CartItem> cartItemList;

    private TextView tvProductCount;
    private TextView tvTotalPrice;
    private RecyclerView rvCartItems;
    public CartDataAdapter dataAdapter;


    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this.getActivity()).get(CartViewModel.class);
        // TODO: Use the ViewModel

        // Setup references
        ImageButton btnBack = view.findViewById(R.id.btnCheckoutBack);
        Button btnCheckout = view.findViewById(R.id.btnCartCheckout);
        tvProductCount = view.findViewById(R.id.tvCartItemCount);
        tvTotalPrice = view.findViewById(R.id.tvCartTotalPrice);
        rvCartItems = view.findViewById(R.id.rvCartItems);
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));

        // Handles back button
        btnBack.setOnClickListener(v -> {
            var mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.goBack();
        });

        // Handles checkout button
        btnCheckout.setOnClickListener(v -> {
            // TODO: Implement checkout=
            var mainActivity = (MainActivity) requireActivity();
            mainActivity.navigate(R.id.action_cartFragment_to_checkoutFragment);
        });

        // Setup temp data
        cartItemList = mViewModel.getCartItems().getValue();

        dataAdapter = new CartDataAdapter(CartFragment.this, cartItemList);
        rvCartItems.setAdapter(dataAdapter);
        notifyItemChanged(-1);
    }

    public void notifyItemChanged(int position) {
        this.rvCartItems.post(() ->dataAdapter.notifyItemChanged(position));
        int totalProductCount = 0;
        int totalPrice = 0;
        for (var item : cartItemList) {
            totalPrice += item.getQuantity() * item.getProduct().getProductPrice();
            totalProductCount += item.getQuantity();
        }

        // Format to Vietnamese Dong
        Locale vietnamLocale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);
        tvTotalPrice.setText(currencyFormatter.format(totalPrice));

        tvProductCount.setText("" + totalProductCount);
    }

    public void notifyItemRemoved(int _position) {
        this.rvCartItems.post(() ->dataAdapter.notifyDataSetChanged());
        int totalProductCount = 0;
        int totalPrice = 0;
        for (var item : cartItemList) {
            totalPrice += item.getQuantity() * item.getProduct().getProductPrice();
            totalProductCount += item.getQuantity();
        }

        // Format to Vietnamese Dong
        Locale vietnamLocale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);
        tvTotalPrice.setText(currencyFormatter.format(totalPrice));

        tvProductCount.setText("" + totalProductCount);
    }
}