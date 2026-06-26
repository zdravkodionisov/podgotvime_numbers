package com.example.podgotvime_numbers;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        EditText editFirstName = findViewById(R.id.editFirstName);
        EditText editLastName = findViewById(R.id.editLastName);
        EditText editGrade = findViewById(R.id.editGrade);
        EditText editSchool = findViewById(R.id.editSchool);
        EditText editCity = findViewById(R.id.editCity);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPassword = findViewById(R.id.editPassword);
        Button btnCreateProfile = findViewById(R.id.btnCreateProfile);

        btnCreateProfile.setOnClickListener(v -> {

            String firstName = editFirstName.getText().toString();
            String lastName = editLastName.getText().toString();
            int grade = Integer.parseInt(editGrade.getText().toString());
            String school = editSchool.getText().toString();
            String city = editCity.getText().toString();
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            JSONObject json = new JSONObject();

            try {
                json.put("firstName", firstName);
                json.put("lastName", lastName);
                json.put("city", city);
                json.put("school", school);
                json.put("grade", grade);
                json.put("email", email);
                json.put("password", password);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String url = "http://10.0.2.2:5002/api/auth/register";

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    json,
                    response -> {
                        Toast.makeText(this,
                                "Регистрацията е успешна",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    },
                    error -> {
                        if (error.networkResponse != null) {
                            Toast.makeText(this,
                                    "Error: " + error.networkResponse.statusCode,
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this,
                                    error.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

            Toast.makeText(this,
                    firstName + " " + lastName,
                    Toast.LENGTH_SHORT).show();
        });

    }
}