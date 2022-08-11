package com.example.tenantjournal.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tenantjournal.Model.NewPayment;
import com.example.tenantjournal.Model.Tenant;
import com.example.tenantjournal.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TenantInformationAdapter extends RecyclerView.Adapter<TenantInformationAdapter.ViewHolder> implements Filterable {
    private ArrayList<Tenant> mData;
    private ArrayList<Tenant> FullList;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    public interface ViewClickListener {

        void onClickView(View view, int position);
        void onClickDelete(View view, int position, ArrayList<Tenant> arrayList);
    }

    ViewClickListener viewClickListener;



    // data is passed into the constructor
    public TenantInformationAdapter(Context context, ArrayList<Tenant> data, ViewClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        FullList = new ArrayList<>(data);
        this.viewClickListener = listener;
        this.context = context;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_list_item, parent, false);
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
        Button btnView, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textview);
            btnView = itemView.findViewById(R.id.bt_view);
            btnDelete = itemView.findViewById(R.id.bt_delete);

            itemView.setOnClickListener(this);

           btnView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   viewClickListener.onClickView(view, getAbsoluteAdapterPosition());
               }
           });

           btnDelete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   new AlertDialog.Builder(context)
                           .setTitle("Confirm")
                           .setMessage("Do you really want to delete "+ mData.get(getAbsoluteAdapterPosition()).getFullName()+"?")
                           .setIcon(android.R.drawable.ic_dialog_alert)
                           .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                               public void onClick(DialogInterface dialog, int whichButton) {
                                   mData.remove(getAdapterPosition());
                                   notifyItemRemoved(getAdapterPosition());
                                   notifyItemRangeChanged(getAdapterPosition(),mData.size());

                                   saveData();
                               }})
                           .setNegativeButton(android.R.string.no, null).show();



                   viewClickListener.onClickDelete(view,getAbsoluteAdapterPosition(), mData);
               }
           });

        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            //if (viewClickListener != null) viewClickListener.onClickView(view, getAdapterPosition());

        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Tenant> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Tenant item : FullList) {
                    if (item.getFullName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData.clear();
            mData.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };


    private void saveData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(mData);
            editor.putString("newTenant", json);
            editor.apply();
        }
    }
}

