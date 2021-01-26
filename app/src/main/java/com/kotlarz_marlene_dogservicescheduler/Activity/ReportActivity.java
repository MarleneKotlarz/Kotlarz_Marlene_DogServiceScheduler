package com.kotlarz_marlene_dogservicescheduler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.kotlarz_marlene_dogservicescheduler.Adapter.ReportAdapter;
import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.Entity.AppointmentAndServiceOption;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.AppointmentViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.CustomerViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.ServiceOptionViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//---------- ABILITY TO GENERATE REPORTS ----------//

public class ReportActivity extends AppCompatActivity {

    private AppointmentViewModel appointmentViewModel;
    RecyclerView recyclerView;
    ReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm a");
        String currentDateTime = dateFormat.format(new Date());
        setTitle("Report: " + currentDateTime);

        recyclerView = findViewById(R.id.recyclerView_reportList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        adapter = new ReportAdapter();
        recyclerView.setAdapter(adapter);

        appointmentViewModel.getAppointmentAndServiceOptions().observe(this, new Observer<List<AppointmentAndServiceOption>>() {
            @Override
            public void onChanged(List<AppointmentAndServiceOption> appointmentAndServiceOptions) {
                if (appointmentAndServiceOptions.isEmpty()) {
                    Toast.makeText(ReportActivity.this, "No appointments found", Toast.LENGTH_SHORT).show();
                }
                adapter.setAppointmentServiceList(appointmentAndServiceOptions);
            }
        });
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