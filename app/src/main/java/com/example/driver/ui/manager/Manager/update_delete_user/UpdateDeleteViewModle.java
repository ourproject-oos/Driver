package com.example.driver.ui.manager.Manager.update_delete_user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class UpdateDeleteViewModle extends ViewModel {

    private MutableLiveData<String> mText;

    public UpdateDeleteViewModle () {
        mText = new MutableLiveData<>();
        mText.setValue("This is update fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
