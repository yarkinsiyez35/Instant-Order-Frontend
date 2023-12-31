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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.instantorder.ExecService.ExecServiceApp;
import com.example.instantorder.Model.FoodTable;
import com.example.instantorder.Model.Table;
import com.example.instantorder.Repository.TableRepo;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class TableActivity extends AppCompatActivity {

    private Button buttonHome;
    private Button buttonBack;
    private Button buttonPlaceOrder;
    private Button buttonPaid;
    private LinearLayout linearLayout;

    private String tableId;
    private String employeeId;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.arg1 == 1)
            {
                Table tableResponse = (Table)message.obj;
                List<FoodTable> foodTables = tableResponse.getFoodOrders();
                linearLayout.removeAllViews();
                if(foodTables.isEmpty())
                {
                    String str = "There are currently no orders!";
                    linearLayout.removeAllViews();
                    TextView textView = new TextView(linearLayout.getContext());
                    textView.setText(str);
                    textView.setTextColor(Color.BLACK);
                    linearLayout.addView(textView);
                }
                else
                {
                    for (FoodTable foodTable : foodTables)
                    {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(foodTable.getFood().getName() + " x " + foodTable.getCount());
                        TextView textView = new TextView(linearLayout.getContext());
                        textView.setText(stringBuilder.toString());
                        textView.setTextColor(Color.WHITE);
                        linearLayout.addView(textView);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Total: " + Double.toString(tableResponse.getTotal()));
                    TextView textView = new TextView(linearLayout.getContext());
                    textView.setText(stringBuilder.toString());
                    textView.setTextColor(Color.WHITE);
                    linearLayout.addView(textView);
                }


            }
            else
            {
                String str = "There are currently no orders!";
                linearLayout.removeAllViews();
                TextView textView = new TextView(linearLayout.getContext());
                textView.setText(str);
                textView.setTextColor(Color.BLACK);
                linearLayout.addView(textView);
            }
            return true;
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Bundle sentBundle = getIntent().getExtras();
        if(sentBundle != null)
        {
            tableId = sentBundle.getString("TABLE_ID");
            employeeId = sentBundle.getString("EMPLOYEE_ID");
        }

        TextView header = findViewById(R.id.textView20);
        String headerStr = "TABLE #" + tableId;
        header.setText(headerStr);

        linearLayout = findViewById(R.id.linearLayoutTable);

        buttonHome = findViewById(R.id.buttonTableHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TableActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        buttonPaid = findViewById(R.id.buttonTablePaid);
        buttonPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //should send put message
                putTable(tableId);
            }
        });

        buttonBack = findViewById(R.id.buttonTableBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TableActivity.this, TablesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        buttonPlaceOrder = findViewById(R.id.buttonTableOrder);
        buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //should open a new activity, send tableId and employeeId in a bundle
            }
        });

        //get the information about the table
        fetchTable(tableId);

    }


    private void fetchTable(String tableId)
    {
        TableRepo tableRepo = new TableRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        tableRepo.getTable(executorService,handler, tableId);
    }
    private void putTable(String tableId)
    {
        TableRepo tableRepo = new TableRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        tableRepo.putUpdateMessage(executorService,handler, tableId);
    }
}