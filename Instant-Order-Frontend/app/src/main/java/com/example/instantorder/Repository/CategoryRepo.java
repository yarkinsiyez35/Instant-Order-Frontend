package com.example.instantorder.Repository;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.instantorder.Model.Category;
import com.example.instantorder.Model.FoodServe;
import com.example.instantorder.Model.NoteAndCount;
import com.example.instantorder.Model.Table;
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

public class CategoryRepo {

    public void getCategories(ExecutorService executorService, Handler handler, String tableId)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/tables/" + tableId + "/menu/categories";
                    Log.d("CATEGORY-REPO-ADDRESS", address);
                    URL url = new URL(address);
                    //open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    Message message = new Message();
                    int responseCode = connection.getResponseCode();
                    Log.d("CATEGORY-REPO-RESPONSE", Integer.toString(responseCode));

                    if(responseCode != HttpURLConnection.HTTP_OK)
                    {
                        message.arg1 = 0;   //flag
                        Log.d("CATEGORY-REPO-FLAG",Integer.toString(0));
                    }
                    else
                    {
                        message.arg1 = 1;
                        Log.d("CATEGORY-REPO-FLAG",Integer.toString(1));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder buffer = new StringBuilder();
                        //read the output
                        String line = "";
                        while((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }
                        String reply = buffer.toString();
                        Log.d("CATEGORY-REPO-RESPONSE", reply);
                        connection.disconnect();    //stop connection
                        //convert the reply
                        Gson gsonReply = new Gson();
                        Category[] categoryArr = gsonReply.fromJson(reply, Category[].class);
                        //convert to Arraylist
                        ArrayList<Category> categories = new ArrayList<>(Arrays.asList(categoryArr));
                        message.obj = categories;
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

    public void getCategory(ExecutorService executorService, Handler handler, String tableId, String categoryName)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/tables/" + tableId + "/menu/categories/" + categoryName;
                    Log.d("CATEGORY-REPO-ADDRESS", address);
                    URL url = new URL(address);
                    //open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    Message message = new Message();
                    int responseCode = connection.getResponseCode();
                    Log.d("CATEGORY-REPO-RESPONSE", Integer.toString(responseCode));

                    if(responseCode != HttpURLConnection.HTTP_OK)
                    {
                        message.arg1 = 0;   //flag
                        Log.d("CATEGORY-REPO-FLAG",Integer.toString(0));
                    }
                    else
                    {
                        message.arg1 = 1;
                        Log.d("CATEGORY-REPO-FLAG",Integer.toString(1));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder buffer = new StringBuilder();
                        //read the output
                        String line = "";
                        while((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }
                        String reply = buffer.toString();
                        Log.d("CATEGORY-REPO-RESPONSE", reply);
                        connection.disconnect();    //stop connection
                        //convert the reply
                        Gson gsonReply = new Gson();
                        Category category = gsonReply.fromJson(reply, Category.class);
                        //convert to Arraylist
                        message.obj = category;
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

    public void postOrder(ExecutorService executorService, Handler handler, String tableId, String categoryName, String foodName, NoteAndCount noteAndCount)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String currentIP = new IpAddress().getIp();
                    String address = "http://" + currentIP + ":8080/instantOrder/tables/" + tableId + "/menu/categories/" + categoryName + "/" + foodName;
                    Log.d("CATEGORY-REPO-ADDRESS", address);
                    URL url = new URL(address);
                    //open connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //there is input
                    connection.setDoInput(true);
                    //there is output
                    connection.setDoOutput(true);
                    //it is POST
                    connection.setRequestMethod("POST");
                    //set Content-Type
                    connection.addRequestProperty("Content-Type", "application/json");
                    //convert NoteAndCount to JSON
                    Gson gsonSend = new Gson();
                    String jsonSend = gsonSend.toJson(noteAndCount, NoteAndCount.class);
                    //send it
                    BufferedOutputStream writer = new BufferedOutputStream(connection.getOutputStream());
                    writer.write(jsonSend.getBytes(StandardCharsets.UTF_8));
                    writer.flush();

                    Message message = new Message();
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                    {
                        message.arg1 = 0;
                    }
                    else
                    {
                        message.arg1 = 1;
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
                        //convert reply to Table
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
}
