package com.example.instantorder.Repository;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.instantorder.Model.FoodOrder;
import com.example.instantorder.Model.FoodServe;
import com.example.instantorder.Model.Table;
import com.example.instantorder.Model.UpdateMessage;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class TableRepo {

    //get request for all Tables
    public void getTables(ExecutorService executorService, Handler handler)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/tables";
                    Log.d("TABLE-REPO-ADDRESS", address);
                    URL url = new URL(address);
                    //open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    Message message = new Message();
                    int responseCode = connection.getResponseCode();
                    Log.d("TABLE-REPO-RESPONSE", Integer.toString(responseCode));
                    if(responseCode != HttpURLConnection.HTTP_OK)
                    {
                        message.arg1 = 0;   //flag
                        Log.d("TABLE-REPO-FLAG",Integer.toString(0));
                    }
                    else
                    {
                        message.arg1 = 1;
                        Log.d("TABLE-REPO-FLAG",Integer.toString(1));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        StringBuilder buffer = new StringBuilder();
                        //read the output
                        String line = "";
                        while((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }
                        String reply = buffer.toString();
                        Log.d("TABLE-REPO-RESPONSE", reply);
                        connection.disconnect();    //stop connection
                        //convert the reply
                        Gson gsonReply = new Gson();
                        Table[] tableArr = gsonReply.fromJson(reply, Table[].class);
                        //convert to Arraylist
                        ArrayList<Table> tables = new ArrayList<>(Arrays.asList(tableArr));
                        message.obj = tables;
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


    //get request for a table
    public void getTable(ExecutorService executorService, Handler handler, String tableId)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/tables/" + tableId;
                    Log.d("TABLE-REPO-ADDRESS", address);
                    URL url = new URL(address);
                    //open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                    Message message = new Message();
                    int responseCode = connection.getResponseCode();
                    Log.d("TABLE-REPO-RESPONSE", Integer.toString(responseCode));
                    if(responseCode != HttpURLConnection.HTTP_OK)
                    {
                        message.arg1 = 0;   //flag
                        Log.d("TABLE-REPO-FLAG",Integer.toString(0));
                    }
                    else
                    {
                        message.arg1 = 1;
                        Log.d("TABLE-REPO-FLAG",Integer.toString(1));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        StringBuilder buffer = new StringBuilder();
                        //read the output
                        String line = "";
                        while((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }
                        String reply = buffer.toString();
                        Log.d("TABLE-REPO-RESPONSE", reply);
                        connection.disconnect();    //stop connection
                        //convert the reply
                        Gson gsonReply = new Gson();
                        Table table = gsonReply.fromJson(reply, Table.class);
                        message.obj = table;
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

    //clears the table
    public void putUpdateMessage(ExecutorService executorService, Handler handler, String tableId)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/tables/" + tableId;
                    Log.d("TABLE-REPO-ADDRESS", address);
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
                        Table table;
                        Gson gsonReply = new Gson();
                        table = gsonReply.fromJson(reply, Table.class);
                        message.obj = table;
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
