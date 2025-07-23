package com.example.prn392_project.bill;

import static com.example.prn392_project.checkout.CheckoutFragment.KEY_BUY_NOW_ITEM;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;
import com.example.prn392_project.cart.CartViewModel;
import com.example.prn392_project.data_classes.CartDataAdapter;
import com.example.prn392_project.data_classes.CartItem;
import com.example.prn392_project.email_sender.EmailSender;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BillFragment extends Fragment {

    public static final String KEY_CUST_NAME = "CustomerName";
    public static final String KEY_CUST_PHONE = "CustomerPhone";
    public static final String KEY_CUST_ADDR = "CustomerAddress";
    String custName = "";
    String custPhone = "";
    String custAddr = "";

    private List<CartItem> cartItemList;
    private CartViewModel mViewModel;
    public BillAdapter adapter;
    private RecyclerView rvBillItems;
    private CartItem buyNowItem;

    // TODO: Rename and change types and number of parameters
    public static BillFragment newInstance() {
        return new BillFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this.getActivity()).get(CartViewModel.class);

        ImageButton btnBack = view.findViewById(R.id.btnBillBack);
        TextView tvBillItemCount = view.findViewById(R.id.tvBillItemCount);
        TextView tvBillTotalPrice = view.findViewById(R.id.tvBillTotalPrice);
        TextView tvCustName = view.findViewById(R.id.tvCustName);
        TextView tvCustPhone = view.findViewById(R.id.tvCustPhone);
        TextView tvCustAddr = view.findViewById(R.id.tvCustAddr);
        rvBillItems = view.findViewById(R.id.rvBillProduct);
        rvBillItems.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = getArguments();
        if (bundle != null) {
            custName = bundle.getString(KEY_CUST_NAME);
            custPhone = bundle.getString(KEY_CUST_PHONE);
            custAddr = bundle.getString(KEY_CUST_ADDR);

            tvCustName.setText(custName);
            tvCustPhone.setText(custPhone);
            tvCustAddr.setText(custAddr);
        }

        CartItem cartItem = null;
        if (bundle.containsKey(KEY_BUY_NOW_ITEM)) {
            cartItem = (CartItem) bundle.getSerializable(KEY_BUY_NOW_ITEM);
        }


        //check comment in CheckoutFragment, it's the same thing
        if (cartItem == null) {
            var cartItemList = mViewModel.getCartItems().getValue();
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
                tvBillTotalPrice.setText(currencyFormatter.format(totalPrice));

                adapter = new BillAdapter(BillFragment.this, cartItemList);
                rvBillItems.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                tvBillItemCount.setText("" + totalProductCount);
                EmailSender.sendEmail(custAddr, "Checkout Billing", custName, custPhone, custAddr, cartItemList);

                mViewModel.clearCartItems();
            }
        } else {
            var count = cartItem.getQuantity();
            var totalPrice = count * cartItem.getProduct().getProductPrice();

            adapter = new BillAdapter(BillFragment.this, new ArrayList<>(Collections.singletonList(cartItem)));
            rvBillItems.setAdapter(adapter);

            Locale vietnamLocale = new Locale("vi", "VN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);
            tvBillTotalPrice.setText(currencyFormatter.format(totalPrice));

            buyNowItem = new CartItem(
                    cartItem.getProduct(),
                    cartItem.getSize(),
                    cartItem.getQuantity()
            );
            cartItemList = new ArrayList<>(Collections.singletonList(cartItem));

            tvBillItemCount.setText("" + count);
            EmailSender.sendEmail(custAddr, "Checkout Billing", custName, custPhone, custAddr, cartItemList);
        }


        // Handles back button
        btnBack.setOnClickListener(v -> {
            var mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
//            mainActivity.goBack();
            mainActivity.navigate(R.id.action_billFragment_to_storeFragment, null, true);
        });
    }
}