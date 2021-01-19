package com.kotlarz_marlene_dogservicescheduler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.kotlarz_marlene_dogservicescheduler.Adapter.CustomerAdapter;
import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.CustomerViewModel;

import java.util.List;

public class AppointmentCustomerListActivity extends AppCompatActivity {

    // Intent Extra Keys
    public static final String EXTRA_CUSTOMER_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ID";
    public static final String EXTRA_CUSTOMER_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_NAME";

    private CustomerViewModel customerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_customer_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Appointment Customer List");

        // Reference to RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_appointmentCustomerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Reference adapter
        final CustomerAdapter adapter = new CustomerAdapter();
        recyclerView.setAdapter(adapter);

        // Assign ViewModel
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        customerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) { // Triggered every time data in liveData object changes.
                // Update UI/ RecyclerView

                if(customers.isEmpty()) {
                    Toast.makeText(AppointmentCustomerListActivity.this, "No customers found", Toast.LENGTH_SHORT).show();
                }else {

                    adapter.setCustomers(customers); // Retrieve list of customers
                }
            }
        });

        // onClick - return details to AppointmentAddActivity
        adapter.setOnItemClickListener(new CustomerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Customer customer) {
                Intent intent = new Intent(AppointmentCustomerListActivity.this, AppointmentAddActivity.class);
                intent.putExtra(AppointmentCustomerListActivity.EXTRA_CUSTOMER_ID, customer.getCustomer_id());
                intent.putExtra(AppointmentCustomerListActivity.EXTRA_CUSTOMER_NAME, customer.getCustomer_name());

                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }

    // Create actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appointment_customer_list_menu, menu);
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