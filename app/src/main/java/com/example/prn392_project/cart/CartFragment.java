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

    public CartViewModel mViewModel;
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
    public void onStop() {
        super.onStop();

        ((MainActivity)requireActivity()).setCartBtnVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this.requireActivity()).get(CartViewModel.class);

        ((MainActivity)requireActivity()).setCartBtnVisibility(View.INVISIBLE);

        // Setup references
        ImageButton btnBack = view.findViewById(R.id.btnCheckoutBack);
        Button btnCheckout = view.findViewById(R.id.btnCartCheckout);
        tvProductCount = view.findViewById(R.id.tvCartItemCount);

        tvTotalPrice = view.findViewById(R.id.tvCartTotalPrice);
        rvCartItems = view.findViewById(R.id.rvCartItems);
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));

        var cartItems = mViewModel.getCartItems().getValue();
        assert cartItems != null;
        if (cartItems.isEmpty()) {
            btnCheckout.setEnabled(false);
            btnCheckout.setAlpha(0.5f);
        }

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

        // Get cartItems from viewModel
        cartItemList = mViewModel.getCartItems().getValue();
        dataAdapter = new CartDataAdapter(CartFragment.this, cartItemList);
        rvCartItems.setAdapter(dataAdapter);

        // Update UI when ViewModel's CartItems is updated
        mViewModel.getCartItems().observe(getViewLifecycleOwner(), updated -> {
            cartItemList = updated;
            dataAdapter.setCartList(cartItemList);
            notifyItemChanged();
        });
    }

    public void notifyItemChanged() {
        dataAdapter.notifyDataSetChanged();
        int totalProductCount = 0;
        int totalPrice = 0;
        for (var item : cartItemList) {
            totalPrice += item.getQuantity() * item.getProduct().getProductPrice();
            totalProductCount += item.getQuantity();
        }

        // Format to Vietnamese Dong
        tvTotalPrice.setText(String.format("%,d VNĐ", totalPrice));

        tvProductCount.setText("" + totalProductCount);
    }
}