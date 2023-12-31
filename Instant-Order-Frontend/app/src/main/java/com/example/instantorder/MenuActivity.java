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

import com.example.instantorder.Adapter.CategoryAdapter;
import com.example.instantorder.ExecService.ExecServiceApp;
import com.example.instantorder.Model.Category;
import com.example.instantorder.Repository.CategoryRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Category> categories;
    private CategoryAdapter categoryAdapter;
    private Button buttonHome;
    private Button buttonBack;
    private String tableId;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.arg1 == 1)
            {
                List<Category> categoriesResponse = (ArrayList<Category>)message.obj;
                categories.clear();
                categories.addAll(categoriesResponse);
                categoryAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_menu);

        //set recyclerView
        recyclerView = findViewById(R.id.recyclerViewMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categories,this);
        recyclerView.setAdapter(categoryAdapter);

        categoryAdapter.setCategoryListener(new CategoryAdapter.CategoryListener() {
            @Override
            public void CategoryClicked(Category category) {
                //open FoodActivity
                Bundle bundleSend = new Bundle();
                bundleSend.putString("TABLE_ID",tableId);
                bundleSend.putString("CATEGORY_NAME", category.getName());
                Intent intent = new Intent(MenuActivity.this, CategoryActivity.class);
                intent.putExtras(bundleSend);
                startActivity(intent);
            }
        });

        if(getIntent().getExtras() != null)
        {
            tableId = getIntent().getExtras().getString("TABLE_ID");
        }

        buttonHome = findViewById(R.id.buttonMenuHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        buttonBack = findViewById(R.id.buttonMenuBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleSend = new Bundle();
                bundleSend.putString("TABLE_ID",tableId);
                Intent intent = new Intent(MenuActivity.this, TableActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(bundleSend);
                startActivity(intent);
                finish();
            }
        });

        fetchCategories();

    }


    private void fetchCategories()
    {
        CategoryRepo categoryRepo = new CategoryRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        categoryRepo.getCategories(executorService, handler, tableId);
    }
}