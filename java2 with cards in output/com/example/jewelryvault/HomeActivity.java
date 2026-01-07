package com.example.jewelryvault;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button addBtn, viewBtn;
    TextView welcomeText;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addBtn = findViewById(R.id.addBtn);
        viewBtn = findViewById(R.id.viewBtn);
        welcomeText = findViewById(R.id.welcomeText);

        db = new DBHelper(this);

        // Get username from intent
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            welcomeText.setText("Welcome, " + username + "! 💍");
        }

        // Get jewelry count and update subtitle
        int jewelryCount = db.getJewelryCount();
        TextView subtitle = findViewById(R.id.subtitle);
        subtitle.setText("You have " + jewelryCount + " jewelry items in your vault");

        addBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AddJewelryActivity.class)));

        viewBtn.setOnClickListener(v ->
                startActivity(new Intent(this, MyCollectionActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
        }
    }
}