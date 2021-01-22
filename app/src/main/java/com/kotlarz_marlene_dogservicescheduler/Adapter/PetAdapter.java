package com.kotlarz_marlene_dogservicescheduler.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kotlarz_marlene_dogservicescheduler.Entity.Pet;
import com.kotlarz_marlene_dogservicescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetHolder> {

    private List<Pet> pets = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public PetAdapter.PetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_pet, parent, false);
        return new PetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.PetHolder holder, int position) {
        Pet currentPet = pets.get(position);
        holder.textView_name.setText(currentPet.getPet_name());
        holder.textView_breed.setText(currentPet.getPet_breed());
        holder.textView_age.setText(String.valueOf(currentPet.getPet_age()));
        holder.textView_id.setText(String.valueOf(currentPet.getPet_id()));
    }

    @Override
    public int getItemCount() { return pets.size(); }


    public void setPets(List<Pet> pets) {
        this.pets = pets;
        notifyDataSetChanged();
    }


    public Pet getPetAt(int position) {
        return pets.get(position);
    }


    // Create ViewHolder class
    public class PetHolder extends RecyclerView.ViewHolder {
        private TextView textView_name;
        private TextView textView_breed;
        private TextView textView_age;
        private TextView textView_id;

        // Constructor
        public PetHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_pet_name);
            textView_breed = itemView.findViewById(R.id.textView_pet_breed);
            textView_age = itemView.findViewById(R.id.textView_pet_age);
            textView_id = itemView.findViewById(R.id.textView_pet_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pets.get(position));
                    }
                }
            });
        }
    }

    // onClick event interface
    public interface OnItemClickListener {
        void onItemClick(Pet pet);
    }

    // Reference onItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {this.listener = listener;}

}
