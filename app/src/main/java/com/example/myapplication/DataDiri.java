package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

public class DataDiri extends AppCompatActivity {

    private ImageButton circularImageButton;
    private SharedPreferences sharedPreferences;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_diri);

        Button buttonSubmit = findViewById(R.id.btn_submit);
        EditText etName = findViewById(R.id.et_name);
        EditText etAge = findViewById(R.id.et_age);
        EditText etWeight = findViewById(R.id.et_weight);
        EditText etHeight = findViewById(R.id.et_height);
        EditText etPhone = findViewById(R.id.et_phone);
        EditText etAbout = findViewById(R.id.et_about);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        RadioGroup rgExperience = findViewById(R.id.rgExperience);
        RadioButton rbLessYear = findViewById(R.id.rb_less_year);
        RadioButton rbMoreYear = findViewById(R.id.rb_more_year);
        EditText etLessYear = findViewById(R.id.et_Less_year);
        EditText etMoreYear = findViewById(R.id.et_more_year);

        circularImageButton = findViewById(R.id.circular_image);
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        etLessYear.setEnabled(false);
        etMoreYear.setEnabled(false);

        rgExperience.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_less_year) {
                etLessYear.setEnabled(true);
                etMoreYear.setEnabled(false);
                etMoreYear.setText("");
            } else if (checkedId == R.id.rb_more_year) {
                etMoreYear.setEnabled(true);
                etLessYear.setEnabled(false);
                etLessYear.setText("");
            }
        });

        String[] items = {"Male", "Female"};
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        autoCompleteTextView.setAdapter(adapterItems);

        circularImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);
        });

        loadProfileImage();

        buttonSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String ageStr = etAge.getText().toString().trim();
            String weightStr = etWeight.getText().toString().trim();
            String heightStr = etHeight.getText().toString().trim();
            String phoneStr = etPhone.getText().toString().trim();
            String about = etAbout.getText().toString().trim();
            String gender = autoCompleteTextView.getText().toString().trim();
            String lessYearStr = etLessYear.getText().toString().trim();
            String moreYearStr = etMoreYear.getText().toString().trim();

            if (name.isEmpty() || name.length() < 3 || name.length() > 25) {
                Toast.makeText(this, "Nama harus terdiri dari 3 hingga 25 karakter!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ageStr.isEmpty() || !isNumeric(ageStr)) {
                Toast.makeText(this, "Umur harus berupa angka!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (weightStr.isEmpty() || !isNumeric(weightStr)) {
                Toast.makeText(this, "Berat badan harus berupa angka!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (heightStr.isEmpty() || !isNumeric(heightStr)) {
                Toast.makeText(this, "Tinggi badan harus berupa angka!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phoneStr.isEmpty() || phoneStr.length() < 10 || !isNumeric(phoneStr)) {
                Toast.makeText(this, "Nomor telepon harus terdiri dari minimal 10 angka!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (about.isEmpty() || about.length() < 5 || about.length() > 50) {
                Toast.makeText(this, "Tentang diri harus terdiri dari 5 hingga 50 karakter!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (rgExperience.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Harap pilih pengalaman kerja!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rbLessYear.isChecked() && lessYearStr.isEmpty()) {
                Toast.makeText(this, "Harap masukkan jumlah bulan pengalaman kerja!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rbMoreYear.isChecked() && moreYearStr.isEmpty()) {
                Toast.makeText(this, "Harap masukkan jumlah tahun pengalaman kerja!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int age = Integer.parseInt(ageStr);
                int weight = Integer.parseInt(weightStr);
                int height = Integer.parseInt(heightStr);
                long phone = Long.parseLong(phoneStr);
                int lessYear = lessYearStr.isEmpty() ? 0 : Integer.parseInt(lessYearStr);
                int moreYear = moreYearStr.isEmpty() ? 0 : Integer.parseInt(moreYearStr);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putInt("age", age);
                editor.putInt("weight", weight);
                editor.putInt("height", height);
                editor.putLong("phone", phone);
                editor.putString("about", about);
                editor.putString("gender", gender);
                if (rbLessYear.isChecked()) {
                    editor.putString("Experienced", lessYear + " months");
                } else if (rbMoreYear.isChecked()) {
                    editor.putString("Experienced", moreYear + " years");
                }
                editor.apply();


                Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DataDiri.this, Profile.class);
                startActivity(intent);

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Pastikan umur, berat, tinggi, dan nomor telepon adalah angka valid!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profileImageUri", imageUri.toString());
                editor.apply();

                Glide.with(this)
                        .load(imageUri)
                        .transform(new CircleCrop())  // Make it circular
                        .into(circularImageButton);
            } else {
                Toast.makeText(this, "Gagal memuat gambar.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadProfileImage() {
        String savedImageUri = sharedPreferences.getString("profileImageUri", null);
        if (savedImageUri != null) {
            imageUri = Uri.parse(savedImageUri);
            Glide.with(this)
                    .load(imageUri)
                    .transform(new CircleCrop())  // Make it circular
                    .into(circularImageButton);
        }
    }
}
