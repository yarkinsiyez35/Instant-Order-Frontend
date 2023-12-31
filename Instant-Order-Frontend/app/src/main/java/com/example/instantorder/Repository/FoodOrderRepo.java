package com.example.instantorder.Repository;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.instantorder.Model.FoodOrder;
import com.example.instantorder.Model.UpdateMessage;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class FoodOrderRepo {

    //sends a get Request
    public void getFoodOrders(ExecutorService executorService, Handler handler)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/orders";
                    Log.d("FOODORDER-REPO-ADDRESS", address);
                    URL url = new URL(address);
                    //open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    Message message = new Message();
                    int responseCode = connection.getResponseCode();
                    Log.d("FOODORDER-REPO-RESPONSE", Integer.toString(responseCode));
                    if(responseCode != HttpURLConnection.HTTP_OK)
                    {
                        message.arg1 = 0;   //flag
                        Log.d("FOODORDER-REPO-FLAG",Integer.toString(0));
                    }
                    else
                    {
                        message.arg1 = 1;
                        Log.d("FOODORDER-REPO-FLAG",Integer.toString(1));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        StringBuilder buffer = new StringBuilder();
                        //read the output
                        String line = "";
                        while((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }
                        String reply = buffer.toString();
                        Log.d("FOODORDER-REPO-RESPONSE", reply);
                        connection.disconnect();    //stop connection
                        //convert the reply
                        Gson gsonReply = new Gson();
                        FoodOrder[] foodOrderArr = gsonReply.fromJson(reply, FoodOrder[].class);
                        //convert to Arraylist
                        ArrayList<FoodOrder> foodOrders = new ArrayList<>(Arrays.asList(foodOrderArr));
                        message.obj = foodOrders;
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


    //put Request
    public void putFoodOrder(ExecutorService executorService, Handler handler, String objectId)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/orders/" + objectId;
                    Log.d("FOODORDER-REPO-PUT-ADDRESS", address);
                    URL url = new URL(address);
                    //open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //there is input
                    connection.setDoInput(true);
                    //there is output
                    connection.setDoOutput(true);
                    //it is PUT
                    connection.setRequestMethod("PUT");
                    //set Content-Type
                    connection.addRequestProperty("Content-Type", "application/json");
                    //convert UpdateMessage to json
                    Gson gsonSend = new Gson();
                    UpdateMessage updateMessage = new UpdateMessage(true);
                    String jsonSend = gsonSend.toJson(updateMessage, UpdateMessage.class);
                    //send it
                    BufferedOutputStream writer = new BufferedOutputStream(connection.getOutputStream());
                    writer.write(jsonSend.getBytes(StandardCharsets.UTF_8));
                    writer.flush();

                    Message message = new Message();
                    if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                    {
                        message.arg1 = 0;   //flag
                    }
                    else
                    {
                        message.arg1 = 1;
                        //read the reply
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
                        //convert json to FoodOrder
                        FoodOrder foodOrder;
                        Gson gsonReply = new Gson();
                        foodOrder = gsonReply.fromJson(reply, FoodOrder.class);
                        message.obj = foodOrder;
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
