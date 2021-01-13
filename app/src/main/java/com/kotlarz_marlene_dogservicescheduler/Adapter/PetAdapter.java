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

    // Set to new ArrayList, otherwise it would be set to null to avoid null-checks
    private List<Pet> pets = new ArrayList<>();
    private OnItemClickListener listener;

    // Create and return PetHolder, this is the layout you want to use for the single items in RecyclerView.
    @NonNull
    @Override
    public PetAdapter.PetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_pet, parent, false);
        return new PetHolder(itemView);
    }

    // Takes care of getting the data from the single PetList java objects into the
    // views of the PetHolder. Get item and pass it into the textView
    @Override
    public void onBindViewHolder(@NonNull PetAdapter.PetHolder holder, int position) {
        Pet currentPet = pets.get(position);
        holder.textView_name.setText(currentPet.getPet_name());
        holder.textView_breed.setText(currentPet.getPet_breed());
        holder.textView_age.setText(String.valueOf(currentPet.getPet_age()));
    }

    // Returns how many items you want to display in the RecyclerView
    @Override
    public int getItemCount() { return pets.size(); }

    // PetListActivity observes liveData, onChanged methods passes List of terms.
    // We need to get this list into the RecyclerView by calling this method
    public void setPets(List<Pet> pets) {
        this.pets = pets;
        // Tell adapter to redraw the layout.
        notifyDataSetChanged();
    }

    // Return Pet at this position - created for swipe delete method in PetListActivity
    public Pet getPetAt(int position) {
        return pets.get(position);
    }

    // Create ViewHolder class - Will hold the views in our single RecyclerView items
    public class PetHolder extends RecyclerView.ViewHolder {
        private TextView textView_name;
        private TextView textView_breed;
        private TextView textView_age;

        // Constructor
        public PetHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_pet_name);
            textView_breed = itemView.findViewById(R.id.textView_pet_breed);
            textView_age = itemView.findViewById(R.id.textView_pet_age);

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
    // Choose onItemClickListener with my package name
    public void setOnItemClickListener(OnItemClickListener listener) {this.listener = listener;}

}
