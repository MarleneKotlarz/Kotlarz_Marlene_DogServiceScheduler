package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.CustomerViewModel;

public class CustomerDetailsActivity extends AppCompatActivity {

    public static final int EDIT_CUSTOMER_REQUEST = 2;

    // Intent Extra Keys
    public static final String EXTRA_CUSTOMER_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ID";
    public static final String EXTRA_CUSTOMER_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_NAME";
    public static final String EXTRA_CUSTOMER_ADDRESS =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ADRESS";
    public static final String EXTRA_CUSTOMER_PHONE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_PHONE";
    public static final String EXTRA_CUSTOMER_EMPLOYEE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_EMPLOYEE_ID";

    private CustomerViewModel customerViewModel;
    private int customerId, employeeId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private ImageButton imageButtonPet;
    TextView textView_customerName, textView_customerAddress, textView_customerPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Customer Details");

        // Get the CustomerViewModel
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);

        // Assign fields
        textView_customerName = findViewById(R.id.textView_customer_name);
        textView_customerAddress = findViewById(R.id.textView_customer_address);
        textView_customerPhone = findViewById(R.id.textView_customer_phone);
        imageButtonPet = findViewById(R.id.imageButton_pet);

        // Populate fields by getting data from CustomerListActivity
        Intent intent = getIntent();
        textView_customerName.setText(intent.getStringExtra(EXTRA_CUSTOMER_NAME));
        textView_customerAddress.setText(intent.getStringExtra(EXTRA_CUSTOMER_ADDRESS));
        textView_customerPhone.setText(intent.getStringExtra(EXTRA_CUSTOMER_PHONE));

        // Set field values
        customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1);
        customerName = intent.getStringExtra(EXTRA_CUSTOMER_NAME);
        customerAddress = intent.getStringExtra(EXTRA_CUSTOMER_ADDRESS);
        customerPhone = intent.getStringExtra(EXTRA_CUSTOMER_PHONE);
        employeeId = intent.getIntExtra(EXTRA_CUSTOMER_EMPLOYEE_ID, -1);

        // FAB edit customer button - open CustomerAddEditActivity
        FloatingActionButton fabEditCustomer = findViewById(R.id.fab_customer_edit);
        fabEditCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerName = textView_customerName.getText().toString();
                customerAddress = textView_customerAddress.getText().toString();
                customerPhone = textView_customerPhone.getText().toString();

                Intent intent = new Intent(CustomerDetailsActivity.this, CustomerAddEditActivity.class);
                intent.putExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_NAME, customerName);
                intent.putExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_ADDRESS, customerAddress);
                intent.putExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_PHONE, customerPhone);
                customerId = getIntent().getIntExtra(EXTRA_CUSTOMER_ID, -1);
                if (customerId != -1) {
                    intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                }
                startActivityForResult(intent, EDIT_CUSTOMER_REQUEST);
            }
        });

        // ImageButton onClick - PetListActivity
        imageButtonPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDetailsActivity.this, PetListActivity.class);
                intent.putExtra(PetListActivity.EXTRA_CUSTOMER_ID, customerId);
                startActivity(intent);
            }
        });

    }

    // Get results back from editing a customer
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_CUSTOMER_REQUEST && resultCode == RESULT_OK) {
            textView_customerName = findViewById(R.id.textView_customer_name);
            textView_customerAddress = findViewById(R.id.textView_customer_address);
            textView_customerPhone = findViewById(R.id.textView_customer_phone);

            customerName = data.getStringExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_NAME);
            customerAddress = data.getStringExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_ADDRESS);
            customerPhone = data.getStringExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_PHONE);

            textView_customerName.setText(customerName);
            textView_customerAddress.setText(customerAddress);
            textView_customerPhone.setText(customerPhone);

            customerId = data.getIntExtra(CustomerAddEditActivity.EXTRA_CUSTOMER_ID, -1);
            if (customerId == -1) {
                Toast.makeText(this, "Customer can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            Customer customer = new Customer(employeeId, customerName, customerAddress, customerPhone);
            customer.setCustomer_id(customerId);
            customerViewModel.update(customer);

            Toast.makeText(this, "Customer updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Customer not updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Create actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.customer_details_menu, menu);
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