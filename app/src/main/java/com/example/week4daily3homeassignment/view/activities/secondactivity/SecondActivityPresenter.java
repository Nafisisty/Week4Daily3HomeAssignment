package com.example.week4daily3homeassignment.view.activities.secondactivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SecondActivityPresenter {

    SecondActivityContract secondActivityContract;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    public SecondActivityPresenter(SecondActivityContract secondActivityContract) {
        this.secondActivityContract = secondActivityContract;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void setUserData(Bundle bundle) {

        FirebaseUser user = bundle.getParcelable("user");

        if(user != null) {

            firebaseUser = user;
            secondActivityContract.sendToActivity(user);

        }

    }

    public void saveUserInfo(String name, final String email) {

        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        firebaseUser.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            firebaseUser.updateEmail(email);
                        }
                    }
                });

    }
}
