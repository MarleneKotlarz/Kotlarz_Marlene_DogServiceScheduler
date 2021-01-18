package com.kotlarz_marlene_dogservicescheduler.Adapter;

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

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private List<AppointmentAndServiceOption> apptServicelist = new ArrayList<>();


    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
//
//        try {
//            if (apptServicelist != null && apptServicelist.size() > 0) {

        AppointmentAndServiceOption currentItem = apptServicelist.get(position);


        holder.tv_report_apptDate.setText(currentItem.appointment.getDate());
        holder.tv_report_apptTime.setText(currentItem.appointment.getTime());
        holder.tv_report_serviceType.setText(currentItem.serviceOption.getType());
        holder.tv_report_serviceDuration.setText(currentItem.serviceOption.getDuration());
        holder.tv_report_apptId.setText(String.valueOf(currentItem.appointment.getAppointment_id()));
        holder.tv_report_serviceId.setText(String.valueOf(currentItem.serviceOption.getService_id()));
        holder.tv_report_customerId.setText(String.valueOf(currentItem.appointment.getCustomer_id_fk()));
        holder.tv_report_petId.setText(String.valueOf(currentItem.appointment.getPet_id_fk()));

//            } else {
//                return;
//            }
//        } catch (NullPointerException e) {
//            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage());
//        }

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
        TextView tv_report_apptDate, tv_report_apptTime, tv_report_serviceType, tv_report_serviceDuration;
        TextView tv_report_apptId, tv_report_serviceId, tv_report_customerId, tv_report_petId;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tv_report_apptDate = itemView.findViewById(R.id.report_apptDate);
            tv_report_apptTime = itemView.findViewById(R.id.report_apptTime);
            tv_report_serviceType = itemView.findViewById(R.id.report_serviceType);
            tv_report_serviceDuration = itemView.findViewById(R.id.report_serviceDuration);
            tv_report_apptId = itemView.findViewById(R.id.report_apptId);
            tv_report_serviceId = itemView.findViewById(R.id.report_serviceId);
            tv_report_customerId = itemView.findViewById(R.id.report_customerId);
            tv_report_petId = itemView.findViewById(R.id.report_petId);


        }


    }


}

