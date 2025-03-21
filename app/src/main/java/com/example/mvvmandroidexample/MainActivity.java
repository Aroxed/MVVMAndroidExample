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
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private TextView tvRandomNumber;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        
        // Initialize views
        Button btnRandom = findViewById(R.id.btnRandom);
        Button btnNavigate = findViewById(R.id.btnNavigate);
        tvRandomNumber = findViewById(R.id.tvRandomNumber);

        // Set up click listeners
        btnRandom.setOnClickListener(v -> viewModel.generateNewRandomNumber());
        btnNavigate.setOnClickListener(v -> navigateToSecondActivity());

        // Observe random number changes
        viewModel.getRandomNumber().observe(this, number -> {
            if (number != null) {
                tvRandomNumber.setText(String.valueOf(number));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void navigateToSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}