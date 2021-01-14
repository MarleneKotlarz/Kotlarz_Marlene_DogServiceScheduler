package com.kotlarz_marlene_dogservicescheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.Database.SchedulerRepository;
import com.kotlarz_marlene_dogservicescheduler.Entity.ServiceOption;

import java.util.List;

public class ServiceOptionViewModel extends AndroidViewModel {

    private SchedulerRepository schedulerRepository;
    private LiveData<List<ServiceOption>> allServiceOptions;

    public ServiceOptionViewModel(@NonNull Application application) {
        super(application);
        schedulerRepository = new SchedulerRepository(application);
        allServiceOptions = schedulerRepository.getAllServiceOptions();
    }

    // Wrapper methods for the data operation methods(created in the repository).
    public void insert(ServiceOption serviceOption) {
        schedulerRepository.insert(serviceOption);
    }

    public void update(ServiceOption serviceOption) {
        schedulerRepository.update(serviceOption);
    }

    public void delete(ServiceOption serviceOption) {
        schedulerRepository.delete(serviceOption);
    }

    public LiveData<List<ServiceOption>> getAllServiceOptions() { return allServiceOptions; }

}
