package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kotlarz_marlene_dogservicescheduler.Adapter.CustomerAdapter;
import com.kotlarz_marlene_dogservicescheduler.Adapter.PetAdapter;
import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.Entity.AppointmentAndServiceOption;
import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;
import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.AppointmentViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.CustomerViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.PetViewModel;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDetailsActivity extends AppCompatActivity {

    public static final int EDIT_APPOINTMENT_REQUEST = 2;

    // Intent Extra Keys
    public static final String EXTRA_APPOINTMENT_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_ID";
    public static final String EXTRA_APPOINTMENT_DATE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_DATE";
    public static final String EXTRA_APPOINTMENT_TIME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_TIME";
    public static final String EXTRA_CUSTOMER_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ID";
    public static final String EXTRA_CUSTOMER_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_NAME";
    public static final String EXTRA_PET_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_PET_ID";
    public static final String EXTRA_EMPLOYEE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_EMPLOYEE_ID";
    public static final String EXTRA_CUSTOMER_SERVICE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_SERVICE_ID";


    private static final String TAG = "Scheduler";

    private AppointmentViewModel appointmentViewModel;
    private CustomerViewModel customerViewModel;
    private PetViewModel petViewModel;


    TextView textView_date, textView_time, textView_customerName, textView_customerAddress, textView_customerPhone;
    TextView textView_petName, textView_petBreed, textView_petAge, textView_petNote;
    TextView textView_serviceDuration, textView_serviceLocation, textView_serviceType, textView_serviceOption;

    private int appointmentId, customerId, petId, employeeId;
    private String date, time, customerName, customerAddress, customerPhone,  petName, petBreed, petAge, petNote;
    private String serviceDuration, serviceLocation, serviceType, serviceOption;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Appointment Details");

        // Get the ViewModels
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        petViewModel = new ViewModelProvider(this).get(PetViewModel.class);

        // Assign fields
        textView_date = findViewById(R.id.textView_appointmentDetails_appointment_date);
        textView_time = findViewById(R.id.textView_appointmentDetails_appointment_time);
        textView_customerName = findViewById(R.id.textView_appointmentDetails_customerName);
        textView_customerAddress = findViewById(R.id.textView_appointmentDetails_customerAddress);
        textView_customerPhone = findViewById(R.id.textView_appointmentDetails_customerPhone);
        textView_petName = findViewById(R.id.textView_appointmentDetails_petName);
        textView_petBreed = findViewById(R.id.textView_appointmentDetails_petBreed);
        textView_petAge = findViewById(R.id.textView_appointmentDetails_petAge);
        textView_petNote = findViewById(R.id.textView_appointmentDetails_petNote);
        textView_serviceDuration= findViewById(R.id.textView_appointmentDetails_serviceDuration);
        textView_serviceLocation = findViewById(R.id.textView_appointmentDetails_serviceLocation);
        textView_serviceType = findViewById(R.id.textView_appointmentDetails_serviceType);
        textView_serviceOption = findViewById(R.id.textView_appointmentDetails_serviceOption);

        // Populate fields
        Intent intent = getIntent();
        textView_date.setText(intent.getStringExtra(EXTRA_APPOINTMENT_DATE));
        textView_time.setText(intent.getStringExtra(EXTRA_APPOINTMENT_TIME));

        // Set field values
        appointmentId = intent.getIntExtra(EXTRA_APPOINTMENT_ID, -1);
        date = intent.getStringExtra(EXTRA_APPOINTMENT_DATE);
        time = intent.getStringExtra(EXTRA_APPOINTMENT_TIME);
        customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1);
        petId = intent.getIntExtra(EXTRA_PET_ID, -1);
        employeeId = intent.getIntExtra(EXTRA_EMPLOYEE_ID, -1);




        customerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {

                for (Customer customer : customers)
                    if (customer.getCustomer_id() == getIntent().getIntExtra(EXTRA_CUSTOMER_ID, -1)) {
                        customerName = customer.getCustomer_name();
                        textView_customerName.setText(customerName);

                        customerAddress = customer.getCustomer_address();
                        textView_customerAddress.setText(customerAddress);

                        customerPhone = customer.getCustomer_phone();
                        textView_customerPhone.setText(customerPhone);
                    } else {
                        System.out.println("CustomerName not found");
                    }

            }
        });


        petViewModel.getPetByCustomerId(customerId).observe(this, new Observer<List<Pet>>() {
            @Override
            public void onChanged(List<Pet> pets) {

                for (Pet pet : pets)
                    if (pet.getPet_id() == getIntent().getIntExtra(EXTRA_PET_ID, -1)) {
                        petName = pet.getPet_name();
                        textView_petName.setText(petName);

                        petBreed = pet.getPet_breed();
                        textView_petBreed.setText(petBreed);

                        petAge = pet.getPet_age();
                        textView_petAge.setText(petAge);

                        petNote = pet.getPet_note();
                        textView_petNote.setText(petNote);
                    }


            }
        });


        appointmentViewModel.getAppointmentAndServiceByApptId(appointmentId).observe(this, new Observer<List<AppointmentAndServiceOption>>() {
            @Override
            public void onChanged(List<AppointmentAndServiceOption> appointmentAndServiceOptions) {

                for (AppointmentAndServiceOption appointmentAndServiceOption : appointmentAndServiceOptions) {

                    if(appointmentAndServiceOption.appointment.getAppointment_id() == getIntent().getIntExtra(EXTRA_APPOINTMENT_ID, -1)) {
                        serviceDuration = appointmentAndServiceOption.serviceOption.getDuration();
                        textView_serviceDuration.setText(serviceDuration);

                        serviceLocation = appointmentAndServiceOption.serviceOption.getLocation();
                        textView_serviceLocation.setText(serviceLocation);

                        serviceType = appointmentAndServiceOption.serviceOption.getType();
                        textView_serviceType.setText(serviceType);

                        serviceOption = appointmentAndServiceOption.serviceOption.getOption();
                        textView_serviceOption.setText(serviceOption);

                    } else {
                        System.out.println("Service type not found");
                    }
                }
            }
        });

        Log.v(TAG, "Scheduler - AppointmentDetailsActivity - onCreate appointmentId " + appointmentId);


