package com.example.instantorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instantorder.Model.Food;
import com.example.instantorder.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    List<Food> foods;
    private Context context;


    public interface FoodListener
    {
        public void FoodClicked(Food food);
    }

    FoodListener foodListener;

    public FoodAdapter(List<Food> foods, Context context) {
        this.foods = foods;
        this.context = context;
    }

    public void setFoodListener(FoodListener foodListener) {
        this.foodListener = foodListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foods.get(position);

        holder.nameTextView.setText("Name: " + food.getName());
        holder.priceTextView.setText("Price: " + Double.toString(food.getPrice()));
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foodListener != null)
                {
                    foodListener.FoodClicked(food);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }



    public static class FoodViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameTextView;
        TextView priceTextView;
        ConstraintLayout row;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.foodTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            row = itemView.findViewById(R.id.foodContainer);
        }
    }
}
