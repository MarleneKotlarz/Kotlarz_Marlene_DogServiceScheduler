package com.kotlarz_marlene_dogservicescheduler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.Database.SchedulerRepository;
import com.kotlarz_marlene_dogservicescheduler.Entity.Employee;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {

    private SchedulerRepository schedulerRepository;
    private LiveData<List<Employee>> allEmployees;

    // Constructor
    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        schedulerRepository = new SchedulerRepository(application);
        allEmployees = schedulerRepository.getAllEmployees();
    }

    // Wrapper methods for the data operation methods(created in the repository).
    public void insert(Employee employee) {
        schedulerRepository.insert(employee);
    }

    public void update(Employee employee) {
        schedulerRepository.update(employee);
    }

    public void delete(Employee employee) {
        schedulerRepository.delete(employee);
    }

    public LiveData<List<Employee>> getAllEmployees() {
        return allEmployees;
    }


}
