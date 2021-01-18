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

import com.kotlarz_marlene_dogservicescheduler.Adapter.ReportAdapter;
import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.Entity.AppointmentAndServiceOption;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.AppointmentViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.CustomerViewModel;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.ServiceOptionViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private AppointmentViewModel appointmentViewModel;
    private ServiceOptionViewModel serviceOptionViewModel;

    RecyclerView recyclerView;
    ReportAdapter adapter;
    private List<AppointmentAndServiceOption> apptServiceList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Report");

        recyclerView = findViewById(R.id.recyclerView_reportList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        serviceOptionViewModel = new ViewModelProvider(this).get(ServiceOptionViewModel.class);

        adapter = new ReportAdapter();
        recyclerView.setAdapter(adapter);


        appointmentViewModel.getAppointmentAndServiceOptions().observe(this, new Observer<List<AppointmentAndServiceOption>>() {
            @Override
            public void onChanged(List<AppointmentAndServiceOption> appointmentAndServiceOptions) {

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