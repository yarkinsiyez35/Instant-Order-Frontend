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

import com.example.instantorder.Adapter.FoodReadyAdapter;
import com.example.instantorder.ExecService.ExecServiceApp;
import com.example.instantorder.Model.FoodServe;
import com.example.instantorder.Repository.FoodServeRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ReadyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FoodServe> foodServes;
    private FoodReadyAdapter foodReadyAdapter;
    private Button buttonRefresh;
    private Button buttonHome;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.arg1 == 1)
            {
                ArrayList<FoodServe> foodServeResponse = (ArrayList<FoodServe>)message.obj;
                foodServes.clear();
                foodServes.addAll(foodServeResponse);
                foodReadyAdapter.notifyDataSetChanged();
            }
            else
            {

            }

            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);

        //set recylerView
        recyclerView = findViewById(R.id.recyclerViewFoodServe);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodServes = new ArrayList<>();
        foodReadyAdapter = new FoodReadyAdapter(foodServes, this);
        recyclerView.setAdapter(foodReadyAdapter);


        fetchFoodServes();
        foodReadyAdapter.setFoodReadyListener(new FoodReadyAdapter.FoodReadyListener() {
            @Override
            public void FoodReadyClicked(FoodServe foodServe)
            {
                //starts UpdateFoodReadyActivity with clicked item
                Bundle bundle = new Bundle();
                bundle.putString("FOOD_NAME", foodServe.getFoodName());
                bundle.putString("TABLE_ID", Integer.toString(foodServe.getTableId()));
                bundle.putString("COUNT", Integer.toString(foodServe.getCount()));
                bundle.putString("NOTE", foodServe.getNote());
                bundle.putString("OBJECT_ID", foodServe.getObjectId());
                Intent intent = new Intent(ReadyActivity.this, UpdateFoodReadyActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        buttonRefresh = findViewById(R.id.buttonReadyRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchFoodServes();
            }
        });

        buttonHome = findViewById(R.id.buttonReadyHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ReadyActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }



    public void fetchFoodServes()
    {
        FoodServeRepo foodServeRepo = new FoodServeRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        foodServeRepo.getFoodServes(executorService, handler);
    }
}