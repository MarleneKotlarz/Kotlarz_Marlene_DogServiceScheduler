package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kotlarz_marlene_dogservicescheduler.Adapter.CustomerAdapter;
import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.CustomerViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {

    public static final int ADD_CUSTOMER_REQUEST = 1;

    public static final String EXTRA_CUSTOMER_EMPLOYEE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_EMPLOYEE_ID";

    private static final String TAG = "Scheduler";

    private CustomerViewModel customerViewModel;
    private CustomerAdapter customerAdapter;
    private int employeeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Customer List");

        Intent intent = getIntent();
        employeeId = intent.getIntExtra(EXTRA_CUSTOMER_EMPLOYEE_ID, -1);

//---------- DESIGN ELEMENT THAT MAKE APPLICATION SCALABLE ----------//

        // Reference to RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_customerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Reference adapter
        customerAdapter = new CustomerAdapter();
        recyclerView.setAdapter(customerAdapter);

        // Assign ViewModel
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        customerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                if (customers.isEmpty()) {
                    Toast.makeText(CustomerListActivity.this, "No customers found", Toast.LENGTH_SHORT).show();
                }
                customerAdapter.setCustomers(customers);
                customerAdapter.setOriginalCustomers(customers);
            }
        });

//---------- DATABASE COMPONENT WITH FUNCTIONALITY TO ADD, MODIFY, DELETE DATA ----------//

        // RecyclerView - delete by swiping
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                customerViewModel.delete(customerAdapter.getCustomerAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CustomerListActivity.this, "Customer deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

//---------- DATABASE COMPONENT WITH FUNCTIONALITY TO ADD, MODIFY, DELETE DATA ----------//

        // onClick - view customer details in CustomerDetailsActivity
        customerAdapter.setOnItemClickListener(new CustomerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Customer customer) {
                Intent intent = new Intent(CustomerListActivity.this, CustomerDetailsActivity.class);
                intent.putExtra(CustomerDetailsActivity.EXTRA_CUSTOMER_ID, customer.getCustomer_id());
                intent.putExtra(CustomerDetailsActivity.EXTRA_CUSTOMER_NAME, customer.getCustomer_name());
                intent.putExtra(CustomerDetailsActivity.EXTRA_CUSTOMER_ADDRESS, customer.getCustomer_address());
                intent.putExtra(CustomerDetailsActivity.EXTRA_CUSTOMER_PHONE, customer.getCustomer_phone());
                intent.putExtra(CustomerDetailsActivity.EXTRA_CUSTOMER_EMPLOYEE_ID, customer.getEmployee_id_fk());

                startActivity(intent);
            }
        });

//---------- DATABASE COMPONENT WITH FUNCTIONALITY TO ADD, MODIFY, DELETE DATA ----------//

        // FAB to add customer in CustomerAddEditActivity
        FloatingActionButton fabAddCustomer = findViewById(R.id.fab_customer_add);
        fabAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerListActivity.this, CustomerAddEditActivity.class);
                startActivityForResult(intent, ADD_CUSTOMER_REQUEST);
            }
        });

        Log.v(TAG, "Scheduler - CustomerListActivity - onCreate employeeId " + employeeId);

    }

//---------- DATABASE COMPONENT WITH FUNCTIONALITY TO ADD, MODIFY, DELETE DATA ----------//

    // Get results back from CustomerAddEditActivity saveCustomer method.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CUSTOMER_REQUEST && resultCode == RESULT_OK) {
            String customerName = data.getStringExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_NAME);
            String customerAddress = data.getStringExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_ADDRESS);
            String customerPhone = data.getStringExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_PHONE);
            // Create new Customer
            Customer customer = new Customer(employeeId, customerName, customerAddress, customerPhone);
            customerViewModel.insert(customer);

            Log.v(TAG, "Scheduler - onActivityResult - employeeId " + employeeId );

            Toast.makeText(this, "Customer saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Customer not saved", Toast.LENGTH_SHORT).show();
        }
    }

//---------- SEARCH FUNCTIONALITY ----------//

    // Create actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.customer_list_menu, menu);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String query) {
                    Log.i("Search bar change", query);
                    ArrayList<Customer> currentList = new ArrayList<>();
                    if(query.isEmpty()){
                        customerAdapter.setCustomers(customerAdapter.getOriginalCustomers());
                    }
                    else {
                        for (Customer customer : customerAdapter.getCustomers()) {
                            if (customer.getCustomer_name().toLowerCase().contains(query.toLowerCase())) {
                                currentList.add(customer);
                            }
                        }
                        customerAdapter.setCustomers(currentList);
                    }
                    customerAdapter.notifyDataSetChanged();
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("Search text submitted", query);
                    return false;
                }
            });

        return true;
    }

    // Handle backwards arrow in actionbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}