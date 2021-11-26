package com.dmitrij.viable.ndurasov.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VideoViewModel extends ViewModel {
    private final MutableLiveData<Integer> activated = new MutableLiveData<Integer>();

    public VideoViewModel() {
        activated.setValue(0);
    }

    public LiveData<Integer> getProgress() {
        return activated;
    }
    public void setProgress(Integer p) {
        activated.setValue(p);
    }
}