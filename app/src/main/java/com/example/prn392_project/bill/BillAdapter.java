package com.example.prn392_project.bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prn392_project.R;
import com.example.prn392_project.data_classes.Bill;
import com.example.prn392_project.data_classes.CartItem;
import com.example.prn392_project.data_classes.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    private BillFragment context;
    private List<CartItem> list;

    public BillAdapter(BillFragment context, List<CartItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getContext()).inflate(R.layout.item_bill_product, parent, false);
        return new BillAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = list.get(position);
        Product product = cartItem.getProduct();
        var currentIndex = holder.getAdapterPosition();

        holder.tvName.setText(product.getProductName());
        holder.imgProduct.setImageResource(product.getProductImageId());
        holder.tvProductSize.setText(cartItem.getSize());

        // Format to Vietnamese Dong
        Locale vietnamLocale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);
        holder.tvTotalPrice.setText(currencyFormatter.format(product.getProductPrice()));

        var currentQuantity = cartItem.getQuantity();
        holder.tvQuantity.setText(Integer.toString(currentQuantity));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvTotalPrice;
        TextView tvProductSize;
        ImageView imgProduct;
        TextView tvQuantity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBillProductName);
            tvTotalPrice = itemView.findViewById(R.id.tvBillProductPrice);
            tvProductSize = itemView.findViewById(R.id.tvBillProductSize);
            imgProduct = itemView.findViewById(R.id.imgBillProductImage);
            tvQuantity = itemView.findViewById(R.id.tvBillProductQuantity);
        }
    }
}
