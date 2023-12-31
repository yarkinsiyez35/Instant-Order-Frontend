package com.example.instantorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instantorder.ExecService.ExecServiceApp;
import com.example.instantorder.Model.NoteAndCount;
import com.example.instantorder.Repository.CategoryRepo;

import java.util.concurrent.ExecutorService;

public class PlaceOrderActivity extends AppCompatActivity {

    private Button buttonSend;
    private Button buttonBack;
    private Button buttonHome;
    private String tableId;
    private String foodName;
    private String categoryName;
    private EditText inputCount;
    private EditText inputNote;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.arg1 == 1)
            {
                Toast.makeText(PlaceOrderActivity.this, "ORDER CONFIRMED!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        inputCount = findViewById(R.id.inputPOCount);
        inputNote = findViewById(R.id.inputPONote);

        if(getIntent().getExtras() != null)
        {
            tableId = getIntent().getExtras().getString("TABLE_ID");
            foodName = getIntent().getExtras().getString("FOOD_NAME");
            categoryName = getIntent().getExtras().getString("CATEGORY_NAME");
        }


        TextView tableTextView = findViewById(R.id.textView24);
        tableTextView.setText("TABLE #" + tableId);
        tableTextView.setTextColor(Color.WHITE);

        TextView foodTextView = findViewById(R.id.textView25);
        foodTextView.setText("FOOD: " + foodName);
        foodTextView.setTextColor(Color.WHITE);

        buttonSend = findViewById(R.id.buttonPOSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputCountValue = inputCount.getText().toString();
                if (!inputCountValue.equals(""))
                {
                    int inputCountInt = Integer.parseInt(inputCountValue);
                    if(inputCountInt == 0)
                    {
                        Toast.makeText(PlaceOrderActivity.this, "Count cannot be 0!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        NoteAndCount noteAndCount = new NoteAndCount();
                        noteAndCount.setCount(inputCountInt);
                        noteAndCount.setNote(inputNote.getText().toString());
                        noteAndCount.setEmployeeId(1);
                        postOrder(tableId,categoryName,foodName,noteAndCount);
                    }
                }
                else
                {
                    Toast.makeText(PlaceOrderActivity.this, "Count cannot be null!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBack = findViewById(R.id.buttonPOBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleSend = new Bundle();
                bundleSend.putString("TABLE_ID",tableId);
                bundleSend.putString("FOOD_NAME",foodName);
                bundleSend.putString("CATEGORY_NAME",categoryName);
                Intent intent = new Intent(PlaceOrderActivity.this, CategoryActivity.class);
                intent.putExtras(bundleSend);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        buttonHome = findViewById(R.id.buttonPOHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaceOrderActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

    }



    private void postOrder(String tableId, String categoryName, String foodName, NoteAndCount noteAndCount)
    {
        CategoryRepo categoryRepo = new CategoryRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        categoryRepo.postOrder(executorService, handler, tableId, categoryName, foodName, noteAndCount);
    }
}