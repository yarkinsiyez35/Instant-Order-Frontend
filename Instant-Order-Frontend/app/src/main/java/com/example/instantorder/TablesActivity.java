package com.example.instantorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.instantorder.Adapter.TableAdapter;
import com.example.instantorder.ExecService.ExecServiceApp;
import com.example.instantorder.Model.Table;
import com.example.instantorder.Repository.TableRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class TablesActivity extends AppCompatActivity {

    private Button buttonHome;
    private Button buttonRefresh;
    private RecyclerView recyclerView;
    private List<Table> tables;
    private TableAdapter tableAdapter;
    private Handler handler = new Handler(new Handler.Callback()
    {

        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.arg1 == 1)
            {
                ArrayList<Table> tablesResponse = (ArrayList<Table>)message.obj;
                tables.clear();
                tables.addAll(tablesResponse);
                tableAdapter.notifyDataSetChanged();
            }
            else
            {

            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        //set the recylerView
        recyclerView = findViewById(R.id.recyclerViewTables);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tables = new ArrayList<>();
        tableAdapter = new TableAdapter(tables, this);
        recyclerView.setAdapter(tableAdapter);

        //get tables
        fetchTables();

        tableAdapter.setTableListener(new TableAdapter.TableListener() {
            @Override
            public void TableClicked(Table table) {
                //open TableActivity, send TableId in Bundle
                Bundle bundle = new Bundle();
                bundle.putString("TABLE_ID",Integer.toString(table.getTableId()));
                bundle.putString("EMPLOYEE_ID", Integer.toString(table.getEmployeeId()));
                Intent intent = new Intent(TablesActivity.this, TableActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        buttonHome = findViewById(R.id.buttonTablesHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TablesActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        buttonRefresh = findViewById(R.id.buttonTablesRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchTables();
            }
        });

    }

    private void fetchTables()
    {
        TableRepo tableRepo = new TableRepo();
        ExecutorService executorService = ((ExecServiceApp) getApplication()).executorService;
        tableRepo.getTables(executorService,handler);
    }
}