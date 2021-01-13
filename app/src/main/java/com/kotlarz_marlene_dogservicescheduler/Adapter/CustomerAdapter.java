package com.kotlarz_marlene_dogservicescheduler.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

    public void setOriginalCustomers(List<Customer> originalCustomers) {
        this.originalCustomers = originalCustomers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    // Create and return CustomerHolder, this is the layout you want to use for the single items in RecyclerView.
    @NonNull
    @Override
    public CustomerAdapter.CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_customer, parent, false);
        return new CustomerHolder(itemView);
    }

    // Takes care of getting the data from the single CustomerList java objects into the
    // views of the CustomerHolder. Get item and pass it into the textView
    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerHolder holder, int position) {
        Customer currentCustomer = customers.get(position);
        holder.textView_name.setText(currentCustomer.getCustomer_name());
        holder.textView_address.setText(currentCustomer.getCustomer_address());
        holder.textView_phone.setText(currentCustomer.getCustomer_phone());
    }

    // Returns how many items you want to display in the RecyclerView
    @Override
    public int getItemCount() { return customers.size(); }

    // CustomerListActivity observes liveData, onChanged methods passes List of customers.
    // We need to get this list into the RecyclerView by calling this method
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        // Tell adapter to redraw the layout.
        notifyDataSetChanged();
    }


    // Return Customer at this position - created for swipe delete method in CustomerListActivity
    public Customer getCustomerAt(int position) {
        return customers.get(position);
    }


    // Create ViewHolder class - Will hold the views in our single RecyclerView items
    public class CustomerHolder extends RecyclerView.ViewHolder {
        private TextView textView_name;
        private TextView textView_address;
        private TextView textView_phone;

        // Constructor
        // Assign the textViews - itemView is the card itself from the cardView list item.
        public CustomerHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_customer_name);
            textView_address = itemView.findViewById(R.id.textView_customer_address);
            textView_phone = itemView.findViewById(R.id.textView_customer_phone);

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
    // Choose onItemClickListener with my package name
    public void setOnItemClickListener(OnItemClickListener listener) {this.listener = listener;}

}