// TODO remove appointmentEditActivity

        // FAB to edit Appointment in AppointmentEditActivity
//        FloatingActionButton fabEditAppointment = findViewById(R.id.fab_appointment_edit);
//        fabEditAppointment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                date = textView_date.getText().toString();
//                time = textView_time.getText().toString();
//
//                Intent intent = new Intent(AppointmentDetailsActivity.this, AppointmentEditActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
//                intent.putExtra(AppointmentAddActivity.EXTRA_APPOINTMENT_DATE, date);
//                intent.putExtra(AppointmentAddActivity.EXTRA_APPOINTMENT_TIME, time);
//                appointmentId = getIntent().getIntExtra(EXTRA_APPOINTMENT_ID, -1);
//                if (appointmentId != -1) {
//                    intent.putExtra(EXTRA_APPOINTMENT_ID, appointmentId);
//                }
//                startActivityForResult(intent, EDIT_APPOINTMENT_REQUEST);
////                startActivity(intent);
//            }
//        });


    }

    // Get results back from editAppointment method.
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == EDIT_APPOINTMENT_REQUEST && resultCode == RESULT_OK) {
//            // Get Intent Keys from other AppointmentAddActivity.
//            textView_date = findViewById(R.id.textView_appointmentDetails_appointment_date);
//            textView_time = findViewById(R.id.textView_appointmentDetails_appointment_time);
//
//            textView_date.setText(data.getStringExtra(AppointmentEditActivity.EXTRA_APPOINTMENT_DATE));
//            textView_time.setText(data.getStringExtra(AppointmentEditActivity.EXTRA_APPOINTMENT_TIME));
//
//            String date = data.getStringExtra(AppointmentEditActivity.EXTRA_APPOINTMENT_DATE);
//            String time = data.getStringExtra(AppointmentEditActivity.EXTRA_APPOINTMENT_TIME);
////            int employeeId = 1;
//
//            int appointmentId = data.getIntExtra(AppointmentEditActivity.EXTRA_APPOINTMENT_ID, -1);
//            if (appointmentId == -1) {
//                Toast.makeText(this, "Appointment can't be updated", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Update Appointment
//            Appointment appointment = new Appointment(employeeId, customerId, petId, date, time);
//            appointment.setAppointment_id(appointmentId);
//            appointmentViewModel.update(appointment);
//
//            Toast.makeText(this, "Appointment updated", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(this, "Appointment not saved", Toast.LENGTH_SHORT).show();
//        }
//
//        Log.v(TAG, "Scheduler - AppointmentDetailsActivity - onActivityResult");
//
//
//    }


        // Create actionbar menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.appointment_details_menu, menu);
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