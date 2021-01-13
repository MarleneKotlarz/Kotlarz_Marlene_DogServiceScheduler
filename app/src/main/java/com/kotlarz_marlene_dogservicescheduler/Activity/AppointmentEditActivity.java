package com.kotlarz_marlene_dogservicescheduler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.Utilities.DatePickerFragment;
import com.kotlarz_marlene_dogservicescheduler.Utilities.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class AppointmentEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    // Intent Extra Keys
    public static final String EXTRA_APPOINTMENT_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_ID";
    public static final String EXTRA_APPOINTMENT_DATE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_DATE";
    public static final String EXTRA_APPOINTMENT_TIME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_APPOINTMENT_TIME";

    private static final String TAG = "Scheduler";

    Calendar calendar;
    private EditText editText_appointmentDate, editText_appointmentTime;
    private String date, time;
    private int appointmentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Appointment");

        // Assign fields
        editText_appointmentDate = findViewById(R.id.editText_appointmentEdit_date);
        editText_appointmentTime = findViewById(R.id.editText_appointmentEdit_time);
        ImageButton imageButton_date = findViewById(R.id.imageButton_date);
        ImageButton imageButton_time = findViewById(R.id.imageButton_time);

        // Receiving data from AppointmentDetailsActivity
        Intent dataIntent = getIntent();
        if (dataIntent.hasExtra(EXTRA_APPOINTMENT_ID)) {
            appointmentId = dataIntent.getIntExtra(EXTRA_APPOINTMENT_ID, -1);
            editText_appointmentDate.setText(dataIntent.getStringExtra(EXTRA_APPOINTMENT_DATE));
            editText_appointmentTime.setText(dataIntent.getStringExtra(EXTRA_APPOINTMENT_TIME));
        }

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


    }

    // DatePicker - set up EditText to selected date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        editText_appointmentDate.setText(currentDateString);

    }

    // TimePicker - set up EditText to selected time
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String currentTime = String.format("%02d:%02d", hourOfDay, minute); // set date as String to include leading zeros
        editText_appointmentTime.setText(currentTime);
    }

    // Save appointment iconButton method
    private void saveAppointment() {
        // Get input from editText views
        date = editText_appointmentDate.getText().toString();
        time = editText_appointmentTime.getText().toString();
        // Input validation for empty fields
        if (date.trim().isEmpty() || time.trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Accept input and save appointment - send data back
        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_APPOINTMENT_TIME, time);
        dataIntent.putExtra(EXTRA_APPOINTMENT_DATE, date);

        int id = getIntent().getIntExtra(EXTRA_APPOINTMENT_ID, -1);
        if(id != -1) {
            dataIntent.putExtra(EXTRA_APPOINTMENT_ID, id);
        }

        Log.v(TAG, "Scheduler - AppointmentAddActivity - saveAppointment ");

        setResult(RESULT_OK, dataIntent);
        finish();

    }

    // Create actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appointment_edit_menu, menu);
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