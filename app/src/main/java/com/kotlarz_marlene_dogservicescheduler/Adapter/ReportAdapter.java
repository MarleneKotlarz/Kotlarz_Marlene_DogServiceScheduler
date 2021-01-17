package com.kotlarz_marlene_dogservicescheduler.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kotlarz_marlene_dogservicescheduler.Entity.AppointmentAndServiceOption;
import com.kotlarz_marlene_dogservicescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {


    private static final String TAG = "ServiceScheduler";

    private List<AppointmentAndServiceOption> apptServicelist = new ArrayList<>();


    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {

        try {
            if (apptServicelist != null && apptServicelist.size() > 0) {
                AppointmentAndServiceOption currentItem = apptServicelist.get(position);
                holder.report_apptId.setText(String.valueOf(currentItem.appointment.getAppointment_id()));
                holder.report_apptDate.setText(currentItem.appointment.getDate());
                holder.report_apptTime.setText(currentItem.appointment.getTime());
                holder.report_serviceType.setText(currentItem.serviceOption.getType());
            } else {
                return;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return apptServicelist.size();
    }


    public void setAppointmentServiceList(List<AppointmentAndServiceOption> list) {
        this.apptServicelist = list;
        // Tell adapter to redraw the layout.
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView report_apptId, report_apptDate, report_apptTime, report_serviceType;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            report_apptId = itemView.findViewById(R.id.report_apptId);
            report_apptDate = itemView.findViewById(R.id.report_apptDate);
            report_apptTime = itemView.findViewById(R.id.report_apptTime);
            report_serviceType = itemView.findViewById(R.id.report_serviceType);


        }


    }


}

