package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kotlarz_marlene_dogservicescheduler.Adapter.PetAdapter;
import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.PetViewModel;

import java.util.List;

public class PetListActivity extends AppCompatActivity {

    public static final int ADD_PET_REQUEST = 1;

    // Intent Extra Keys
    public static final String EXTRA_CUSTOMER_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ID";

    private PetViewModel petViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Pet List");

        Intent intent =getIntent();
        int customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1);

        // Reference to RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_petList);
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
                if(pets.isEmpty()) {
                    Toast.makeText(PetListActivity.this, "No pets found", Toast.LENGTH_SHORT).show();
                }
                adapter.setPets(pets);
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
                petViewModel.delete(adapter.getPetAt(viewHolder.getAdapterPosition()));
                Toast.makeText(PetListActivity.this, "Pet deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        // onClick - view pet details in PetDetailsActivity
        adapter.setOnItemClickListener(new PetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pet pet) {
                Intent dataIntent = new Intent(PetListActivity.this, PetDetailsActivity.class);
                dataIntent.putExtra(PetDetailsActivity.EXTRA_CUSTOMER_ID, pet.getCustomer_id_fk());
                dataIntent.putExtra(PetDetailsActivity.EXTRA_PET_ID, pet.getPet_id());
                dataIntent.putExtra(PetDetailsActivity.EXTRA_PET_NAME, pet.getPet_name());
                dataIntent.putExtra(PetDetailsActivity.EXTRA_PET_BREED, pet.getPet_breed());
                dataIntent.putExtra(PetDetailsActivity.EXTRA_PET_AGE, pet.getPet_age());
                dataIntent.putExtra(PetDetailsActivity.EXTRA_PET_NOTE, pet.getPet_note());
                startActivity(dataIntent);
            }
        });

        // FAB to add customer in PetAddEditActivity
        FloatingActionButton fabAddPet = findViewById(R.id.fab_pet_add);
        fabAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(PetListActivity.this, PetAddEditActivity.class);
                startActivityForResult(addIntent, ADD_PET_REQUEST);
            }
        });

    }

    // Get results back from PetAddEditActivity savepet method.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PET_REQUEST && resultCode == RESULT_OK) {
            String petName = data.getStringExtra(PetAddEditActivity.EXTRA_PET_NAME);
            String petBreed = data.getStringExtra(PetAddEditActivity.EXTRA_PET_BREED);
            String petAge = data.getStringExtra(PetAddEditActivity.EXTRA_PET_AGE);
            String petNote = data.getStringExtra(PetAddEditActivity.EXTRA_PET_NOTE);

            int customerId = getIntent().getIntExtra(EXTRA_CUSTOMER_ID, -1);
            if (customerId != -1) {
                data.putExtra(EXTRA_CUSTOMER_ID, customerId);
            }
            // Create new pet
            Pet pet = new Pet(customerId, petAge, petName, petBreed, petNote);
            petViewModel.insert(pet);

            Toast.makeText(this, "Pet saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Pet not saved", Toast.LENGTH_SHORT).show();
        }
    }

    // Create actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pet_list_menu, menu);
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