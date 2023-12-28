package com.example.instantorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.instantorder.ExecService.ExecServiceApp;
import com.example.instantorder.Model.Employee;
import com.example.instantorder.Repository.EmployeeRepo;

import java.util.concurrent.ExecutorService;

public class HomeActivity extends AppCompatActivity {


    private Button buttonTables;
    private Button buttonOrders;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.arg1 == 1)
            {   //employee exists
                Employee employee = (Employee) message.obj;
                TextView welcomeMessageTextView = findViewById(R.id.welcomeMessage);
                String welcomeMessage = "Welcome to the home page " + employee.getFirstName();
                welcomeMessageTextView.setText(welcomeMessage);
            }
            else
            {   //employee does not exist
                TextView welcomeMessageTextView = findViewById(R.id.welcomeMessage);
                String welcomeMessage = "Welcome to the home page!";
                welcomeMessageTextView.setText(welcomeMessage);
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get the employee ID from the bundle
        Bundle bundle = getIntent().getExtras();
        String employeeId = bundle.getString("EMPLOYEE_ID");

        //send the get request
        EmployeeRepo employeeRepo = new EmployeeRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        employeeRepo.getEmployeeById(executorService, handler, employeeId);

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
