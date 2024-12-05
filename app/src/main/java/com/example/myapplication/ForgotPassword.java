package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        Button buttonChangePassword = findViewById(R.id.btn_change_password);
        EditText etUsername = findViewById(R.id.et_username);
        EditText etOldPassword = findViewById(R.id.et_old_password);
        EditText etNewPassword = findViewById(R.id.et_new_password);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String oldPassword = etOldPassword.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(ForgotPassword.this, "Username tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(oldPassword)) {
                    Toast.makeText(ForgotPassword.this, "Password lama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(ForgotPassword.this, "Password baru tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(newPassword)) {
                    Toast.makeText(ForgotPassword.this, "Password baru harus 8-25 karakter, diawali huruf besar, dan mengandung angka!", Toast.LENGTH_LONG).show();
                } else {
                    changePassword(username, oldPassword, newPassword);
                }
            }
        });
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.length() <= 25 &&
                password.matches("^[A-Z][A-Za-z\\d]*$") &&
                password.matches(".*\\d.*");
    }

    private void changePassword(String username, String oldPassword, String newPassword) {
        String savedUsername = sharedPreferences.getString("username", null);
        String savedPassword = sharedPreferences.getString("password", null);

        if (username.equals(savedUsername) && oldPassword.equals(savedPassword)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("password", newPassword);
            editor.apply();

            Toast.makeText(this, "Password berhasil diubah!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Username atau password lama salah!", Toast.LENGTH_SHORT).show();
        }
    }
}
