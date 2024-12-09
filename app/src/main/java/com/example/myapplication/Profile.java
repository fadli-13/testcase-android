package com.example.myapplication;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

public class Profile extends AppCompatActivity {

    private ImageView circularImage;
    private TextView tvWeight, tvHeight, tvPhone, tvAbout;
    private TextView tvNameValue, tvAgeValue, tvWeightValue, tvHeightValue, tvPhoneValue, tvAboutValue, tvGenderValue, tvUsernameValue, tvEmailValue, tvExperienceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        circularImage = findViewById(R.id.circular_image);
        tvWeight = findViewById(R.id.tv_weight);
        tvHeight = findViewById(R.id.tv_height);
        tvPhone = findViewById(R.id.tv_phone);
        tvAbout = findViewById(R.id.tv_about);

        tvNameValue = findViewById(R.id.tv_name_value);
        tvAgeValue = findViewById(R.id.tv_age_value);
        tvWeightValue = findViewById(R.id.tv_weight_value);
        tvHeightValue = findViewById(R.id.tv_height_value);
        tvPhoneValue = findViewById(R.id.tv_phone_value);
        tvAboutValue = findViewById(R.id.tv_about_value);
        tvGenderValue = findViewById(R.id.tv_gender_value);
        tvUsernameValue = findViewById(R.id.tv_username_value);
        tvEmailValue = findViewById(R.id.tv_email_value);
        tvExperienceValue = findViewById(R.id.tv_experienced_value);

        SharedPreferences userDataPrefs = getSharedPreferences("UserData", MODE_PRIVATE);

        String name = userDataPrefs.getString("name", "Name not set");
        int age = userDataPrefs.getInt("age", -1);
        int weight = userDataPrefs.getInt("weight", -1);
        int height = userDataPrefs.getInt("height", -1);
        long phone = userDataPrefs.getLong("phone", 0);
        String about = userDataPrefs.getString("about", "About not set");
        String gender = userDataPrefs.getString("gender", "Gender not set");
        String experience = userDataPrefs.getString("experience", "Experience not set");
        String username = userDataPrefs.getString("username", "Username not set");
        String email = userDataPrefs.getString("email", "Email not set");

        tvNameValue.setText(name);
        tvAgeValue.setText((age > 0 ? age + " y/o" : "Age not set"));
        tvWeightValue.setText((weight > 0 ? weight + " kg" : "Weight not set"));
        tvHeightValue.setText((height > 0 ? height + " cm" : "Height not set"));
        tvPhoneValue.setText((phone > 0 ? "+62" + phone : "Phone not set"));
        tvAboutValue.setText(about);
        tvGenderValue.setText(gender);
        tvExperienceValue.setText(experience);
        tvUsernameValue.setText(username);
        tvEmailValue.setText(email);

        try {
            String profileImageUri = userDataPrefs.getString("profileImageUri_" + username, null);
            if (profileImageUri != null && !profileImageUri.isEmpty()) {
                Uri imageUri = Uri.parse(profileImageUri);
                Glide.with(this)
                        .load(imageUri)
                        .transform(new CircleCrop())
                        .into(circularImage);
            } else {
                circularImage.setImageResource(R.drawable.icon_camera);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading profile image", Toast.LENGTH_SHORT).show();
        }
    }
}
