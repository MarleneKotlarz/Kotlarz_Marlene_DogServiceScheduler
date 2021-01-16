package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kotlarz_marlene_dogservicescheduler.Entity.Employee;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.EmployeeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Scheduler";

    int employeeId;
    Button button_login;
    EditText editText_password;
    EmployeeViewModel employeeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        editText_password = findViewById(R.id.editText_password);
        button_login = findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = editText_password.getText().toString().trim();

                if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                } else {
                    employeeViewModel.getAllEmployees().observe(MainActivity.this, new Observer<List<Employee>>() {
                        @Override
                        public void onChanged(List<Employee> employees) {

                            try {
                                for (Employee employee : employees)
                                    if (employee.getEmployee_password().equals(password)) {
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        intent.putExtra(HomeActivity.EXTRA_EMPLOYEE_ID, employee.getEmployee_id());
                                        intent.putExtra(HomeActivity.EXTRA_EMPLOYEE_NAME, employee.getEmployee_name());
                                        intent.putExtra(HomeActivity.EXTRA_EMPLOYEE_PHONE, employee.getEmployee_phone());
                                        intent.putExtra(HomeActivity.EXTRA_EMPLOYEE_PASSWORD, password);
                                        startActivity(intent);

                                        Log.v(TAG, "Scheduler - MainActivity - onClick - password enter " + password);

                                    } else {
                                        Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                    }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        });
    }


}


