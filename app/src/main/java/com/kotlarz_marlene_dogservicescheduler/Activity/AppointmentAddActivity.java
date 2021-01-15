package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.Utilities.AppointmentAddReceiver;
import com.kotlarz_marlene_dogservicescheduler.Utilities.DatePickerFragment;
import com.kotlarz_marlene_dogservicescheduler.Utilities.TimePickerFragment;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.AppointmentViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.CustomerViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.PetViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.ServiceOptionViewModel;

import java.text.DateFormat;
import java.util.Calendar;

public class AppointmentAddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final int ADD_CUSTOMER_TO_APPOINTMENT_REQUEST = 1;
    public static final int ADD_PET_TO_APPOINTMENT_REQUEST = 2;
    public static final int ADD_SERVICE_TO_APPOINTMENT_REQUEST = 3;



    // Intent Extra Keys
    public static final String EXTRA_EMPLOYEE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_EMPLOYEE_ID";
    public static final String EXTRA_APPOINTMENT_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_ID";
    public static final String EXTRA_APPOINTMENT_DATE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_DATE";
    public static final String EXTRA_APPOINTMENT_TIME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_TIME";
    public static final String EXTRA_SERVICE_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_SERVICE_ID";
    public static final String EXTRA_CUSTOMER_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ID";
    public static final String EXTRA_CUSTOMER_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_NAME";
    public static final String EXTRA_PET_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_PET_ID";
    public static final String EXTRA_SERVICE_DURATION =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_SERVICE_DURATION";
    public static final String EXTRA_SERVICE_LOCATION =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_SERVICE_LOCATION";
    public static final String EXTRA_SERVICE_TYPE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_SERVICE_TYPE";
    public static final String EXTRA_SERVICE_OPTION =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_SERVICE_OPTION";




    private static final String TAG = "Scheduler";


    private Calendar calendar;
    private EditText editText_appointmentDate;
    private EditText editText_appointmentTime;
    private TextView textView_date, textView_time, textView_customerName, textView_petName, textView_serviceType;
    private String date, time, customerName, petName;
    private String location, duration, serviceType, option;
    private int customerId, petId, serviceId, appointmentId;
    private int notificationId = 1;
    private int employeeId = 1;
    private Button buttonService;
    Appointment appointment;

    private AppointmentViewModel appointmentViewModel;
    private CustomerViewModel customerViewModel;
    private PetViewModel petViewModel;
    private ServiceOptionViewModel serviceOptionViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Appointment");

        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        petViewModel = new ViewModelProvider(this).get(PetViewModel.class);
        serviceOptionViewModel = new ViewModelProvider(this).get(ServiceOptionViewModel.class);




        // Assign fields
        textView_date = findViewById(R.id.textView_appointmentAdd_date);
        textView_time = findViewById(R.id.textView_appointmentAdd_time);
        textView_serviceType = findViewById(R.id.textView_appointmentAdd_serviceType);
        textView_customerName = findViewById(R.id.textView_appointmentAdd_customerName);
        textView_petName = findViewById(R.id.textView_appointmentAdd_petName);
        buttonService = findViewById(R.id.button_addService);
        ImageButton imageButton_date = findViewById(R.id.imageButton_date);
        ImageButton imageButton_alarmStart = findViewById(R.id.imageButton_alarmStart);
        ImageButton imageButton_alarmCancel = findViewById(R.id.imageButton_alarmCancel);
        ImageButton imageButton_time = findViewById(R.id.imageButton_time);

        // Assign add Service button
        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentAddActivity.this, AppointmentServiceActivity.class);
                startActivityForResult(intent, ADD_SERVICE_TO_APPOINTMENT_REQUEST);
            }
        });

        // Assign DatePicker
        imageButton_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        // Assign TimePicker
        imageButton_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        // Assign Alarm imageButton
        imageButton_alarmStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm(calendar);
            }
        });

        // Assign Alarm imageButton
        imageButton_alarmCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm(calendar);
            }
        });


        Log.v(TAG, "Scheduler - AppointmentAddActivity - onCreate - employeeId " + employeeId);

    }


    // DatePicker - set up EditText to selected date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        textView_date.setText(currentDateString);

    }

    // TimePicker - set up EditText to selected time
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String currentTime = String.format("%02d:%02d", hourOfDay, minute); // set date as String to include leading zeros
        textView_time.setText(currentTime);
    }

    // Create alarm for due date
    @SuppressLint("NewApi")
    private void startAlarm(Calendar c) {
        customerName = textView_customerName.getText().toString();
        petName = textView_petName.getText().toString();
        date = textView_date.getText().toString();
        time = textView_time.getText().toString();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AppointmentAddReceiver.class);
        intent.putExtra(AppointmentAddReceiver.EXTRA_NOTIFICATION_ID, notificationId);
        intent.putExtra(AppointmentAddReceiver.EXTRA_NOTIFICATION_CUSTOMER, customerName);
        intent.putExtra(AppointmentAddReceiver.EXTRA_NOTIFICATION_PET, petName);
        intent.putExtra(AppointmentAddReceiver.EXTRA_NOTIFICATION_DATE, date);
        intent.putExtra(AppointmentAddReceiver.EXTRA_NOTIFICATION_TIME, time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();

    }

    // Cancel alarm for due date
    @SuppressLint("NewApi")
    private void cancelAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AppointmentAddReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);

        Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show();

    }


    // Get selection back from AppointmentCustomerListActivity, AppointmentPetListActivity and AppointmentServiceActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CUSTOMER_TO_APPOINTMENT_REQUEST && resultCode == RESULT_OK) {

            TextView textView_customerName = findViewById(R.id.textView_appointmentAdd_customerName);
            customerName = data.getStringExtra(AppointmentCustomerListActivity.EXTRA_CUSTOMER_NAME);
            textView_customerName.setText(customerName);
            customerId = data.getIntExtra(AppointmentCustomerListActivity.EXTRA_CUSTOMER_ID, -1);
            if (customerId == -1) {
                Toast.makeText(this, "Customer can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (requestCode == ADD_PET_TO_APPOINTMENT_REQUEST && resultCode == RESULT_OK) {

            TextView textView_petName = findViewById(R.id.textView_appointmentAdd_petName);
            petName = data.getStringExtra(AppointmentPetListActivity.EXTRA_PET_NAME);
            textView_petName.setText(petName);
            petId = data.getIntExtra(AppointmentPetListActivity.EXTRA_PET_ID, -1);
            if (petId == -1) {
                Toast.makeText(this, "Pet can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (requestCode == ADD_SERVICE_TO_APPOINTMENT_REQUEST && resultCode == RESULT_OK) {

            duration = data.getStringExtra(AppointmentServiceActivity.EXTRA_SERVICE_DURATION);
            location = data.getStringExtra(AppointmentServiceActivity.EXTRA_SERVICE_LOCATION);
            textView_serviceType = findViewById(R.id.textView_appointmentAdd_serviceType);
            serviceType = data.getStringExtra(AppointmentServiceActivity.EXTRA_SERVICE_TYPE);
            textView_serviceType.setText(serviceType);
            option = data.getStringExtra(AppointmentServiceActivity.EXTRA_SERVICE_OPTION);


//            if (serviceType.equals("Walking")) {
//
//                int newAppointmentId = appointmentViewModel.getAppointmentIdForService();
//                newAppointmentId++;
//
//
//                Log.v(TAG, "Scheduler - AppointmentAddActivity - onActivityResult getAppointmentIdForService " + appointmentViewModel.getAppointmentIdForService()
//                        + " newAppointmentId " + newAppointmentId);
//
//                ServiceOption serviceOption1 = new ServiceOption(duration, location, serviceType, option);
//                serviceOptionViewModel.insert(serviceOption1);
//                Toast.makeText(this, "Service saved", Toast.LENGTH_SHORT).show();
//
//            }
//
//            if (serviceType.equals("Playing")) {

//                int newAppointmentId = appointmentViewModel.getAppointmentIdForService();
//                newAppointmentId++;
//                ServiceOption serviceOption = new ServiceOption(duration, serviceType, newAppointmentId,  option);
//                serviceOptionViewModel.insert(serviceOption);
//                Toast.makeText(this, "Service saved", Toast.LENGTH_SHORT).show();
//            }
        } else {
            Toast.makeText(this, "Changes are not saved", Toast.LENGTH_SHORT).show();
        }


        Log.v(TAG, "Scheduler - AppointmentAddActivity - onActivityResult - customerId " + customerId
                + "petId" + petId);

    }

    // ImageButton Customer
    public void enter_customerList(View view) {
        Intent intent = new Intent(AppointmentAddActivity.this, AppointmentCustomerListActivity.class);
        startActivityForResult(intent, ADD_CUSTOMER_TO_APPOINTMENT_REQUEST);
    }

    // ImageButton Pet
    public void enter_petList(View view) {
        Intent intent = new Intent(AppointmentAddActivity.this, AppointmentPetListActivity.class);
        customerId = getIntent().getIntExtra(EXTRA_CUSTOMER_ID, customerId);
        petViewModel.getPetByCustomerId(customerId);
        intent.putExtra(AppointmentPetListActivity.EXTRA_CUSTOMER_ID, customerId);
        startActivityForResult(intent, ADD_PET_TO_APPOINTMENT_REQUEST);

        Log.v(TAG, "Scheduler - AppointmentAddActivity - onClick - PetList - CustomerId " + customerId);
    }


    // Save appointment iconButton method
    private void saveAppointment() {
        // Get input from editText views
        date = textView_date.getText().toString();
        time = textView_time.getText().toString();
        customerName = textView_customerName.getText().toString();
        petName = textView_petName.getText().toString();
        serviceType = textView_serviceType.getText().toString();
        // Input validation for empty fields
        if (date.trim().isEmpty() || time.trim().isEmpty() || serviceType.trim().isEmpty() || customerName.trim().isEmpty() || petName.trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }


        // Accept input and save appointment - send data back
        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_APPOINTMENT_TIME, time);
        dataIntent.putExtra(EXTRA_APPOINTMENT_DATE, date);
        dataIntent.putExtra(EXTRA_CUSTOMER_ID, customerId);
        dataIntent.putExtra(EXTRA_CUSTOMER_NAME, customerName);
        dataIntent.putExtra(EXTRA_PET_ID, petId);
        dataIntent.putExtra(EXTRA_SERVICE_DURATION, duration);
        dataIntent.putExtra(EXTRA_SERVICE_LOCATION, location);
        dataIntent.putExtra(EXTRA_SERVICE_TYPE, serviceType);
        dataIntent.putExtra(EXTRA_SERVICE_OPTION, option);



//                int newAppointmentId = appointmentViewModel.getAppointmentIdForService();
//                newAppointmentId++;


//        Appointment appointment = new Appointment(employeeId, customerId, petId, date, time);
//        appointmentViewModel.insert(appointment);


        Log.v(TAG, "Scheduler - AppointmentAddActivity - saveAppointment newAppointmentId" );

//        int appointmentId = getIntent().getIntExtra(EXTRA_APPOINTMENT_ID, -1);
//        if (appointmentId != -1) {
//            dataIntent.putExtra(EXTRA_APPOINTMENT_ID, appointmentId);
//        }

        //        int newAppointmentId = appointmentViewModel.getAppointmentIdForService();
//        newAppointmentId++;
//


//        if (serviceType.equals("Walking")) {
//            ServiceOption serviceOption1 = new ServiceOption(duration, location, serviceType, newAppointmentId, option);
//            serviceOptionViewModel.insert(serviceOption1);
//        } else if (serviceType.equals("Playing")) {
//            ServiceOption serviceOption = new ServiceOption(duration, serviceType, newAppointmentId, option);
//            serviceOptionViewModel.insert(serviceOption);
//        }else {
//            Toast.makeText(this, "Service not saved", Toast.LENGTH_SHORT).show();
//        }


        Log.v(TAG, "Scheduler - AppointmentAddActivity - saveAppointment appointmentId " + appointmentId);

            setResult(RESULT_OK, dataIntent);
            finish();


    }


    // Create actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appointment_add_menu, menu);
        return true;
    }

    // Handle backwards arrow in actionbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_appointment:
                saveAppointment();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}