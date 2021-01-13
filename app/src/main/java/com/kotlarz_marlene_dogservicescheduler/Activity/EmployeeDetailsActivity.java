package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.EmployeeViewModel;

public class EmployeeDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_EMPLOYEE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_EMPLOYEE_ID";
    public static final String EXTRA_EMPLOYEE_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_EMPLOYEE_NAME";
    public static final String EXTRA_EMPLOYEE_PHONE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_EMPLOYEE_PHONE";
    public static final String EXTRA_EMPLOYEE_PASSWORD =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_EMPLOYEE_PASSWORD";

    private static final String TAG = "Scheduler";

    EditText editText_employeeName, editText_employeePhone, editText_employeePassword;
    String employeeName, employeePhone, employeePassword;
    int employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Employee Details");

        // Assign fields
        editText_employeeName = findViewById(R.id.editText_employeeName);
        editText_employeePhone = findViewById(R.id.editText_employeePhone);
        editText_employeePassword = findViewById(R.id.editText_employeePassword);

        // Populate fields
        Intent intent = getIntent();
        editText_employeeName.setText(intent.getStringExtra(EXTRA_EMPLOYEE_NAME));
        editText_employeePhone.setText(intent.getStringExtra(EXTRA_EMPLOYEE_PHONE));
        editText_employeePassword.setText(intent.getStringExtra(EXTRA_EMPLOYEE_PASSWORD));
        // Set values
        employeeId = intent.getIntExtra(EXTRA_EMPLOYEE_ID, -1);
        employeeName = intent.getStringExtra(EXTRA_EMPLOYEE_NAME);
        employeePhone = intent.getStringExtra(EXTRA_EMPLOYEE_PHONE);
        employeePassword = intent.getStringExtra(EXTRA_EMPLOYEE_PASSWORD);

        Log.v(TAG,"Scheduler - EmployeeDetailsActivity - onCreate ");

    }

    private void saveEmployee() {
        employeeName = editText_employeeName.getText().toString();
        employeePhone = editText_employeePhone.getText().toString();
        employeePassword = editText_employeePassword.getText().toString();

        // Input validation for empty fields
        if (employeeName.trim().isEmpty() || employeePhone.trim().isEmpty() || employeePassword.trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Accept input and save employee - send data back
        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_EMPLOYEE_NAME, employeeName);
        dataIntent.putExtra(EXTRA_EMPLOYEE_PHONE, employeePhone);
        dataIntent.putExtra(EXTRA_EMPLOYEE_PASSWORD, employeePassword);
        dataIntent.putExtra(EXTRA_EMPLOYEE_ID, employeeId);

        setResult(RESULT_OK, dataIntent);
        finish();

    }

    // Add menu bar to actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.employee_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_employee:
                saveEmployee();
            default:
                return super.onOptionsItemSelected(item);
        }


    }


}