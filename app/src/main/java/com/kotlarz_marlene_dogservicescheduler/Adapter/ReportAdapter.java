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

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private List<Appointment> appointmentList = new ArrayList<>();


    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
        if(appointmentList != null && appointmentList.size() > 0) {
            Appointment appointment = appointmentList.get(position);
            holder.report_apptId.setText(String.valueOf(appointment.getAppointment_id()));
            holder.report_apptDate.setText(appointment.getDate());
            holder.report_apptTime.setText(appointment.getTime());

        } else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }


    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView report_apptId, report_apptDate, report_apptTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            report_apptId = itemView.findViewById(R.id.report_apptId);
            report_apptDate = itemView.findViewById(R.id.report_apptDate);
            report_apptTime = itemView.findViewById(R.id.report_apptTime);


        }
    }
}
