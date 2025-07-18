package com.example.prn392_project.bill;

import androidx.recyclerview.widget.RecyclerView;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    private Context context;
    private List<Bill> billList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgProduct;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
