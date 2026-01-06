package com.example.jewelryvault;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyCollectionActivity extends AppCompatActivity {

    TextView listText, titleText;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        listText = findViewById(R.id.listText);
        titleText = findViewById(R.id.titleText);
        db = new DBHelper(this);

        displayJewelry();
    }

    private void displayJewelry() {
        Cursor cursor = db.getAllJewelry();

        if (cursor.getCount() == 0) {
            listText.setText("No jewelry items found.\n\nAdd some jewelry first!");
            titleText.setText("My Collection (0 items)");
        } else {
            StringBuilder data = new StringBuilder();
            int count = 0;
            double totalValue = 0;

            while (cursor.moveToNext()) {
                count++;
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String value = cursor.getString(3);

                // Calculate total value
                try {
                    totalValue += Double.parseDouble(value.replace("$", "").trim());
                } catch (NumberFormatException e) {
                    // Ignore if value is not a number
                }

                data.append("💎 ").append(name).append("\n")
                        .append("   Type: ").append(type).append("\n")
                        .append("   Value: $").append(value).append("\n\n");
            }

            titleText.setText("My Collection (" + count + " items)");
            String summary = "Total Items: " + count + "\n"
                    + "Total Value: $" + String.format("%.2f", totalValue) + "\n\n"
                    + "──────────────────\n\n";
            listText.setText(summary + data.toString());
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.closeDB();
        }
    }
}