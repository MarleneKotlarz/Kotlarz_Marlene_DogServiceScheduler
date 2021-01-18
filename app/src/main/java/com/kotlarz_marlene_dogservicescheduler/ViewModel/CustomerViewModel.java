package com.kotlarz_marlene_dogservicescheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.Database.SchedulerRepository;
import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {

    // Member variables
    private SchedulerRepository schedulerRepository;
    private LiveData<List<Customer>> allCustomers;

    // Constructor
    public CustomerViewModel(@NonNull Application application) {
        super(application);
        // Instantiate repository and allAppointments
        schedulerRepository = new SchedulerRepository(application);
        allCustomers = schedulerRepository.getAllCustomers();

    }

    // Wrapper methods for the data operation methods(created in the repository).
    public void insert(Customer customer) {
        schedulerRepository.insert(customer);
    }

    public void update(Customer customer) {
        schedulerRepository.update(customer);
    }

    public void delete(Customer customer) {
        schedulerRepository.delete(customer);
    }

    // Return the liveData list
    public LiveData<List<Customer>> getAllCustomers() {
        if (allCustomers == null) {
            allCustomers = new LiveData<List<Customer>>() {
            };
        }
        return allCustomers;
    }


}
