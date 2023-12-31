package com.example.instantorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.instantorder.Adapter.FoodOrderAdapter;
import com.example.instantorder.ExecService.ExecServiceApp;
import com.example.instantorder.Model.FoodOrder;
import com.example.instantorder.Repository.FoodOrderRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class KitchenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FoodOrder> foodOrders;
    private FoodOrderAdapter foodOrderAdapter;

    private Button buttonRefresh;
    private Button buttonHome;



    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.arg1 == 1)
            {
                ArrayList<FoodOrder> foodOrderResponse = (ArrayList<FoodOrder>)message.obj;
                foodOrders.clear();
                foodOrders.addAll(foodOrderResponse);

                // Notify the adapter that the data set has changed
                foodOrderAdapter.notifyDataSetChanged();
            }
            else
            {   //it will show nothing?

            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        recyclerView = findViewById(R.id.recyclerViewFoodOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodOrders = new ArrayList<>();
        foodOrderAdapter = new FoodOrderAdapter(foodOrders, this);
        recyclerView.setAdapter(foodOrderAdapter);

        foodOrderAdapter.setFoodOrderListener(new FoodOrderAdapter.FoodOrderListener() {
            @Override
            public void FoodOrderClicked(FoodOrder foodOrder) {
                //go to a page with only that foodOrder
                Intent intent = new Intent(KitchenActivity.this, UpdateFoodOrderActivity.class);
                Bundle bundle = new Bundle();
                //add the necessary information to display
                bundle.putString("FOOD_NAME", foodOrder.getFood().getName());
                bundle.putString("TABLE_ID", Integer.toString(foodOrder.getTableId()));
                bundle.putString("COUNT", Integer.toString(foodOrder.getCount()));
                bundle.putString("NOTE", foodOrder.getNote());
                bundle.putString("OBJECT_ID", foodOrder.getObjectId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //send get request for FoodOrder
        fetchFoodOrders();



        buttonHome = findViewById(R.id.buttonKitchenHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KitchenActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });


        buttonRefresh = findViewById(R.id.buttonKitchenRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send get request again
                fetchFoodOrders();
            }
        });
    }

    //get request for List<FoodOrder>
    public void fetchFoodOrders()
    {
        FoodOrderRepo foodOrderRepo = new FoodOrderRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        foodOrderRepo.getFoodOrders(executorService,handler);
    }
}