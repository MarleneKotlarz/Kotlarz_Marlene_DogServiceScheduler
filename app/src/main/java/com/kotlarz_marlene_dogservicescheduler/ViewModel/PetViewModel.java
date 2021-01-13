package com.kotlarz_marlene_dogservicescheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.Database.SchedulerRepository;
import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;

import java.util.List;

public class PetViewModel extends AndroidViewModel {

    // Member variables
    private SchedulerRepository schedulerRepository;
    private LiveData<List<Pet>> allPets;

    // Constructor
    public PetViewModel(@NonNull Application application) {
        super(application);
        // Instantiate repository and allPets
        schedulerRepository = new SchedulerRepository(application);
        allPets = schedulerRepository.getAllPets();
    }

    // Wrapper methods for the data operation methods(created in the repository).
    public void insert(Pet pet) {
        schedulerRepository.insert(pet);
    }

    public void update(Pet pet) {
        schedulerRepository.update(pet);
    }

    public void delete(Pet pet) {
        schedulerRepository.delete(pet);
    }

    public void deleteAllPets() {
        schedulerRepository.deleteAllPets();
    }

    // Return the liveData list
    public LiveData<List<Pet>> getAllPets() {
        return allPets;
    }

    public LiveData<List<Pet>> getPetByCustomerId(int customerIdFk) {
        return schedulerRepository.getPetByCustomerId(customerIdFk);
    }
}
