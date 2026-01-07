package com.example.jewelryvault;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class JewelryAdapter extends RecyclerView.Adapter<JewelryAdapter.ViewHolder> {

    private ArrayList<JewelryModel> jewelryList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, typeTextView, valueTextView;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.jewelryName);
            typeTextView = itemView.findViewById(R.id.jewelryType);
            valueTextView = itemView.findViewById(R.id.jewelryValue);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }
    }

    public JewelryAdapter(ArrayList<JewelryModel> jewelryList) {
        this.jewelryList = jewelryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jewelry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JewelryModel item = jewelryList.get(position);
        holder.nameTextView.setText(item.getName());
        holder.typeTextView.setText("Type: " + item.getType());
        holder.valueTextView.setText("Value: $" + item.getValue());
    }

    @Override
    public int getItemCount() {
        return jewelryList.size();
    }
}