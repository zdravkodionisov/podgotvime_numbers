package com.example.podgotvime_numbers;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);

        String token = prefs.getString("token", null);

        if (token != null) {

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);

            intent.putExtra("firstName", prefs.getString("firstName", ""));
            intent.putExtra("lastName", prefs.getString("lastName", ""));
            intent.putExtra("city", prefs.getString("city", ""));
            intent.putExtra("school", prefs.getString("school", ""));
            intent.putExtra("grade", prefs.getInt("grade", 7));

            startActivity(intent);
            finish();
        }
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}