package com.example.prn392_project.checkout;

import static com.example.prn392_project.bill.BillFragment.KEY_CUST_ADDR;
import static com.example.prn392_project.bill.BillFragment.KEY_CUST_NAME;
import static com.example.prn392_project.bill.BillFragment.KEY_CUST_PHONE;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;
import com.example.prn392_project.cart.CartViewModel;
import com.example.prn392_project.data_classes.CartItem;
import com.example.prn392_project.data_classes.Product;

import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutFragment extends Fragment {

    private CartViewModel cartViewModel;
    public static final String KEY_BUY_NOW_ITEM = "BuyNowCartItem";
    private CartItem buyNowItem;
    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartViewModel = new ViewModelProvider(this.requireActivity()).get(CartViewModel.class);
        // TODO: Use the ViewModel

        EditText etName = view.findViewById(R.id.etCheckoutName);
        EditText etPhone = view.findViewById(R.id.etCheckoutPhone);
        EditText etAddress = view.findViewById(R.id.etCheckoutAddress);
        TextView tvCount = view.findViewById(R.id.tvCheckoutCount);
        TextView tvTotalAmount = view.findViewById(R.id.tvCheckoutTotalAmount);
        Button btnSubmit = view.findViewById(R.id.btnCheckoutSubmit);
        ImageButton btnBack = view.findViewById(R.id.btnCheckoutBack);

        Bundle bundle = getArguments();
        CartItem cartItem = null;
        if (bundle != null) { //this bundle only exist if passed from itemdetail
            cartItem = (CartItem) bundle.getSerializable(KEY_BUY_NOW_ITEM);
        }

        // this part is for if there's no buy now item, get everything in cart instead?
        if (cartItem == null) {
            var cartItemList = cartViewModel.getCartItems().getValue();
            assert cartItemList != null;
            if (!cartItemList.isEmpty()) {
                int totalProductCount = 0;
                int totalPrice = 0;
                for (var item : cartItemList) {
                    totalPrice += item.getQuantity() * item.getProduct().getProductPrice();
                    totalProductCount += item.getQuantity();
                }

                // Format to Vietnamese Dong
                Locale vietnamLocale = new Locale("vi", "VN");
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);
                tvTotalAmount.setText(currencyFormatter.format(totalPrice));

                tvCount.setText("" + totalProductCount);
            }

            //this logic is for buy now item i think?
        } else {
            var count = cartItem.getQuantity();
            var totalPrice = count * cartItem.getProduct().getProductPrice();

            Locale vietnamLocale = new Locale("vi", "VN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);
            tvTotalAmount.setText(currencyFormatter.format(totalPrice));

            buyNowItem = new CartItem(
                    cartItem.getProduct(),
                    cartItem.getSize(),
                    cartItem.getQuantity()
            );
            tvCount.setText("" + count);
        }

        btnSubmit.setOnClickListener(v -> {
            var isValid = true;
            var error = "Required";

            var name = etName.getText().toString();
            var phone = etPhone.getText().toString();
            var address = etAddress.getText().toString();

            if (name.isEmpty()) {
                isValid = false;
                etName.setError(error);
            }
            if (phone.isEmpty()) {
                isValid = false;
                etPhone.setError(error);
            }
            if (address.isEmpty()) {
                isValid = false;
                etAddress.setError(error);
            }

            if (isValid) {
                // TODO: Generate bill
                var mainActivity = (MainActivity) requireActivity();

                Bundle billBundle = new Bundle();
                billBundle.putString(KEY_CUST_NAME, name);
                billBundle.putString(KEY_CUST_PHONE, phone);
                billBundle.putString(KEY_CUST_ADDR, address);
                // If BUY NOW item exist, pass that as well
                if(buyNowItem != null){
                    billBundle.putSerializable(KEY_BUY_NOW_ITEM, buyNowItem);
                }
                // Otherwise, BillFragment will get its list from cart similar to this
                mainActivity.navigate(R.id.action_checkoutFragment_to_billFragment, billBundle);
            }
        });

        btnBack.setOnClickListener(v -> {
            var mainActivity = (MainActivity) requireActivity();
            mainActivity.goBack();
        });
    }

}