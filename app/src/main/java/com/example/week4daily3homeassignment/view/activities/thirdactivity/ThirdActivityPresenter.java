package com.example.week4daily3homeassignment.view.activities.thirdactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.week4daily3homeassignment.model.user.User;

public class ThirdActivityPresenter {

    ThirdActivityContract thirdActivityContract;

    public ThirdActivityPresenter(ThirdActivityContract thirdActivityContract) {
        this.thirdActivityContract = thirdActivityContract;
    }

    public void setUserData(@NonNull Bundle bundle) {

        User user = bundle.getParcelable("user");

        thirdActivityContract.sendToActivity(user);

    }
}
