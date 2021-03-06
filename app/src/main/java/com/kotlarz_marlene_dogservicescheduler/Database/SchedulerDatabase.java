package com.kotlarz_marlene_dogservicescheduler.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kotlarz_marlene_dogservicescheduler.DAO.AppointmentDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.CustomerDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.EmployeeDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.PetDao;
import com.kotlarz_marlene_dogservicescheduler.DAO.ServiceOptionDao;
import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.Entity.Customer;
import com.kotlarz_marlene_dogservicescheduler.Entity.Employee;
import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;
import com.kotlarz_marlene_dogservicescheduler.Entity.ServiceOption;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Appointment.class, Customer.class, Employee.class, Pet.class, ServiceOption.class}, version = 1, exportSchema = false)
public abstract class SchedulerDatabase extends RoomDatabase {

    private static final String TAG = "Scheduler";


    // Create methods that return/access the Dao Interfaces
    public abstract AppointmentDao appointmentDao();

    public abstract CustomerDao customerDao();

    public abstract EmployeeDao employeeDao();

    public abstract PetDao petDao();

    public abstract ServiceOptionDao serviceOptionDao();


    // Create singleton
    private static volatile SchedulerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    // ExecutorService with fixed thread pool, will be used to run DB operations asynchronously in the background thread
    static final ExecutorService executorServiceDB = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Create only DB instance that can be accessed from outside
    public static SchedulerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchedulerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SchedulerDatabase.class, "scheduler_database.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                    Log.v(TAG, "Scheduler - SchedulerDatabase - getDatabase ");

                }
            }
        }
        return INSTANCE;
    }

    // Only gets called during the first time the DB is created! App needs to be uninstalled/reinstalled to call this method
    // Pre-populate DB with sample data
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts comment out the following block
            executorServiceDB.execute(() -> {
                // Populate DB in the background
                AppointmentDao appointmentDao = INSTANCE.appointmentDao();
                CustomerDao customerDao = INSTANCE.customerDao();
                EmployeeDao employeeDao = INSTANCE.employeeDao();
                PetDao petDao = INSTANCE.petDao();
                ServiceOptionDao serviceOptionDao = INSTANCE.serviceOptionDao();

                Log.v(TAG, "Scheduler SchedulerDatabase - roomCallback method ");

                // Sample Code - make sure FK entity is created first
                Employee employee1 = new Employee("FirstName LastName", "###-###-####", "capstone");
                employeeDao.insert(employee1);

                Customer customer1 = new Customer(1, "Julia Roberts", "74 Rosebud, Charleston, SC 29414", "938-493-5323");
                customerDao.insert(customer1);

                Customer customer2 = new Customer(1, "George Clooney", "38 Main Street, Charleston, SC 29492", "843-204-0394");
                customerDao.insert(customer2);

                Pet pet1 = new Pet(1, "8", "Bruno", "Dachshund", "Poultry food allergy");
                petDao.insert(pet1);

                Pet pet2 = new Pet(2, "4", "Rosie", "French Bulldog", "Does not like big dogs");
                petDao.insert(pet2);

                Appointment appointment1 = new Appointment(1, 1, 1, "2/22/21", "09:30 AM");
                appointmentDao.insert(appointment1);

                Appointment appointment2 = new Appointment(1, 2, 2, "2/26/21", "02:00 PM");
                appointmentDao.insert(appointment2);

                ServiceOption service1 = new ServiceOption("45 minutes", "Park", "Walking", 1, "Moderate intensity");
                serviceOptionDao.insert(service1);

                ServiceOption service2 = new ServiceOption("30 minutes", "Home", "Playing", 2, "Ball");
                serviceOptionDao.insert(service2);


            });
        }
    };


}
