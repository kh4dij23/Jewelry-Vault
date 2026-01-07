package com.example.jewelryvault;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddJewelryActivity extends AppCompatActivity {

    EditText name, value;
    Spinner typeSpinner;
    Button saveBtn;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jewelry);

        name = findViewById(R.id.name);
        typeSpinner = findViewById(R.id.typeSpinner);
        value = findViewById(R.id.value);
        saveBtn = findViewById(R.id.saveBtn);

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.jewelry_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        db = new DBHelper(this);

        saveBtn.setOnClickListener(v -> {
            String jewelryName = name.getText().toString().trim();
            String jewelryType = typeSpinner.getSelectedItem().toString();
            String jewelryValue = value.getText().toString().trim();

            if (jewelryName.isEmpty() || jewelryValue.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.addJewelry(jewelryName, jewelryType, jewelryValue)) {
                Toast.makeText(this, "Jewelry Saved 💎", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save jewelry", Toast.LENGTH_SHORT).show();
            }
        });
    }
}