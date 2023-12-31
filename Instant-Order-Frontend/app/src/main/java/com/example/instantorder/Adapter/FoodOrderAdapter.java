package com.example.instantorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instantorder.Model.FoodOrder;
import com.example.instantorder.R;

import java.util.List;

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.FoodOrderViewHolder>
{
    private List<FoodOrder> foodOrders;
    private Context context;

    public interface FoodOrderListener
    {
        public void FoodOrderClicked(FoodOrder foodOrder);
    }

    private FoodOrderListener foodOrderListener;

    public FoodOrderAdapter(List<FoodOrder> foodOrders1, Context context1)
    {
        this.foodOrders = foodOrders1;
        this.context = context1;
    }

    public void setFoodOrderListener(FoodOrderListener foodOrderListener) {
        this.foodOrderListener = foodOrderListener;
    }

    @NonNull
    @Override
    public FoodOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_order, parent, false);
        return new FoodOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOrderViewHolder holder, int position) {
        FoodOrder foodOrder = foodOrders.get(position);

        holder.foodNameTextView.setText(foodOrder.getFood().getName());
        holder.tableIdTextView.setText("TableId: " + foodOrder.getTableId());
        holder.countTextView.setText("Count: " + foodOrder.getCount());
        holder.noteTextView.setText("Note: " + foodOrder.getNote());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foodOrderListener != null)
                {
                    foodOrderListener.FoodOrderClicked(foodOrder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodOrders.size();
    }

    public static class FoodOrderViewHolder extends RecyclerView.ViewHolder
    {
        TextView foodNameTextView;
        TextView tableIdTextView;
        TextView countTextView;
        TextView noteTextView;

        ConstraintLayout row;


        public FoodOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
            tableIdTextView = itemView.findViewById(R.id.tableIdTextView);
            countTextView = itemView.findViewById(R.id.countTextView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            row = itemView.findViewById(R.id.foodOrderContainer);
        }
    }

}
