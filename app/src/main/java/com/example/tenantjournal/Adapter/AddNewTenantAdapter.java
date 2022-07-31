package com.example.tenantjournal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tenantjournal.Model.Tenant;
import com.example.tenantjournal.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AddNewTenantAdapter extends RecyclerView.Adapter<AddNewTenantAdapter.ViewHolder> {
    private ArrayList<Tenant> mData;
    private LayoutInflater mInflater;
    private TenantInformationAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public AddNewTenantAdapter(Context context, ArrayList<Tenant> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rent_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tenant tenant = mData.get(position);
        holder.myTextView.setText(tenant.getFullName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

//        String getItem(int id) {
//            // return mData.get(id);
//            return mData.get(id);
//        }
    }

    // convenience method for getting data at click position


    // allows clicks events to be caught
    public void setClickListener(TenantInformationAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
