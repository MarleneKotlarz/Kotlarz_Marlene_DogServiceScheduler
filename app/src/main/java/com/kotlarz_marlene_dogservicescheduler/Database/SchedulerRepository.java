package com.kotlarz_marlene_dogservicescheduler.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kotlarz_marlene_dogservicescheduler.DAO.AppointmentDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.CustomerDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.EmployeeDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.PetDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.ServiceOptionDao;
import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.Entity.AppointmentAndServiceOption;
import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;
import com.kotlarz_marlene_dogservicescheduler.Entity.Employee;
import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;
import com.kotlarz_marlene_dogservicescheduler.Entity.ServiceOption;

import java.util.List;

public class SchedulerRepository {

    // Create member variables
    private AppointmentDao appointmentDao;
    private CustomerDao customerDao;
    private EmployeeDao employeeDao;
    private PetDao petDao;
    private ServiceOptionDao serviceOptionDao;

    private LiveData<List<Appointment>> allAppointments;
    private LiveData<List<Customer>> allCustomers;
    private LiveData<List<Employee>> allEmployees;
    private LiveData<List<Pet>> allPets;
    private LiveData<List<ServiceOption>> allServiceOptions;
    private LiveData<List<AppointmentAndServiceOption>> allApptServices;
    private Integer getAppointmentIdForService = 0;
//    private LiveData<List<AppointmentAndServiceOption>> apptServiceApptId;
//
//    private LiveData<List<AppointmentAndServiceOption>> deleteAllApptServices;


    // Constructor = Application is a subclass of Context
    public SchedulerRepository(Application application) {
        SchedulerDatabase schedulerDatabase = SchedulerDatabase.getDatabase(application);
        // Call abstract dao interfaces from DB class
        appointmentDao = schedulerDatabase.appointmentDao();
        customerDao = schedulerDatabase.customerDao();
        employeeDao = schedulerDatabase.employeeDao();
        petDao = schedulerDatabase.petDao();
        serviceOptionDao = schedulerDatabase.serviceOptionDao();

        allAppointments = appointmentDao.getAllAppointments();
        allCustomers = customerDao.getAllCustomers();
        allEmployees = employeeDao.getAllEmployees();
        allPets = petDao.getAllPets();
        allServiceOptions = serviceOptionDao.getAllServiceOptions();
        getAppointmentIdForService = appointmentDao.getAppointmentIdForService();
        allApptServices = appointmentDao.getAppointmentAndServiceOptions();


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


    public int getAppointmentIdForService() {
        getAppointmentIdForService = appointmentDao.getAppointmentIdForService();
        return getAppointmentIdForService;
    }


    // Used in ReportActivity to get list of appt and services
    public LiveData<List<AppointmentAndServiceOption>> getAppointmentAndServiceOptions() {
        return allApptServices;
    }

    // Used for AppointmentDetails Activity to get Service by appointmentId
    public LiveData<List<AppointmentAndServiceOption>> getAppointmentAndServiceByApptId(int appointmentId) {
        return appointmentDao.getAppointmentAndServiceByApptId(appointmentId);
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


    public void insert(ServiceOption serviceOption) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            serviceOptionDao.insert(serviceOption);
        });
    }

    public void update(ServiceOption serviceOption) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            serviceOptionDao.update(serviceOption);
        });
    }

    public void delete(ServiceOption serviceOption) {
        SchedulerDatabase.executorServiceDB.execute(() -> {
            serviceOptionDao.delete(serviceOption);
        });
    }

    public LiveData<List<ServiceOption>> getAllServiceOptions() {
        return allServiceOptions;
    }


}
