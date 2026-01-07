package com.example.jewelryvault;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText newPassword, confirmPassword;
    Button resetBtn;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newPassword = findViewById(R.id.newPass);
        confirmPassword = findViewById(R.id.confirmPass);
        resetBtn = findViewById(R.id.resetBtn);
        db = new DBHelper(this);

        resetBtn.setOnClickListener(v -> {
            String newPass = newPassword.getText().toString().trim();
            String confirmPass = confirmPassword.getText().toString().trim();

            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // In a real app, you would verify the user's identity first
            // For now, we'll use a default username or get it from intent
            String username = getIntent().getStringExtra("username");

            if (username == null || username.isEmpty()) {
                Toast.makeText(this, "Please enter username in previous screen", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            if (db.resetPassword(username, newPass)) {
                Toast.makeText(this, "Password Reset Successful 💖", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Reset failed. User not found.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
        }
    }
}