package com.example.mvvmandroidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String KEY_NUMBER_HISTORY = "number_history";
    private TextView tvRandomNumber;
    private TextView tvHistory;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Activity is being created");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        
        // Initialize views
        Button btnRandom = findViewById(R.id.btnRandom);
        Button btnNavigate = findViewById(R.id.btnNavigate);
        tvRandomNumber = findViewById(R.id.tvRandomNumber);
        tvHistory = findViewById(R.id.tvHistory);

        // Set up click listeners
        btnRandom.setOnClickListener(v -> viewModel.generateNewRandomNumber());
        btnNavigate.setOnClickListener(v -> navigateToSecondActivity());

        // Observe random number changes
        viewModel.getRandomNumber().observe(this, number -> {
            if (number != null) {
                tvRandomNumber.setText(String.valueOf(number));
            }
        });
        viewModel.getNumberHistory().observe(this, this::updateHistoryDisplay);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity is becoming visible");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity is in foreground and interactive");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity is partially obscured");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity is being destroyed");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: Saving activity state");
        List<Integer> history = viewModel.getNumberHistory().getValue();
        if (history != null) {
            outState.putIntegerArrayList(KEY_NUMBER_HISTORY, new ArrayList<>(history));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: Restoring activity state");
        ArrayList<Integer> history = savedInstanceState.getIntegerArrayList(KEY_NUMBER_HISTORY);
        if (history != null) {
            viewModel.setNumberHistory(history);
        }
    }

    private void updateHistoryDisplay(List<Integer> history) {
        StringBuilder sb = new StringBuilder("History:\n");
        if (history != null) {
            for (int i = 0; i < history.size(); i++) {
                sb.append(i + 1).append(". ").append(history.get(i)).append("\n");
            }
        }
        tvHistory.setText(sb.toString());
    }

    private void navigateToSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}