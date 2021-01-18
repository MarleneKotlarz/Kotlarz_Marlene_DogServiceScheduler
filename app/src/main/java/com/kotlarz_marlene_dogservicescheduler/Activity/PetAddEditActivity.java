package com.kotlarz_marlene_dogservicescheduler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.kotlarz_marlene_dogservicescheduler.R;

public class PetAddEditActivity extends AppCompatActivity {

    // Intent Extra Keys
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

    // Member variables
    private EditText editText_petName, editText_petBreed, editText_petAge, editText_note;
    private String petName, petBreed, petAge, petNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Assign fields
        editText_petName = findViewById(R.id.editText_petAddEdit_name);
        editText_petBreed = findViewById(R.id.editText_petAddEdit_breed);
        editText_petAge = findViewById(R.id.editText_petAddEdit_age);
        editText_note = findViewById(R.id.editText_petAddEdit_note);

        // Receiving data form CustomerListActivity and CustomerDetailsActivity
        Intent dataIntent = getIntent();
        if (dataIntent.hasExtra(EXTRA_PET_ID)) {
            setTitle("Edit pet");
            editText_petName.setText(dataIntent.getStringExtra(EXTRA_PET_NAME));
            editText_petBreed.setText(dataIntent.getStringExtra(EXTRA_PET_BREED));
            editText_petAge.setText(dataIntent.getStringExtra(EXTRA_PET_AGE));
            editText_note.setText(dataIntent.getStringExtra(EXTRA_PET_NOTE));
        } else {
            setTitle("Add pet");
        }

        Log.v(TAG, "Scheduler - PetAddEditActivity - onCreate ");

    }

    private void savePet() {
        // Get input from editText views
        petName = editText_petName.getText().toString();
        petBreed = editText_petBreed.getText().toString();
        petAge = editText_petAge.getText().toString();
        petNote = editText_note.getText().toString();

        // Input validation for empty fields
        if (petName.trim().isEmpty() || petBreed.trim().isEmpty() || petAge.trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Accept input and save Pet - requires Intent extra key -Create intent to send back data.
        Intent data = new Intent();
        data.putExtra(EXTRA_PET_NAME, petName);
        data.putExtra(EXTRA_PET_BREED, petBreed);
        data.putExtra(EXTRA_PET_AGE, petAge);
        data.putExtra(EXTRA_PET_NOTE, petNote);

        int id = getIntent().getIntExtra(EXTRA_PET_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_PET_ID, id);
        }
        Log.v(TAG, "Scheduler - PetAddEditActivity - savePet");

        setResult(RESULT_OK, data);
        finish();
    }

    // Add menu bar to actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pet_add_edit_menu, menu);
        return true;
    }


    // Handle backwards arrow and save Course in actionbar.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_pet:
                savePet();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}