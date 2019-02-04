package com.example.week4daily3homeassignment.view.activities.mainactivity;

import com.example.week4daily3homeassignment.model.user.User;
import com.google.firebase.auth.FirebaseUser;

public interface MainActivityContract {

    void sendToActivity(FirebaseUser user);

    void sendUserInfoToActivity(User aUser);
}
