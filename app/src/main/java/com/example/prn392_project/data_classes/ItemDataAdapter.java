package com.example.prn392_project.data_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prn392_project.R;

import java.util.List;
import java.util.Locale;

public class ItemDataAdapter extends RecyclerView.Adapter<ItemDataAdapter.ViewHolder> {
    private Context context;
    private List<Product> productList;

    public ItemDataAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getProductName());
        holder.tvPrice.setText(String.format(Locale.ENGLISH,"%d d", product.getProductPrice()));
        holder.imgProduct.setImageResource(product.getProductImageId());

        // Click vào item để mở DetailActivity
        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, DetailActivity.class);
//            intent.putExtra("product", product);
//            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
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


