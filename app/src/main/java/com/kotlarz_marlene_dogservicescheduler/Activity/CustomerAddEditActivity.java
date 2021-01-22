package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kotlarz_marlene_dogservicescheduler.R;

public class CustomerAddEditActivity extends AppCompatActivity {

    // Intent Extra Keys
    public static final String EXTRA_CUSTOMER_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ID";
    public static final String EXTRA_CUSTOMER_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_NAME";
    public static final String EXTRA_CUSTOMER_ADDRESS =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ADDRESS";
    public static final String EXTRA_CUSTOMER_PHONE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_PHONE";

    // Member variables
    private EditText editText_customerName;
    private EditText editText_customerAddress;
    private EditText editText_customerPhone;
    private String customerName;
    private String customerAddress;
    private String customerPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Assign fields
        editText_customerName = findViewById(R.id.editText_customerAddEdit_name);
        editText_customerAddress = findViewById(R.id.editText_customerAddEdit_address);
        editText_customerPhone = findViewById(R.id.editText_customerAddEdit_phone);

        // Receiving data form CustomerListActivity and CustomerDetailsActivity
        Intent dataIntent = getIntent();
        if (dataIntent.hasExtra(EXTRA_CUSTOMER_ID)) {
            setTitle("Edit Customer");
            editText_customerName.setText(dataIntent.getStringExtra(EXTRA_CUSTOMER_NAME));
            editText_customerAddress.setText(dataIntent.getStringExtra(EXTRA_CUSTOMER_ADDRESS));
            editText_customerPhone.setText(dataIntent.getStringExtra(EXTRA_CUSTOMER_PHONE));
        } else {
            setTitle("Add Customer");
        }

    }

    // Save customer iconButton method
    private void saveCustomer() {
        // Get input from editText views
        customerName = editText_customerName.getText().toString();
        customerAddress = editText_customerAddress.getText().toString();
        customerPhone = editText_customerPhone.getText().toString();

        try {
            // Input validation for empty fields
            if (customerName.trim().isEmpty() || customerAddress.trim().isEmpty() || customerPhone.trim().isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Accept input and save customer - send data back
        Intent dataIntent = new Intent() ;
        dataIntent.putExtra(EXTRA_CUSTOMER_NAME, customerName);
        dataIntent.putExtra(EXTRA_CUSTOMER_ADDRESS, customerAddress);
        dataIntent.putExtra(EXTRA_CUSTOMER_PHONE, customerPhone);
        int id = getIntent().getIntExtra(EXTRA_CUSTOMER_ID, -1);
        if(id != -1) {
            dataIntent.putExtra(EXTRA_CUSTOMER_ID, id);
        }

        setResult(RESULT_OK, dataIntent);
        finish();
    }

    // Add menu bar to actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.customer_add_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_customer:
                saveCustomer();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}