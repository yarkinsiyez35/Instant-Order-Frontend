package com.example.instantorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instantorder.Model.FoodTable;
import com.example.instantorder.Model.Table;
import com.example.instantorder.R;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder>{

    List<Table> tables;
    Context context;
    public interface TableListener
    {
        public void TableClicked(Table table);
    }
    TableListener tableListener;

    public TableAdapter(List<Table> tables, Context context) {
        this.tables = tables;
        this.context = context;
    }

    public void setTableListener(TableListener tableListener) {
        this.tableListener = tableListener;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        Table table = tables.get(position);
        holder.tableIdTextView.setText("Table Id: " + Integer.toString(table.getTableId()));
        if(table.getTotal() == 0)
        {
            holder.employeeIdTextView.setText("Available: " + "yes");
        }
        else
        {
            holder.employeeIdTextView.setText("Available: " + "no");
        }

        holder.ordersTextView.setText("Bill: " + Double.toString(table.getTotal()));
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableListener != null)
                {
                    tableListener.TableClicked(table);
                }
            }
        });
        /*
        List<FoodTable> foodTables = table.getFoodOrders();

        StringBuilder ordersText = new StringBuilder();
        for (FoodTable foodTable : foodTables)
        {
            ordersText.append(foodTable.getFood().getName() + ", " + Integer.toString(foodTable.getCount()) + "\n");
        }

        holder.ordersTextView.append(ordersText.toString());
        */

    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder
    {
        TextView tableIdTextView;
        TextView employeeIdTextView;
        TextView ordersTextView;
        ConstraintLayout row;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            tableIdTextView = itemView.findViewById(R.id.tableIdTextView3);
            employeeIdTextView = itemView.findViewById(R.id.noteTextView3);
            ordersTextView = itemView.findViewById(R.id.foodNameTextView3);
            row = itemView.findViewById(R.id.tableContainer);
        }
    }


}
