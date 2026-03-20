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
    private final List<Integer> numberHistory = new ArrayList<>();

    public LiveData<Integer> getRandomNumber() {
        return randomNumber;
    }

    public List<Integer> getNumberHistory() {
        return numberHistory;
    }

    public void generateNewRandomNumber() {
        int newNumber = random.nextInt(100);
        numberHistory.add(newNumber);
        randomNumber.setValue(newNumber);
    }

    public void setNumberHistory(List<Integer> history) {
        numberHistory.clear();
        numberHistory.addAll(history);
    }
} 