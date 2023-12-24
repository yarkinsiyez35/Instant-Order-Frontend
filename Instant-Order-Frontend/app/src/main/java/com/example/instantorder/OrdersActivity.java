package com.example.instantorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OrdersActivity extends AppCompatActivity {


    private Button buttonChef;
    private Button buttonWaiter;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        buttonChef = findViewById(R.id.buttonChef);

        buttonChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to ChefActivity
                String message = "Kitchen button clicked!";
                Toast.makeText(OrdersActivity.this, message, Toast.LENGTH_SHORT ).show();
            }
        });

        buttonWaiter = findViewById(R.id.buttonWaiter);
        buttonWaiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to WaiterActivity
                String message = "Ready button clicked!";
                Toast.makeText(OrdersActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        buttonBack = findViewById(R.id.buttonOrdersBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete this activity and go back to home page
                Intent intent = new Intent(OrdersActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //String message = "Back button clicked!";
                //Toast.makeText(OrdersActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}