package com.example.instantorder.Repository;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.instantorder.Model.Employee;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;

public class EmployeeRepo {

    public void getEmployeeById(ExecutorService executorService, Handler handler, String employeeId)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    //connection address
                    //ifconfig | grep "inet " on terminal to find currentIP
                    String currentIP = "10.51.98.34";
                    String address = "http://" + currentIP + ":8080/instantOrder/employees/" +employeeId;
                    Log.e("EMPLOYEE-REPO-ADDRESS", address);    //address is working on postman
                    URL url = new URL(address);
                    //open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //it is GET
                    Message message = new Message();
                    int responseCode = connection.getResponseCode();
                    Log.d("EMPLOYEE-REPO-RESPONSE", Integer.toString(responseCode));
                    if( responseCode != 200)
                    {
                        Log.d("EMPLOYEE-REPO-FLAG",Integer.toString(0));
                        message.arg1 = 0;   //flag
                    }
                    else
                    {
                        message.arg1 = 1;
                        Log.d("EMPLOYEE-REPO-FLAG",Integer.toString(1));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        StringBuilder buffer = new StringBuilder();
                        //read the output
                        String line = "";
                        while((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }
                        String reply = buffer.toString();
                        Log.d("EMPLOYEE-REPO-RESPONSE", reply);

                        //disconnect
                        connection.disconnect();
                        Gson gsonReply = new Gson();
                        Employee employee = gsonReply.fromJson(reply, Employee.class);
                        message.obj = employee;
                    }
                    handler.sendMessage(message);
                } catch (MalformedURLException e)
                {
                    throw new RuntimeException(e);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
