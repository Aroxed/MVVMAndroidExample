package com.example.mvvmandroidexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView tvRandomNumber;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Initialize views
        Button btnRandom = findViewById(R.id.btnRandom);
        Button btnNavigate = findViewById(R.id.btnNavigate);
        tvRandomNumber = findViewById(R.id.tvRandomNumber);
        random = new Random();

        // Set up click listeners
        btnRandom.setOnClickListener(v -> generateRandomNumber());
        btnNavigate.setOnClickListener(v -> navigateToSecondActivity());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void generateRandomNumber() {
        int randomNumber = random.nextInt(100); // Generates a random number between 0 and 99
        tvRandomNumber.setText(String.valueOf(randomNumber));
    }

    private void navigateToSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}