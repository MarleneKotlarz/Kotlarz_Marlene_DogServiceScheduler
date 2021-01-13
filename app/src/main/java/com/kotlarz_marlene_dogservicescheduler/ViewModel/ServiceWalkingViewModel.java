package com.kotlarz_marlene_dogservicescheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.Database.SchedulerRepository;
import com.kotlarz_marlene_dogservicescheduler.Entity.ServiceWalking;

import java.util.List;

public class ServiceWalkingViewModel extends AndroidViewModel {

    private SchedulerRepository schedulerRepository;
    private LiveData<List<ServiceWalking>> allServices;

    public ServiceWalkingViewModel(@NonNull Application application) {
        super(application);
        schedulerRepository = new SchedulerRepository(application);
        allServices = schedulerRepository.getAllServices();
    }

    // Wrapper methods for the data operation methods(created in the repository).
    public void insert(ServiceWalking serviceWalking) {
        schedulerRepository.insert(serviceWalking);
    }

    public void update(ServiceWalking serviceWalking) {
        schedulerRepository.update(serviceWalking);
    }

    public void delete(ServiceWalking serviceWalking) {
        schedulerRepository.delete(serviceWalking);
    }

    public LiveData<List<ServiceWalking>> getAllServices() { return allServices; }



}
