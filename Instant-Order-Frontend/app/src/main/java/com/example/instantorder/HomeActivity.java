package com.example.instantorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {


    private Button buttonTables;
    private Button buttonOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get the employee ID from the bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            String employeeId = bundle.getString("EMPLOYEE_ID");
            //send a GET request for finding the employee name and display it
            //get the Employee object directly and pass it to the bundle from this point and on

            // Display a welcome message with the employee ID
            TextView welcomeMessageTextView = findViewById(R.id.welcomeMessage);
            String welcomeMessage = "Welcome to the home page, user " + employeeId;
            welcomeMessageTextView.setText(welcomeMessage);
        }

        buttonTables = findViewById(R.id.buttonTables);
        buttonTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lead to Tables Activity
                String message = "Tables button clicked!\n";
                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        buttonOrders = findViewById(R.id.buttonOrders);
        buttonOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //lead to OrdersActivity
                Bundle bundle = new Bundle();
                bundle.putString("EMPLOYEE_ID",bundle.getString("EMPLOYEE_ID"));
                Intent intent = new Intent(HomeActivity.this, OrdersActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //String message = "Orders button clicked!\n";
                //Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
