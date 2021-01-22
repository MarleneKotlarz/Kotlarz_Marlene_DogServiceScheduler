package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.kotlarz_marlene_dogservicescheduler.Entity.Employee;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.EmployeeViewModel;

public class HomeActivity extends AppCompatActivity {

    public static final int EDIT_EMPLOYEE_REQUEST = 1;

    public static final String EXTRA_EMPLOYEE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_EMPLOYEE_ID";
    public static final String EXTRA_EMPLOYEE_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_EMPLOYEE_NAME";
    public static final String EXTRA_EMPLOYEE_PHONE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_EMPLOYEE_PHONE";
    public static final String EXTRA_EMPLOYEE_PASSWORD =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_EMPLOYEE_PASSWORD";

    private static final String TAG = "Scheduler";

    String password, employeeName, employeePhone;
    int employeeId;
    Button button_employeeInfo, button_report;
    TextView textView_employeeName;
    EmployeeViewModel employeeViewModel;
    Employee employee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        textView_employeeName = findViewById(R.id.textView_home_employeeName);
        button_employeeInfo = findViewById(R.id.button_employeeInfo);
        button_report = findViewById(R.id.button_report);

        // Receiving data from MainActivity
        Intent intent = getIntent();
        textView_employeeName.setText(intent.getStringExtra(EXTRA_EMPLOYEE_NAME));
        employeeId = intent.getIntExtra(EXTRA_EMPLOYEE_ID, -1);
        employeeName = intent.getStringExtra(EXTRA_EMPLOYEE_NAME);
        employeePhone = intent.getStringExtra(EXTRA_EMPLOYEE_PHONE);
        password = intent.getStringExtra(EXTRA_EMPLOYEE_PASSWORD);

        // onClick - view employee details in EmployeeDetailsActivity
        button_employeeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeName = textView_employeeName.getText().toString();
                Intent dataIntent = new Intent(HomeActivity.this, EmployeeDetailsActivity.class);
                dataIntent.putExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEE_ID, employeeId);
                dataIntent.putExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEE_NAME, employeeName);
                dataIntent.putExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEE_PHONE, employeePhone);
                dataIntent.putExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEE_PASSWORD, password);
                startActivityForResult(dataIntent, EDIT_EMPLOYEE_REQUEST);
            }
        });

        // onClick - view current appointment details in ReportActivity
            button_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(HomeActivity.this, ReportActivity.class);
                    startActivity(intent1);
                }
            });

        Log.v(TAG, "Scheduler - HomeActivity - onCreate - password " + password + " employeeName " + employeeName);

    }

    // onClick - view appointment list in AppointmentListActivity
    public void onClick_appointmentList(View view) {
            Intent intent = new Intent(HomeActivity.this, AppointmentListActivity.class);
            intent.putExtra(AppointmentListActivity.EXTRA_EMPLOYEE_ID, employeeId);
            startActivity(intent);
    }

    // onClick - view customer list in CustomerListActivity
    public void onClick_customerList(View view) {
        Intent intent = new Intent(HomeActivity.this, CustomerListActivity.class);
        intent.putExtra(CustomerListActivity.EXTRA_CUSTOMER_EMPLOYEE_ID, employeeId);
        startActivity(intent);
    }

    // Get results back from EmployeeDetailsActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_EMPLOYEE_REQUEST && resultCode == RESULT_OK) {
            employeeName = data.getStringExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEE_NAME);
            employeePhone = data.getStringExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEE_PHONE);
            password = data.getStringExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEE_PASSWORD);
            employeeId = data.getIntExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEE_ID, -1);

            // Update employee
            employee = new Employee(employeeName, employeePhone, password);
            employee.setEmployee_id(employeeId);
            textView_employeeName.setText(employeeName);
            employeeViewModel.update(employee);

            Toast.makeText(this, "Employee updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Employee not updated", Toast.LENGTH_SHORT).show();
        }
    }

}