package com.kotlarz_marlene_dogservicescheduler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.kotlarz_marlene_dogservicescheduler.Adapter.PetAdapter;
import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.PetViewModel;

import java.util.List;

public class AppointmentPetListActivity extends AppCompatActivity {

    // Intent Extra Keys
    public static final String EXTRA_CUSTOMER_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ID";
    public static final String EXTRA_CUSTOMER_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_NAME";
    public static final String EXTRA_PET_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_PET_ID";
    public static final String EXTRA_PET_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_PET_NAME";

    private PetViewModel petViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_pet_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Appointment Pet List");

        // Set values
        Intent intent = getIntent();
        int customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1);
        String customerName = intent.getStringExtra(EXTRA_CUSTOMER_NAME);

        // Reference to RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_appointmentPetList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Reference adapter
        final PetAdapter adapter = new PetAdapter();
        recyclerView.setAdapter(adapter);

        // Assign ViewModel
        petViewModel = new ViewModelProvider(this).get(PetViewModel.class);

        petViewModel.getPetByCustomerId(customerId).observe(this, new Observer<List<Pet>>() {
            @Override
            public void onChanged(List<Pet> pets) {
                if (pets.isEmpty()) {
                    Toast.makeText(AppointmentPetListActivity.this, "No pets found", Toast.LENGTH_SHORT).show();
                }
                adapter.setPets(pets);
            }
        });

        // onClick - send customer and pet data back to AppointmentAddActivity
        adapter.setOnItemClickListener(new PetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pet pet) {
                Intent data = new Intent(AppointmentPetListActivity.this, AppointmentAddActivity.class);
                data.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                data.putExtra(AppointmentPetListActivity.EXTRA_CUSTOMER_ID, customerId);
                data.putExtra(AppointmentPetListActivity.EXTRA_CUSTOMER_NAME, customerName);
                data.putExtra(AppointmentPetListActivity.EXTRA_PET_ID, pet.getPet_id());
                data.putExtra(AppointmentPetListActivity.EXTRA_PET_NAME, pet.getPet_name());

                setResult(RESULT_OK, data);
                finish();
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