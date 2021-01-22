package com.kotlarz_marlene_dogservicescheduler.Adapter;

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

    private List<Appointment> appointments = new ArrayList<>();
    private OnItemClickListener listener;

    public AppointmentAdapter() {
    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_appointment, viewGroup, false);
        return new AppointmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {
        Appointment currentAppointment = appointments.get(position);
        holder.textView_Id.setText(String.valueOf(currentAppointment.getAppointment_id()));
        holder.textView_date.setText(currentAppointment.getDate());
        holder.textView_time.setText(currentAppointment.getTime());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }


    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    public Appointment getAppointmentAt(int position) {
        return appointments.get(position);
    }


    // Create ViewHolder class
    public class AppointmentHolder extends RecyclerView.ViewHolder {
        private TextView textView_Id;
        private TextView textView_date;
        private TextView textView_time;

        // Constructor
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
        void onItemClick(Appointment appointment);
    }

    //Reference onItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}