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
import android.widget.TextView;
import android.widget.Toast;

import com.example.instantorder.Adapter.FoodAdapter;
import com.example.instantorder.ExecService.ExecServiceApp;
import com.example.instantorder.Model.Category;
import com.example.instantorder.Model.Food;
import com.example.instantorder.Repository.CategoryRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foods;
    private Button buttonHome;
    private Button buttonBack;
    private String tableId;
    private String categoryName;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.arg1 == 1)
            {
                Category categoryResponse = (Category)message.obj;
                List<Food> foodsResponse = categoryResponse.getFoods();
                foods.clear();
                foods.addAll(foodsResponse);
                foodAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.recyclerViewCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foods = new ArrayList<>();
        foodAdapter = new FoodAdapter(foods, this);
        recyclerView.setAdapter(foodAdapter);

        foodAdapter.setFoodListener(new FoodAdapter.FoodListener() {
            @Override
            public void FoodClicked(Food food) {
                Bundle bundleSend = new Bundle();
                bundleSend.putString("TABLE_ID", tableId);
                bundleSend.putString("CATEGORY_NAME", categoryName);
                bundleSend.putString("FOOD_NAME", food.getName());
                Intent intent = new Intent(CategoryActivity.this, PlaceOrderActivity.class);
                intent.putExtras(bundleSend);
                startActivity(intent);

            }
        });

        if(getIntent().getExtras() != null)
        {
            tableId = getIntent().getExtras().getString("TABLE_ID");
            categoryName = getIntent().getExtras().getString("CATEGORY_NAME");
        }

        TextView header = findViewById(R.id.textView22);
        header.setText(categoryName);


        buttonHome = findViewById(R.id.buttonCategoryHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        buttonBack = findViewById(R.id.buttonCategoryBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleSend = new Bundle();
                bundleSend.putString("TABLE_ID",tableId);
                Intent intent = new Intent(CategoryActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(bundleSend);
                startActivity(intent);
                finish();
            }
        });


        fetchCategory(tableId, categoryName);

    }


    public void fetchCategory(String tableId, String categoryName)
    {
        CategoryRepo categoryRepo = new CategoryRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        categoryRepo.getCategory(executorService,handler,tableId, categoryName);
    }
}