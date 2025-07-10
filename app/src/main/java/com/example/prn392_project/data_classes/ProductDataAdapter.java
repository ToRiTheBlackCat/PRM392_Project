package com.example.prn392_project.data_classes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prn392_project.IProductListFragment;
import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;
import com.example.prn392_project.product_detail.ProductDetailFragment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductDataAdapter extends RecyclerView.Adapter<ProductDataAdapter.ViewHolder> {
    private IProductListFragment context;
    private List<Product> productList;

    public ProductDataAdapter(IProductListFragment context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.GetContext()).inflate(R.layout.item_store_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getProductName());
        holder.imgProduct.setImageResource(product.getProductImageId());

        // Format to Vietnamese Dong
        Locale vietnamLocale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnamLocale);
        holder.tvPrice.setText(currencyFormatter.format(product.getProductPrice()));

        // Click vào item để mở DetailActivity
        holder.itemView.setOnClickListener(v -> {
            var mainActivity = (MainActivity) context.GetContext();

            Bundle args = new Bundle();
            args.putSerializable(ProductDetailFragment.KEY_PRODUCT, product);

            mainActivity.navigate(context.GetProductDetailActionId(), args);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPrice;
        ImageView imgProduct;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProducName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            imgProduct = itemView.findViewById(R.id.ivProductImage);
        }
    }
}


