package com.example.prn392_project.bill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;

public class BillFragment extends Fragment {

    public static final String KEY_CUST_NAME = "CustomerName";
    public static final String KEY_CUST_PHONE = "CustomerPhone";
    public static final String KEY_CUST_ADDR = "CustomerAddress";
    String custName = "";
    String custPhone = "";
    String custAddr = "";

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

        ImageButton btnBack = view.findViewById(R.id.btnBillBack);
        TextView tvBillItemCount = view.findViewById(R.id.tvBillItemCount);
        TextView tvBillTotalPrice = view.findViewById(R.id.tvBillTotalPrice);
        TextView tvCustName = view.findViewById(R.id.tvCustName);
        TextView tvCustPhone = view.findViewById(R.id.tvCustPhone);
        TextView tvCustAddr = view.findViewById(R.id.tvCustAddr);

        Bundle bundle = getArguments();
        if(bundle != null){
            custName = bundle.getString(KEY_CUST_NAME);
            custPhone = bundle.getString(KEY_CUST_PHONE);
            custAddr = bundle.getString(KEY_CUST_ADDR);
        }

        // Handles back button
        btnBack.setOnClickListener(v -> {
            var mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.goBack();
        });
    }


}