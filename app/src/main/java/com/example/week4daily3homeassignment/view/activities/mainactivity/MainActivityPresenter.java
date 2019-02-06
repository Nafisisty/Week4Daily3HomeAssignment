package com.example.week4daily3homeassignment.view.activities.mainactivity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.week4daily3homeassignment.model.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivityPresenter {

    MainActivityContract mainActivityContract;


    public MainActivityPresenter(MainActivityContract mainActivityContract) {
        this.mainActivityContract = mainActivityContract;
    }

    public void userSignIn(final FirebaseAuth firebaseAuth, String email, String password){


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new MainActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            mainActivityContract.sendToActivity(user);
                        } else {
                            mainActivityContract.sendToActivity(null);

                        }

                    }
                });
    }


    public void userSignUp(final FirebaseAuth firebaseAuth, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new MainActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            mainActivityContract.sendToActivity(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            mainActivityContract.sendToActivity(null);
                        }
                    }
                });
    }

    public void getFbUserInfo(JSONObject object) {

        User user = new User();
        try {

            user.setUserEmail(object.getString("email"));
            user.setUserBirthDate(object.getString("birthday"));
            user.setUserName(object.getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mainActivityContract.sendUserInfoToActivity(user);
    }
}
