package com.example.pdfviwer.stickynotes;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    AppDatabase appDatabase;
   public UserViewModel(Application application) {
        super(application);
       appDatabase= AppDatabase.getInstance(application.getApplicationContext());

    }
    public LiveData<List<User>> getAllUser(){
     return appDatabase.userDao().getLiveAll();
    }
}