package com.example.jewelryvault;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button loginBtn;
    TextView registerText, forgotText;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerText = findViewById(R.id.registerText);
        forgotText = findViewById(R.id.forgotText);

        // Initialize database helper
        db = new DBHelper(this);

        // Login button click
        loginBtn.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Check login in SQLite
            if (db.checkLogin(user, pass)) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                // ✅ Pass username to HomeActivity
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("username", user); // pass logged-in username
                startActivity(intent);

                // ✅ Close LoginActivity so user cannot go back with back button
                finish();

            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        });

        // Register link
        registerText.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        // Forgot password link
        forgotText.setOnClickListener(v ->
                startActivity(new Intent(this, ForgotPasswordActivity.class)));
    }
}