package com.kotlarz_marlene_dogservicescheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.Database.SchedulerRepository;
import com.kotlarz_marlene_dogservicescheduler.Entity.ServicePlaying;
import com.kotlarz_marlene_dogservicescheduler.Entity.ServiceWalking;

import java.util.List;

public class ServicePlayingViewModel extends AndroidViewModel {

    private SchedulerRepository schedulerRepository;
    private LiveData<List<ServicePlaying>> allPlayingServices;

    public ServicePlayingViewModel(@NonNull Application application) {
        super(application);
        schedulerRepository = new SchedulerRepository(application);
        allPlayingServices = schedulerRepository.getAllPlayingServices();
    }

    // Wrapper methods for the data operation methods(created in the repository).
    public void insert(ServicePlaying servicePlaying) {
        schedulerRepository.insert(servicePlaying);
    }

    public void update(ServicePlaying servicePlaying) {
        schedulerRepository.update(servicePlaying);
    }

    public void delete(ServicePlaying servicePlaying) {
        schedulerRepository.delete(servicePlaying);
    }

    public LiveData<List<ServicePlaying>> getAllPlayingServices() { return allPlayingServices; }

}
