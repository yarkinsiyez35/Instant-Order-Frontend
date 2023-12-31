package com.example.instantorder.Repository;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.instantorder.Model.FoodServe;
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


public class FoodServeRepo {

    public void getFoodServes(ExecutorService executorService, Handler handler)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/serve";
                    Log.d("FOODSERVE-REPO-ADDRESS", address);
                    URL url = new URL(address);
                    //open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    Message message = new Message();
                    int responseCode = connection.getResponseCode();
                    Log.d("FOODSERVE-REPO-RESPONSE", Integer.toString(responseCode));
                    if(responseCode != HttpURLConnection.HTTP_OK)
                    {
                        message.arg1 = 0;   //flag
                        Log.d("FOODSERVE-REPO-FLAG",Integer.toString(0));
                    }
                    else
                    {
                        message.arg1 = 1;
                        Log.d("FOODSERVE-REPO-FLAG",Integer.toString(1));
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
                        FoodServe[] foodServeArr = gsonReply.fromJson(reply, FoodServe[].class);
                        //convert to Arraylist
                        ArrayList<FoodServe> foodServes = new ArrayList<>(Arrays.asList(foodServeArr));
                        message.obj = foodServes;
                    }
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



    public void putUpdateMessage(ExecutorService executorService, Handler handler, String objectId)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/serve/" + objectId;
                    Log.d("FOODSERVE-REPO-PUT-ADDRESS", address);
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
                        FoodServe foodServe;
                        Gson gsonReply = new Gson();
                        foodServe = gsonReply.fromJson(reply, FoodServe.class);
                        message.obj = foodServe;
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
