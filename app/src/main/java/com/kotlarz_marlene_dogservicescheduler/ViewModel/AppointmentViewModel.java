package com.kotlarz_marlene_dogservicescheduler.ViewModel;


// ViewModel stores and processes data for the UI and communicate with the Model.
// It requests data from the repository so Activity/Fragment can draw it on the screen.
// Forwards user interactions from the UI back to the repository.


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.Database.SchedulerRepository;
import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.Entity.AppointmentAndServiceOption;

import java.util.List;

public class AppointmentViewModel extends AndroidViewModel {

    // Member variables
    private SchedulerRepository schedulerRepository;
    private LiveData<List<Appointment>> allAppointments;
    private LiveData<List<AppointmentAndServiceOption>> allApptServices;
    Integer getAppointmentIdForService;


    // Constructor
    public AppointmentViewModel(@NonNull Application application) {
        super(application);
        // Instantiate repository and allAppointments
        schedulerRepository = new SchedulerRepository(application);
        allAppointments = schedulerRepository.getAllAppointments();

        getAppointmentIdForService = schedulerRepository.getAppointmentIdForService();
        allApptServices = schedulerRepository.getAppointmentAndServiceOptions();

    }

   public int grabNewApptIDForService() {
        getAppointmentIdForService = schedulerRepository.getAppointmentIdForService();
       return getAppointmentIdForService;
    }

    // Wrapper methods for the data operation methods(created in the repository).
    public void insert(Appointment appointment) {
        schedulerRepository.insert(appointment);
    }

    public void update(Appointment appointment) {
        schedulerRepository.update(appointment);
    }

    public void delete(Appointment appointment) {
        schedulerRepository.delete(appointment);
    }

    public void deleteAllAppointments() {
        schedulerRepository.deleteAllAppointments();
    }


    public LiveData<List<Appointment>> getAllAppointments() {
        return allAppointments;
    }

    public Integer getAppointmentIdForService() {
        return getAppointmentIdForService;
    }


    public LiveData<List<AppointmentAndServiceOption>> getAppointmentAndServiceOptions() {
        return allApptServices;
    }


    public LiveData<List<AppointmentAndServiceOption>> getAppointmentAndServiceByApptId(int appointmentId) {
        return schedulerRepository.getAppointmentAndServiceByApptId(appointmentId);
    }


}
