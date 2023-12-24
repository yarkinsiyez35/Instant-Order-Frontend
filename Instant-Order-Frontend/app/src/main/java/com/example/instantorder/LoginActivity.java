package com.example.instantorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instantorder.Model.EmployeeLogin;


//this Activity will be the first page in this application
//It will send the employeeId and password to the backend
//If it gets the same JSON file back, it will lead to the HomeActivity
//Else it will pop a message and stay at the LoginActivity

public class LoginActivity extends AppCompatActivity {

    private EditText employeeInput;
    private EditText passwordInput;
    private Button buttonJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        employeeInput = findViewById(R.id.employeeInput);
        passwordInput = findViewById(R.id.passwordInput);
        buttonJoin = findViewById(R.id.buttonJoin);

        // Set click listener for the "Join" button
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here
                String employeeId = employeeInput.getText().toString();

                String password = passwordInput.getText().toString();
                EmployeeLogin employeeLogin = new EmployeeLogin(Integer.parseInt(employeeId), password);    //created EmployeeLogin object to send
                //SEND POST REQUEST

                //RECEIVE RESPONSE

                boolean enter = true;

                if(enter)           //in the future, it will be connected to backend and get the result
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("EMPLOYEE_ID",employeeId);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();               //there is no reason to get back to login page once user logs in
                }
                else        //invalid password or ID
                {
                    employeeInput.setText("");          //resets the input
                    passwordInput.setText("");          //resets the input
                    String invalidMessage = "Employee ID or password is incorrect!\n";
                    Toast.makeText(LoginActivity.this,invalidMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}