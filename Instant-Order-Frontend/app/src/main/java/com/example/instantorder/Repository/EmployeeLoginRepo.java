package com.example.instantorder.Repository;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.instantorder.Model.EmployeeLogin;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

public class EmployeeLoginRepo {

    //returns EmployeeLogin object if successful
    public void postEmployeeLogin(ExecutorService executorService, Handler handler, EmployeeLogin employeeLogin)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    //address
                    //depends on local machine
                    //ifconfig | grep "inet " on terminal to find
                    String currentIP = "10.51.98.34";
                    String address = "http://" + currentIP + ":8080/instantOrder/login";
                    Log.d("EMPLOYEELOGIN-REPO-ADDRESS", address);
                    URL url = new URL(address);
                    //create connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //there is input
                    connection.setDoInput(true);
                    //there is output
                    connection.setDoOutput(true);
                    //it is POST
                    connection.setRequestMethod("POST");
                    //set Content-Type
                    connection.addRequestProperty("Content-Type", "application/json");
                    //convert EmployeeLogin to JSON
                    Gson gsonSend = new Gson();
                    String jsonSend = gsonSend.toJson(employeeLogin);
                    //send it
                    BufferedOutputStream writer = new BufferedOutputStream(connection.getOutputStream());
                    writer.write(jsonSend.getBytes(StandardCharsets.UTF_8));
                    writer.flush();

                    //check if reply is true
                    Message message = new Message();
                    if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                    {
                        message.arg1 = 0;   //flag
                    }
                    else
                    {
                        //get reply
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        StringBuilder buffer = new StringBuilder();

                        String line = "";
                        while((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }
                        String reply = buffer.toString();
                        //disconnect
                        connection.disconnect();
                        Log.e("LOGINREPO", "DISCONNECTION");
                        message.arg1 = 1;
                        //convert reply to EmployeeLogin
                        Gson gsonReply = new Gson();
                        EmployeeLogin employeeLoginReply = gsonReply.fromJson(reply, EmployeeLogin.class);
                        //add EmployeeLogin to message
                        message.obj = employeeLoginReply;
                    }
                    handler.sendMessage(message);
                }
                catch (MalformedURLException e)
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