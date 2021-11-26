package com.dmitrij.viable.ndurasov.ui;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayerViewModel extends ViewModel {
    private final MutableLiveData<Pair<Integer, Integer>> progress = new MutableLiveData<Pair<Integer, Integer>>();

    public PlayerViewModel() {
        progress.setValue(new Pair<>(0, 0));
    }

    public LiveData<Pair<Integer, Integer>> getProgress() {
        return progress;
    }
    public void setProgress(Pair<Integer, Integer> p) {
        if (p != null){
            progress.setValue(p);
        } else {
            progress.setValue(new Pair<>(0, 0));
        }
    }
}