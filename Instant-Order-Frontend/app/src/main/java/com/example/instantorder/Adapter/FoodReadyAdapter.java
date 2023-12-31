package com.example.instantorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instantorder.Model.FoodServe;
import com.example.instantorder.R;

import java.util.List;

public class FoodReadyAdapter extends RecyclerView.Adapter<FoodReadyAdapter.FoodReadyViewHolder>{

    List<FoodServe> foodServes;
    private Context context;



    public interface FoodReadyListener
    {
        void FoodReadyClicked(FoodServe foodServe);
    }

    FoodReadyListener foodReadyListener;

    public FoodReadyAdapter(List<FoodServe> foodServes, Context context) {
        this.foodServes = foodServes;
        this.context = context;
    }

    public void setFoodReadyListener(FoodReadyListener foodReadyListener) {
        this.foodReadyListener = foodReadyListener;
    }

    @NonNull
    @Override
    public FoodReadyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_ready, parent, false);
        return new FoodReadyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodReadyViewHolder holder, int position)
    {
        FoodServe foodServe = foodServes.get(position);

        holder.foodNameTextView.setText(foodServe.getFoodName());
        holder.tableIdTextView.setText("TableId: " + foodServe.getTableId());
        holder.countTextView.setText("Count: " + foodServe.getCount());
        holder.noteTextView.setText("Note: " + foodServe.getNote());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foodReadyListener != null)
                {
                    foodReadyListener.FoodReadyClicked(foodServe);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodServes.size();
    }

    public static class FoodReadyViewHolder extends RecyclerView.ViewHolder
    {
        TextView foodNameTextView;
        TextView tableIdTextView;
        TextView countTextView;
        TextView noteTextView;
        ConstraintLayout row;

        public FoodReadyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.foodNameTextView2);
            tableIdTextView = itemView.findViewById(R.id.tableIdTextView2);
            countTextView = itemView.findViewById(R.id.countTextView2);
            noteTextView = itemView.findViewById(R.id.noteTextView2);
            row = itemView.findViewById(R.id.foodReadyContainer);
        }
    }
}
