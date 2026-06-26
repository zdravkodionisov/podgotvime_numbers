package com.example.podgotvime_numbers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.content.SharedPreferences;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);



        EditText editEmail = findViewById(R.id.editLoginEmail);
        EditText editPassword = findViewById(R.id.editLoginPassword);
        Button btnLoginUser = findViewById(R.id.btnLoginUser);

        btnLoginUser.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            JSONObject json = new JSONObject();

            try {
                json.put("email", email);
                json.put("password", password);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String url = "http://10.0.2.2:5002/api/auth/login";

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    json,
                    response -> {
                        try {

                            JSONObject student = response.getJSONObject("student");

                            String firstName = student.getString("firstName");
                            String lastName = student.getString("lastName");
                            String city = student.getString("city");
                            String school = student.getString("school");
                            int grade = student.getInt("grade");
                            String token = response.getString("token");

                            SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();

                            editor.putString("token", token);
                            editor.putString("firstName", firstName);
                            editor.putString("lastName", lastName);
                            editor.putString("city", city);
                            editor.putString("school", school);
                            editor.putInt("grade", grade);

                            editor.apply();

                            Toast.makeText(this, "Здравей " + firstName, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                            intent.putExtra("firstName", firstName);
                            intent.putExtra("lastName", lastName);
                            intent.putExtra("city", city);
                            intent.putExtra("school", school);
                            intent.putExtra("grade", grade);

                            startActivity(intent);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        error.printStackTrace();

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

        });

    }
}