package com.kotlarz_marlene_dogservicescheduler.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kotlarz_marlene_dogservicescheduler.Adapter.AppointmentAdapter;
import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.AppointmentViewModel;

import java.util.List;

public class AppointmentListActivity extends AppCompatActivity {

    public static final int ADD_APPOINTMENT_REQUEST = 1;
    public static final int EDIT_APPOINTMENT_REQUEST = 2;

    public static final String EXTRA_EMPLOYEE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_EMPLOYEE_ID";

    private static final String TAG = "ServiceScheduler";

    private AppointmentViewModel appointmentViewModel;
    private Appointment appointment;
    private String customerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("AppointmentList");

        Intent intent = getIntent();
        intent.getIntExtra(EXTRA_EMPLOYEE_ID, -1);

        // Reference RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_appointmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); // If size(height and width) doesn't change.

        // Reference Adapter
        final AppointmentAdapter adapter = new AppointmentAdapter();
        recyclerView.setAdapter(adapter);

        // Assign ViewModel
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        // Create observer which updates the UI and
        // observe the LiveData method, passing in this activity as the LifecycleOwner and the observer
        // as an anonymous innerclass
        appointmentViewModel.getAllAppointments().observe(this, new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                // Update UI/ RecyclerView
                adapter.setAppointments(appointments);
            }
        });

        // RecyclerView - delete by swiping
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Get positionAt method from CourseAdapter
                appointmentViewModel.delete(adapter.getAppointmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AppointmentListActivity.this, "Appointment deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        // onClick - view appointment details in AppointmentDetailsActivity
        adapter.setOnItemClickListener(new AppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Appointment appointment) {
                Intent intent = new Intent(AppointmentListActivity.this, AppointmentDetailsActivity.class);
                intent.putExtra(AppointmentDetailsActivity.EXTRA_APPOINTMENT_ID, appointment.getAppointment_id());
                intent.putExtra(AppointmentDetailsActivity.EXTRA_APPOINTMENT_DATE, appointment.getDate());
                intent.putExtra(AppointmentDetailsActivity.EXTRA_APPOINTMENT_TIME, appointment.getTime());
                intent.putExtra(AppointmentDetailsActivity.EXTRA_CUSTOMER_ID, appointment.getCustomer_id_fk());
                intent.putExtra(AppointmentDetailsActivity.EXTRA_PET_ID, appointment.getPet_id_fk());
                intent.putExtra(AppointmentDetailsActivity.EXTRA_EMPLOYEE_ID, appointment.getEmployee_id_fk());
//                intent.putExtra(AppointmentDetailsActivity.EXTRA_CUSTOMER_SERVICE_ID, appointment.getService_id_fk());

                startActivity(intent);
//                startActivityForResult(intent, EDIT_APPOINTMENT_REQUEST);

                Log.v(TAG, "Scheduler - AppointmentListActivity - onItemClick AppointmentDetails CustomerId " + appointment.getCustomer_id_fk());
            }



        });




        // FAB to add appointment in AppointmentAddActivity
        FloatingActionButton fabAddCustomer = findViewById(R.id.fab_appointment_add);
        fabAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentListActivity.this, AppointmentAddActivity.class);
                startActivityForResult(intent, ADD_APPOINTMENT_REQUEST);
            }
        });


        Log.v(TAG, "Scheduler - AppointmentListActivity - onCreate ");


    }

    // Get results back from AppointmentAddActivity saveAppointment method.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_APPOINTMENT_REQUEST && resultCode == RESULT_OK) {
            // Get Intent Keys from other AppointmentAddActivity.
            String date = data.getStringExtra(AppointmentAddActivity.EXTRA_APPOINTMENT_DATE);
            String time = data.getStringExtra(AppointmentAddActivity.EXTRA_APPOINTMENT_TIME);
            String customerName = data.getStringExtra(AppointmentAddActivity.EXTRA_CUSTOMER_NAME);
            int customerId = data.getIntExtra(AppointmentAddActivity.EXTRA_CUSTOMER_ID, -1);
            int pedId = data.getIntExtra(AppointmentAddActivity.EXTRA_PET_ID, -1);
//            int serviceId = data.getIntExtra(AppointmentAddActivity.EXTRA_SERVICE_ID, -1);


            int employeeId = getIntent().getIntExtra(EXTRA_EMPLOYEE_ID, -1);
            if(employeeId != -1) {
                data.putExtra(EXTRA_EMPLOYEE_ID, employeeId);
            }

            // Create new Appointment
            Appointment appointment = new Appointment(employeeId, customerId, pedId, date, time);
            appointmentViewModel.insert(appointment);

            Log.v(TAG, "Scheduler - AppointmentListActivity - onActivityResult employeeId " + employeeId);

            Toast.makeText(this, "Appointment saved", Toast.LENGTH_SHORT).show();

        }

        else {
            Toast.makeText(this, "Appointment not saved", Toast.LENGTH_SHORT).show();
        }


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