package com.example.prn392_project.data_classes;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prn392_project.R;
import com.example.prn392_project.cart.CartFragment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartDataAdapter extends RecyclerView.Adapter<CartDataAdapter.ViewHolder> {
    private CartFragment context;
    private List<CartItem> cartItemList;

    public CartDataAdapter(CartFragment context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartDataAdapter.ViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
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
        holder.etQuantity.setText(Integer.toString(currentQuantity));
        // Click để thêm quantity
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.etQuantity.clearFocus();
                cartItem.setQuantity(currentQuantity + 1);
                context.notifyItemChanged(currentIndex);
            }
        });

        // Click để giảm quantity
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuantity > 1) {
                    holder.etQuantity.clearFocus();
                    cartItem.setQuantity(currentQuantity - 1);
                    context.notifyItemChanged(currentIndex);
                }
            }
        });

        // Click để bỏ product
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.etQuantity.clearFocus();
                cartItemList.remove(currentIndex);
                context.notifyItemRemoved(currentIndex);
            }
        });

        // Input quantity
        holder.etQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    var input = holder.etQuantity.getText().toString();
                    if (!input.isEmpty()) {
                        var quantity = Integer.parseInt(input);
                        if (quantity != cartItem.getQuantity()){
                            cartItem.setQuantity(quantity);
                            context.notifyItemChanged(currentIndex);
                        }
                    }
                }
            }
        });
//        holder.etQuantity.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!s.toString().isEmpty()) {
//                    var quantity = Integer.parseInt(s.toString());
//                    if (quantity != cartItem.getQuantity()){
//                        cartItem.setQuantity(quantity);
//                        context.notifyItemChanged(currentIndex);
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvTotalPrice;
        TextView tvProductSize;
        ImageView imgProduct;
        EditText etQuantity;
        Button btnAdd;
        Button btnMinus;
        Button btnRemove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCartProductName);
            tvTotalPrice = itemView.findViewById(R.id.tvCartProductPrice);
            tvProductSize = itemView.findViewById(R.id.tvCartProductSize);
            imgProduct = itemView.findViewById(R.id.imgCartProductImage);
            etQuantity = itemView.findViewById(R.id.etCartProductQuantity);
            btnAdd = itemView.findViewById(R.id.btnCartAdd);
            btnMinus = itemView.findViewById(R.id.btnCartMinus);
            btnRemove = itemView.findViewById(R.id.btnCartRemove);
        }
    }
}