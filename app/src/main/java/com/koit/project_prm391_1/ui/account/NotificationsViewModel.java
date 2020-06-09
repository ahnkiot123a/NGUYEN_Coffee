package com.koit.project_prm391_1.ui.account;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


//load dữ liệu
public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Đây là nơi hiện thị tài khoản");

    }

    public LiveData<String> getText() {
        return mText;
    }
}