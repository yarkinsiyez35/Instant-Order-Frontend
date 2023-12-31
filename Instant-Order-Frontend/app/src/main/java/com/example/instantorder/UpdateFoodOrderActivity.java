package com.example.instantorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.instantorder.ExecService.ExecServiceApp;
import com.example.instantorder.Model.FoodOrder;
import com.example.instantorder.Repository.FoodOrderRepo;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutorService;

public class UpdateFoodOrderActivity extends AppCompatActivity {


    Button buttonUpdate;
    Button buttonHome;
    Button buttonBack;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food_order);

        //check the content of the bundle
        Bundle bundle = getIntent().getExtras();

        //set the name
        TextView foodName = findViewById(R.id.textView10);
        foodName.setText("Food:" + bundle.getString("FOOD_NAME"));

        TextView tableId =findViewById(R.id.textView11);
        tableId.setText("Table Id: " + bundle.getString("TABLE_ID"));

        TextView count = findViewById(R.id.textView12);
        count.setText("Count: " + bundle.getString("COUNT"));

        TextView note = findViewById(R.id.textView13);
        note.setText("Note: " + bundle.getString("NOTE"));

        //when buttonUpdate is clicked, send PutRequest
        buttonUpdate = findViewById(R.id.buttonUFOReady);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send put request
                putUpdateMessage(bundle.getString("OBJECT_ID"));
                Intent intent = new Intent(UpdateFoodOrderActivity.this, KitchenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //added new
                startActivity(intent);
                finish();
            }
        });
        //when buttonHome is clicked, go to home
        buttonHome = findViewById(R.id.buttonUFOHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go back to home page
                Intent intent = new Intent(UpdateFoodOrderActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        buttonBack = findViewById(R.id.buttonUFOBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateFoodOrderActivity.this, KitchenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    private void putUpdateMessage(String objectId)
    {
        FoodOrderRepo foodOrderRepo = new FoodOrderRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        foodOrderRepo.putFoodOrder(executorService,handler, objectId);
    }
}