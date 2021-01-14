package com.kotlarz_marlene_dogservicescheduler.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.DAO.AppointmentDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.CustomerDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.EmployeeDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.PetDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.ServicePlayingDao;
import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;
import com.kotlarz_marlene_dogservicescheduler.Entity.Employee;
import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;
import com.kotlarz_marlene_dogservicescheduler.Entity.ServicePlaying;

import java.util.List;

public class SchedulerRepository {

    // Create member variables
    private AppointmentDao appointmentDao;
    private CustomerDao customerDao;
    private EmployeeDao employeeDao;
    private PetDao petDao;
    public ServicePlayingDao servicePlayingDao;

    private LiveData<List<Appointment>> allAppointments;
    private LiveData<List<Customer>> allCustomers;
    private LiveData<List<Employee>> allEmployees;
    private LiveData<List<Pet>> allPets;
    private LiveData<List<ServicePlaying>> allPlayingServices;


    // Constructor = Application is a subclass of Context
    public SchedulerRepository(Application application) {
        SchedulerDatabase schedulerDatabase = SchedulerDatabase.getDatabase(application);
        // Call abstract dao interfaces from DB class
        appointmentDao = schedulerDatabase.appointmentDao();
        customerDao = schedulerDatabase.customerDao();
        employeeDao = schedulerDatabase.employeeDao();
        petDao = schedulerDatabase.petDao();
        servicePlayingDao = schedulerDatabase.servicePlayingDao();


        allAppointments = appointmentDao.getAllAppointments();
        allCustomers = customerDao.getAllCustomers();
        allEmployees = employeeDao.getAllEmployees();
        allPets = petDao.getAllPets();
        allPlayingServices = servicePlayingDao.getAllPlayingServices();


    }


    //************ APPOINTMENT DB OPERATIONS ************//

    public void insert(Appointment appointment) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            appointmentDao.insert(appointment);
        });
    }

    public void update(Appointment appointment) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            appointmentDao.update(appointment);
        });
    }

    public void delete(Appointment appointment) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            appointmentDao.delete(appointment);
        });
    }

    public void deleteAllAppointments() {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            appointmentDao.deleteAllAppointments();
        });
    }

    public LiveData<List<Appointment>> getAllAppointments() {
        return allAppointments;
    }


    //************ CUSTOMER DB OPERATIONS ************//

    public void insert(Customer customer) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            customerDao.insert(customer);
        });
    }

    public void update(Customer customer) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            customerDao.update(customer);
        });
    }

    public void delete(Customer customer) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            customerDao.delete(customer);
        });
    }

    public void deleteAllCustomers() {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            customerDao.deleteAllCustomers();
        });
    }

    public LiveData<List<Customer>> getAllCustomers() {
        return allCustomers;
    }


    //************ EMPLOYEE DB OPERATIONS ************//

    public void insert(Employee employee) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            employeeDao.insert(employee);
        });
    }

    public void update(Employee employee) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            employeeDao.update(employee);
        });
    }

    public void delete(Employee employee) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            employeeDao.delete(employee);
        });
    }

    public void deleteAllEmployees() {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            employeeDao.deleteAllEmployees();
        });
    }

    public LiveData<List<Employee>> getAllEmployees() {
        return allEmployees;
    }


    //************ PET DB OPERATIONS ************//

    public void insert(Pet pet) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            petDao.insert(pet);
        });
    }

    public void update(Pet pet) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            petDao.update(pet);
        });
    }

    public void delete(Pet pet) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            petDao.delete(pet);
        });
    }

    public void deleteAllPets() {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            petDao.deleteAllPets();
        });
    }

    public LiveData<List<Pet>> getAllPets() {
        return allPets;
    }

    public LiveData<List<Pet>> getPetByCustomerId(int customerIdFk) {
        return petDao.getPetByCustomerId(customerIdFk);
    }


    //************ SERVICE DB OPERATIONS ************//


    public void insert(ServicePlaying servicePlaying) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            servicePlayingDao.insert(servicePlaying);
        });
    }

    public void update(ServicePlaying servicePlaying) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            servicePlayingDao.update(servicePlaying);
        });
    }

    public void delete(ServicePlaying servicePlaying) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            servicePlayingDao.delete(servicePlaying);
        });
    }

    public LiveData<List<ServicePlaying>> getAllPlayingServices() {
        return allPlayingServices;
    }


}
