package com.kotlarz_marlene_dogservicescheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.Database.SchedulerRepository;
import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;
import com.kotlarz_marlene_dogservicescheduler.Entity.Service;

import java.util.List;

public class ServiceViewModel extends AndroidViewModel {
    public ServiceViewModel(@NonNull Application application) {
        super(application);
    }

//    private SchedulerRepository schedulerRepository;
//    private LiveData<List<Service>> allServices;
//
//    public ServiceViewModel(@NonNull Application application) {
//        super(application);
//        schedulerRepository = new SchedulerRepository(application);
//        allServices = schedulerRepository.getAllServices();
//
//    }
//
//    // Wrapper methods for the data operation methods(created in the repository).
//    public void insert(Service service) {
//        schedulerRepository.insert(service);
//    }
//
//    public void update(Service service) {
//        schedulerRepository.update(service);
//    }
//
//    public void delete(Service service) {
//        schedulerRepository.delete(service);
//    }
//
//    public LiveData<List<Service>> getAllServices() { return allServices; }


}
