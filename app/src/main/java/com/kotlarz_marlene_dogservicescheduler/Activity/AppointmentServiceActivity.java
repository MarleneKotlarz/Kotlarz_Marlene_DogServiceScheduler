package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kotlarz_marlene_dogservicescheduler.R;

import java.util.ArrayList;

public class AppointmentServiceActivity extends AppCompatActivity {

    public static final String EXTRA_SERVICE_DURATION =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_SERVICE_DURATION";
    public static final String EXTRA_SERVICE_LOCATION =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_SERVICE_LOCATION";
    public static final String EXTRA_SERVICE_TYPE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_SERVICE_TYPE";
    public static final String EXTRA_SERVICE_OPTION =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_SERVICE_OPTION";

    Spinner spinnerDuration;
    Spinner spinnerLocation;
    Spinner spinnerType;
    Spinner spinnerOption;

    ArrayList<String> arrayList_duration;
    ArrayAdapter<String> arrayAdapter_duration;

    ArrayList<String> arrayList_playing_location, arrayList_walking_location;
    ArrayAdapter<String> arrayAdapter_location;

    ArrayList<String> arrayList_type;
    ArrayAdapter<String> arrayAdapter_type;

    ArrayList<String> arrayList_intensity, arrayList_toy;
    ArrayAdapter<String> arrayAdapter_option;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_service);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Service");

        spinnerDuration = findViewById(R.id.spinner_appointmentAdd_duration);
        spinnerLocation = findViewById(R.id.spinner_appointmentAdd_location);
        spinnerType = findViewById(R.id.spinner_appointmentAdd_type);
        spinnerOption = findViewById(R.id.spinner_appointmentAdd_option);

        // Assign Duration spinner
        arrayList_duration = new ArrayList<>();
        arrayList_duration.add("15 minutes");
        arrayList_duration.add("30 minutes");
        arrayList_duration.add("45 minutes");
        arrayList_duration.add("60 minutes");

        arrayAdapter_duration = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_duration);
        spinnerDuration.setAdapter(arrayAdapter_duration);

        // Assign Type spinner
        arrayList_type = new ArrayList<>();
        arrayList_type.add("Walking");
        arrayList_type.add("Playing");

        arrayAdapter_type = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_type);
        spinnerType.setAdapter(arrayAdapter_type);

        // Assign Location spinner
        arrayList_walking_location = new ArrayList<>();
        arrayList_walking_location.add("Home");
        arrayList_walking_location.add("Park");
        arrayList_walking_location.add("Beach");

        arrayList_playing_location = new ArrayList<>();
        arrayList_playing_location.add("Home");

        // Assign Option spinner
        arrayList_intensity = new ArrayList<>();
        arrayList_intensity.add("Low intensity");
        arrayList_intensity.add("Moderate intensity");
        arrayList_intensity.add("High intensity");

        arrayList_toy = new ArrayList<>();
        arrayList_toy.add("Rope");
        arrayList_toy.add("Frisbee");
        arrayList_toy.add("Ball");

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    arrayAdapter_location = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_walking_location);
                    arrayAdapter_option = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_intensity);
                }
                if (position == 1) {
                    arrayAdapter_location = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_playing_location);
                    arrayAdapter_option = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_toy);
                }
                spinnerLocation.setAdapter(arrayAdapter_location);
                spinnerOption.setAdapter(arrayAdapter_option);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    // Save service to appointment
    private void saveService() {
        String duration = spinnerDuration.getSelectedItem().toString();
        String location = spinnerLocation.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();
        String option = spinnerOption.getSelectedItem().toString();

        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_SERVICE_DURATION, duration);
        dataIntent.putExtra(EXTRA_SERVICE_LOCATION, location);
        dataIntent.putExtra(EXTRA_SERVICE_TYPE, type);
        dataIntent.putExtra(EXTRA_SERVICE_OPTION, option);

        setResult(RESULT_OK, dataIntent);
        finish();
    }

    // Create actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appointment_service_menu, menu);
        return true;
    }

    // Handle backwards arrow in actionbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_service:
                saveService();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}