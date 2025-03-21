package com.example.mvvmandroidexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class MainViewModel extends ViewModel {
    private final Random random = new Random();
    private final MutableLiveData<Integer> randomNumber = new MutableLiveData<>();

    public LiveData<Integer> getRandomNumber() {
        return randomNumber;
    }
    //
    public void generateNewRandomNumber() {
        randomNumber.setValue(random.nextInt(100));
    }
} 