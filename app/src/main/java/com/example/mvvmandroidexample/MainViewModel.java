package com.example.mvvmandroidexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainViewModel extends ViewModel {
    private final Random random = new Random();
    private final MutableLiveData<Integer> randomNumber = new MutableLiveData<>();
    private final MutableLiveData<List<Integer>> numberHistory = new MutableLiveData<>(new ArrayList<>());

    public LiveData<Integer> getRandomNumber() {
        return randomNumber;
    }

    public LiveData<List<Integer>> getNumberHistory() {
        return numberHistory;
    }

    public void generateNewRandomNumber() {
        int newNumber = random.nextInt(100);
        List<Integer> currentHistory = numberHistory.getValue();
        List<Integer> updatedHistory = currentHistory == null
                ? new ArrayList<>()
                : new ArrayList<>(currentHistory);
        updatedHistory.add(newNumber);
        numberHistory.setValue(updatedHistory);
        randomNumber.setValue(newNumber);
    }

    public void setNumberHistory(List<Integer> history) {
        numberHistory.setValue(new ArrayList<>(history));
    }
} 