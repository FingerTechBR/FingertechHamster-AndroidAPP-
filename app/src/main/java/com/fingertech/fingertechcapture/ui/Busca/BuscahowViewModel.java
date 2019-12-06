package com.fingertech.fingertechcapture.ui.Busca;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BuscahowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BuscahowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}