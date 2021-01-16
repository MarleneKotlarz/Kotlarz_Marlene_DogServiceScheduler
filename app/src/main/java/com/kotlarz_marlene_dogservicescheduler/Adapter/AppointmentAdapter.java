package com.kotlarz_marlene_dogservicescheduler.Adapter;

// After you have AppointmentViewModel added to AppointmentListActivity and the xml files ready for the
// recyclerView continue with Adapter.
// Adapter is used to get the data from the appointment objects into the recyclerView items.


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kotlarz_marlene_dogservicescheduler.Entity.Appointment;
import com.kotlarz_marlene_dogservicescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    
    // Set to new ArrayList, otherwise it would be set to null to avoid null-checks
    private List<Appointment> appointments = new ArrayList<>();
    private OnItemClickListener listener; // First create the methods at the end of this code, then choose the one with my package name.

    public AppointmentAdapter() {
    }

    // Create and return AppointmentHolder, this is the layout you want to use for the single items in RecyclerView.
    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_appointment, viewGroup, false);

        return new AppointmentHolder(itemView);
    }

    // TODO remove appointmentId from List

    // Takes care of getting the data from the single AppointmentList java objects into the
    // views of the AppointmentHolder. Get item and pass it into the textView
    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {
        Appointment currentAppointment = appointments.get(position);
        holder.textView_Id.setText(String.valueOf(currentAppointment.getAppointment_id()));
        holder.textView_date.setText(currentAppointment.getDate());
        holder.textView_time.setText(currentAppointment.getTime());
    }

    // Returns how many items you want to display in the RecyclerView
    @Override
    public int getItemCount() {
        return appointments.size();
    }

    // AppointmentListActivity observes liveData, onChanged methods passes List of terms.
    // We need to get this list into the RecyclerView by calling this method
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        // Tell adapter to redraw the layout.
        notifyDataSetChanged();
    }


    // Return Appointment at this position - created for swipe delete method in AppointmentListActivity
    public Appointment getAppointmentAt(int position) {
        return appointments.get(position);
    }


    // Create ViewHolder class - Will hold the views in our single RecyclerView items
    public class AppointmentHolder extends RecyclerView.ViewHolder {
        private TextView textView_Id;
        private TextView textView_date;
        private TextView textView_time;

        // Constructor
        // Assign the textViews - itemView is the card itself from the cardView list item.
        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);
            textView_Id = itemView.findViewById(R.id.textView_appointment_id);
            textView_date = itemView.findViewById(R.id.textView_appointment_date);
            textView_time = itemView.findViewById(R.id.textView_appointment_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // Get the clicked position
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(appointments.get(position));
                    }

                }
            });
        }
    }


    // onClick event interface
    public interface OnItemClickListener {
        // pass in appointment object
        void onItemClick(Appointment appointment);
    }

    //Reference onItemClickListener
    //Choose onItemClickListener with my package name
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }







}