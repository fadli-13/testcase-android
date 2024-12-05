package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;


import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        Button buttonSignUp = findViewById(R.id.btn_sign_up);
        EditText etUsername = findViewById(R.id.et_username);
        EditText etEmail = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(SignUp.this, "Username tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (!isValidUsername(username)) {
                    Toast.makeText(SignUp.this, "Username harus 5-15 karakter dan mengandung huruf dan angka!", Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty()) {
                    Toast.makeText(SignUp.this, "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(SignUp.this, "Email harus mengandung '@' dan diakhiri dengan '.com'!", Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    Toast.makeText(SignUp.this, "Password harus 8-25 karakter, mengandung huruf, angka, dan diawali huruf besar!", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();

                    Intent intent = new Intent(SignUp.this, DataDiri.class);
                    startActivity(intent);
                    Toast.makeText(SignUp.this, "Sign up berhasil!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView textView = findViewById(R.id.tv_sign_in);
        String text = "Already have an account? Sign In";
        SpannableString spannableString = new SpannableString(text);

        int start = text.indexOf("Sign In");
        int end = start + "Sign In".length();

        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 5 &&
                username.length() <= 15 &&
                username.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") && email.endsWith(".com");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.length() <= 25 &&
                password.matches("^[A-Z][A-Za-z\\d]+$") &&
                password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$");
    }}

