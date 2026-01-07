package com.example.jewelryvault;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyCollectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JewelryAdapter adapter;
    private ArrayList<JewelryModel> jewelryList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        jewelryList = new ArrayList<>();

        loadJewelryData();

        adapter = new JewelryAdapter(jewelryList);
        recyclerView.setAdapter(adapter);
    }

    private void loadJewelryData() {
        Cursor cursor = dbHelper.getAllJewelry();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String value = cursor.getString(cursor.getColumnIndexOrThrow("value"));

                jewelryList.add(new JewelryModel(id, name, type, value));
            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}