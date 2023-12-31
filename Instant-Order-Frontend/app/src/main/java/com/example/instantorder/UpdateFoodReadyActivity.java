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
import com.example.instantorder.Repository.FoodServeRepo;

import java.util.concurrent.ExecutorService;

public class UpdateFoodReadyActivity extends AppCompatActivity {



    Button buttonServed;
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
        setContentView(R.layout.activity_update_food_ready);


        //check the content of the bundle
        Bundle bundle = getIntent().getExtras();

        //set the name
        TextView foodName = findViewById(R.id.textView15);
        foodName.setText("Food:" + bundle.getString("FOOD_NAME"));

        TextView tableId =findViewById(R.id.textView16);
        tableId.setText("Table Id: " + bundle.getString("TABLE_ID"));

        TextView count = findViewById(R.id.textView17);
        count.setText("Count: " + bundle.getString("COUNT"));

        TextView note = findViewById(R.id.textView18);
        note.setText("Note: " + bundle.getString("NOTE"));

        //do the rest
        buttonServed = findViewById(R.id.buttonUFRready);
        buttonServed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putUpdateMessage(bundle.getString("OBJECT_ID"));
                Intent intent = new Intent(UpdateFoodReadyActivity.this, ReadyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        buttonBack = findViewById(R.id.buttonUFRBack);
        buttonBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateFoodReadyActivity.this, ReadyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        buttonHome = findViewById(R.id.buttonUFRHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateFoodReadyActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }


    private void putUpdateMessage(String objectId)
    {
        FoodServeRepo foodServeRepo = new FoodServeRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        foodServeRepo.putUpdateMessage(executorService, handler, objectId);
    }
}