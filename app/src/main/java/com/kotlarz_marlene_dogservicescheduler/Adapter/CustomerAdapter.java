package com.kotlarz_marlene_dogservicescheduler.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;
import com.kotlarz_marlene_dogservicescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> {

    private List<Customer> customers = new ArrayList<>();
    private List<Customer> originalCustomers = new ArrayList<>();
    private OnItemClickListener listener;

    public List<Customer> getOriginalCustomers() {
        return originalCustomers;
    }
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setOriginalCustomers(List<Customer> originalCustomers) {
        this.originalCustomers = originalCustomers;
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_customer, parent, false);
        return new CustomerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerHolder holder, int position) {
        Customer currentCustomer = customers.get(position);
        holder.textView_name.setText(currentCustomer.getCustomer_name());
        holder.textView_address.setText(currentCustomer.getCustomer_address());
        holder.textView_phone.setText(currentCustomer.getCustomer_phone());
        holder.textView_id.setText(String.valueOf(currentCustomer.getCustomer_id()));
    }

    @Override
    public int getItemCount() { return customers.size(); }


    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }

    public Customer getCustomerAt(int position) {
        return customers.get(position);
    }


    // Create ViewHolder class
    public class CustomerHolder extends RecyclerView.ViewHolder {
        private TextView textView_name;
        private TextView textView_address;
        private TextView textView_phone;
        private TextView textView_id;

        // Constructor
        public CustomerHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_customer_name);
            textView_address = itemView.findViewById(R.id.textView_customer_address);
            textView_phone = itemView.findViewById(R.id.textView_customer_phone);
            textView_id = itemView.findViewById(R.id.textView_customer_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(customers.get(position));
                    }
                }
            });
        }
    }

    // onClick event interface
    public interface OnItemClickListener {
        void onItemClick(Customer customer);
    }

    // Reference onItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {this.listener = listener;}

}
