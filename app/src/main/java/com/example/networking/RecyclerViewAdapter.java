package com.example.networking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Mountain> mountainList;

    public RecyclerViewAdapter(List<Mountain> mountainList) {
        this.mountainList = mountainList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mountainList.get(position).getName());
        holder.size.setText(String.valueOf(mountainList.get(position).getSize()));
    }

    @Override
    public int getItemCount() {
        return mountainList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView size;

        public MyViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.name);
            size = view.findViewById(R.id.size);
        }
    }
}
