package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        Button buttonSignIn = findViewById(R.id.btn_sign_in);
        EditText etUsername = findViewById(R.id.et_username);
        EditText etPassword = findViewById(R.id.et_password);
        TextView tvSignUp = findViewById(R.id.tv_sign_up);
        TextView tvForgotPassword = findViewById(R.id.tv_forgot_password);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredInput = etUsername.getText().toString().trim();
                String enteredPassword = etPassword.getText().toString().trim();

                String savedUsername = sharedPreferences.getString("username", null);
                String savedEmail = sharedPreferences.getString("email", null);
                String savedPassword = sharedPreferences.getString("password", null);

                if (enteredInput.isEmpty() || enteredPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Username/email dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
                else if ((enteredInput.equals(savedUsername) || enteredInput.equals(savedEmail)) && enteredPassword.equals(savedPassword)) {
                    Intent intent = new Intent(MainActivity.this, Profile.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Username/email atau password salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        String signUpText = "Don't have an account yet? Sign Up";
        SpannableString spannableSignUp = new SpannableString(signUpText);

        int signUpStart = signUpText.indexOf("Sign Up");
        int signUpEnd = signUpStart + "Sign Up".length();

        spannableSignUp.setSpan(new ForegroundColorSpan(Color.BLUE), signUpStart, signUpEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvSignUp.setText(spannableSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        String forgotPasswordText = "Forget Password? Click Here";
        SpannableString spannableForgotPassword = new SpannableString(forgotPasswordText);

        int forgotPasswordStart = forgotPasswordText.indexOf("Click Here");
        int forgotPasswordEnd = forgotPasswordStart + "Click Here".length();

        spannableForgotPassword.setSpan(new ForegroundColorSpan(Color.BLUE), forgotPasswordStart, forgotPasswordEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvForgotPassword.setText(spannableForgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }
}
