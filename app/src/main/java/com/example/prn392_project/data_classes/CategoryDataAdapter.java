package com.example.prn392_project.data_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prn392_project.MainActivity;
import com.example.prn392_project.R;
import com.example.prn392_project.category.CategoryFragment;

import java.util.List;

public class CategoryDataAdapter extends RecyclerView.Adapter<CategoryDataAdapter.ViewHolder> {
    private Context context;
    private List<Category> categoryList;

    public CategoryDataAdapter(Context context) {
        this.context = context;
        this.categoryList = Category.GetAllCategories();
    }

    @NonNull
    @Override
    public CategoryDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_product, parent, false);
        return new CategoryDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDataAdapter.ViewHolder holder, int position) {
        Category product = categoryList.get(position);
        holder.tvName.setText(product.getCategoryName());
        holder.imgCategory.setImageResource(product.getCategoryImageId());

        // Click vào item để mở DetailActivity
        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, DetailActivity.class);
//            intent.putExtra("product", product);
//            context.startActivity(intent);
            CategoryFragment categoryFragment = CategoryFragment.newInstance();

            var mainActivity = (MainActivity)context;
            mainActivity.setCurrentFragment(categoryFragment);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgCategory;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCategoryName);
            imgCategory = itemView.findViewById(R.id.ivCategoryImage);
        }
    }
}