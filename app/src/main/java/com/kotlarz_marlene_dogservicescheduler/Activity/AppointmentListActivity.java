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
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kotlarz_marlene_dogservicescheduler.Adapter.AppointmentAdapter;
import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.Entity.ServiceOption;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.AppointmentViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.ServiceOptionViewModel;

import java.util.List;

public class AppointmentListActivity extends AppCompatActivity {

    public static final int ADD_APPOINTMENT_REQUEST = 1;
    public static final int EDIT_APPOINTMENT_REQUEST = 2;

    public static final String EXTRA_EMPLOYEE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_EMPLOYEE_ID";
    public static final String EXTRA_APPOINTMENT_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_ID";

    private static final String TAG = "ServiceScheduler";

    private AppointmentViewModel appointmentViewModel;
    private ServiceOptionViewModel serviceOptionViewModel;
    private Appointment appointment;
    private String customerName, date, time,petName;
    private String location, duration, serviceType, option;
    private int customerId, petId, appointmentId;
    private int employeeId=1;
    int newAppointmentId = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("AppointmentList");


        // Reference RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_appointmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); // If size(height and width) doesn't change.

        // Reference Adapter
        final AppointmentAdapter adapter = new AppointmentAdapter();
        recyclerView.setAdapter(adapter);

        // Assign ViewModel
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        serviceOptionViewModel = new ViewModelProvider(this).get(ServiceOptionViewModel.class);
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
//                startActivity(intent);
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
            date = data.getStringExtra(AppointmentAddActivity.EXTRA_APPOINTMENT_DATE);
            time = data.getStringExtra(AppointmentAddActivity.EXTRA_APPOINTMENT_TIME);
            customerName = data.getStringExtra(AppointmentAddActivity.EXTRA_CUSTOMER_NAME);
            customerId = data.getIntExtra(AppointmentAddActivity.EXTRA_CUSTOMER_ID, -1);
            petId = data.getIntExtra(AppointmentAddActivity.EXTRA_PET_ID, -1);
            duration = data.getStringExtra(AppointmentAddActivity.EXTRA_SERVICE_DURATION);
            location = data.getStringExtra(AppointmentAddActivity.EXTRA_SERVICE_LOCATION);
            serviceType = data.getStringExtra(AppointmentAddActivity.EXTRA_SERVICE_TYPE);
            option = data.getStringExtra(AppointmentAddActivity.EXTRA_SERVICE_OPTION);


            // Create new Appointment
            Appointment appointment = new Appointment(employeeId, customerId, petId, date, time);
            appointmentViewModel.insert(appointment);


            Log.v(TAG, "Scheduler - AppointmentListActivity //////////////////////////////////////////// ");


            Thread thread = new Thread();
            try {
                Log.v(TAG, "Scheduler - AppointmentListActiviy ===================SLEEP START========================================= ");
                thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            Log.v(TAG, "Scheduler - AppointmentListActiviy ===================SLEEP END========================================= ");


            newAppointmentId = appointmentViewModel.getAppointmentIdForService();


            if (serviceType.equals("Walking")) {

                newAppointmentId++;
                Log.v(TAG, "Scheduler - AppointmentListActiviy ===================newAppointmentId================ " +newAppointmentId);
                ServiceOption serviceOption1 = new ServiceOption(duration, location, serviceType, newAppointmentId, option);
                serviceOptionViewModel.insert(serviceOption1);
            }

            if (serviceType.equals("Playing")) {

                newAppointmentId++;
                Log.v(TAG, "Scheduler - AppointmentListActiviy ===================newAppointmentId================ " +newAppointmentId);
                ServiceOption serviceOption = new ServiceOption(duration, serviceType, newAppointmentId, option);
                serviceOptionViewModel.insert(serviceOption);
            }






            Log.v(TAG, "Scheduler - AppointmentListActivity - onActivityResult " +
                    "  employeeId " + employeeId +
                    "customerId " + customerId + " petId " + petId);

            Toast.makeText(this, "Appointment saved", Toast.LENGTH_SHORT).show();

            Log.v(TAG, "Scheduler - AppointmentListActiviy ============================================================ ");
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