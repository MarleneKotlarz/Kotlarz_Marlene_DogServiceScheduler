package com.kotlarz_marlene_dogservicescheduler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;
import com.kotlarz_marlene_dogservicescheduler.R;
import com.kotlarz_marlene_dogservicescheduler.ViewModel.PetViewModel;

public class PetDetailsActivity extends AppCompatActivity {

    public static final int EDIT_PET_REQUEST = 2;

    // Intent Extra Keys
    public static final String EXTRA_CUSTOMER_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_CUSTOMER_ID";
    public static final String EXTRA_PET_ID =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_PET_ID";
    public static final String EXTRA_PET_NAME =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_PET_NAME";
    public static final String EXTRA_PET_BREED =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_PET_BREED";
    public static final String EXTRA_PET_AGE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_PET_AGE";
    public static final String EXTRA_PET_NOTE =
            "com.kotlarz_marlene_dogservicescheduler.Activity.EXTRA_PET_NOTE";


    private static final String TAG = "Scheduler";

    private PetViewModel petViewModel;
    TextView textView_petName, textView_petBreed, textView_petAge, textView_petNote;
    private int customerId, petId;
    private String petName, petBreed, petAge, petNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Pet Details");

        // Get the PetViewModel
        petViewModel = new ViewModelProvider(this).get(PetViewModel.class);

        // Assign fields
        textView_petName = findViewById(R.id.textView_pet_name1);
        textView_petBreed = findViewById(R.id.textView_pet_breed1);
        textView_petAge = findViewById(R.id.textView_pet_age1);
        textView_petNote = findViewById(R.id.textView_pet_note1);

        // Populate fields by getting data from PetListActivity
        Intent intent = getIntent();
        textView_petName.setText(intent.getStringExtra(EXTRA_PET_NAME));
        textView_petBreed.setText(intent.getStringExtra(EXTRA_PET_BREED));
        textView_petAge.setText(intent.getStringExtra(EXTRA_PET_AGE));
        textView_petNote.setText(intent.getStringExtra(EXTRA_PET_NOTE));

        // Set field values
        customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1);
        petId = intent.getIntExtra(EXTRA_PET_ID, -1);
        petName = intent.getStringExtra(EXTRA_PET_NAME);
        petBreed = intent.getStringExtra(EXTRA_PET_BREED);
        petAge = intent.getStringExtra(EXTRA_PET_AGE);
        petNote = intent.getStringExtra(EXTRA_PET_NOTE);

        // FAB edit customer button - open PetAddEditActivity
        FloatingActionButton fabEditPet = findViewById(R.id.fab_pet_edit);
        fabEditPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petName = textView_petName.getText().toString();
                petBreed = textView_petBreed.getText().toString();
                petAge = textView_petAge.getText().toString();
                petNote = textView_petNote.getText().toString();

                Intent editIntent = new Intent(PetDetailsActivity.this, PetAddEditActivity.class);
                editIntent.putExtra(PetAddEditActivity.EXTRA_PET_NAME, petName);
                editIntent.putExtra(PetAddEditActivity.EXTRA_PET_BREED, petBreed);
                editIntent.putExtra(PetAddEditActivity.EXTRA_PET_AGE, petAge);
                editIntent.putExtra(PetAddEditActivity.EXTRA_PET_NOTE, petNote);
                petId = getIntent().getIntExtra(EXTRA_PET_ID, -1);
                if (petId != -1) {
                    editIntent.putExtra(EXTRA_PET_ID, petId);
                }
                startActivityForResult(editIntent, EDIT_PET_REQUEST);
            }
        });

        Log.v(TAG, "Scheduler - PetDetailsActivity - onCreate ");
    }

    // Get results back from editing a pet
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PET_REQUEST && resultCode == RESULT_OK) {
            // Get Intent Keys from CourseAddEditActivity
            textView_petName = findViewById(R.id.textView_pet_name1);
            textView_petBreed = findViewById(R.id.textView_pet_breed1);
            textView_petAge = findViewById(R.id.textView_pet_age1);
            textView_petNote = findViewById(R.id.textView_pet_note1);

            petName = data.getStringExtra(PetAddEditActivity.EXTRA_PET_NAME);
            petBreed = data.getStringExtra(PetAddEditActivity.EXTRA_PET_BREED);
            petAge = data.getStringExtra(PetAddEditActivity.EXTRA_PET_AGE);
            petNote = data.getStringExtra(PetAddEditActivity.EXTRA_PET_NOTE);

            textView_petName.setText(petName);
            textView_petBreed.setText(petBreed);
            textView_petAge.setText(petAge);
            textView_petNote.setText(petNote);

            petId = data.getIntExtra(PetAddEditActivity.EXTRA_PET_ID, -1);
            if (petId == -1) {
                Toast.makeText(this, "Pet can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            Pet pet = new Pet(customerId, petAge, petName, petBreed, petNote);
            pet.setPet_id(petId);
            petViewModel.update(pet);

            Toast.makeText(this, "Pet updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Pet not updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Create actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pet_details_menu, menu);
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