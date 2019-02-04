package com.example.week4daily3homeassignment.view.activities.mainactivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week4daily3homeassignment.R;
import com.example.week4daily3homeassignment.model.user.User;
import com.example.week4daily3homeassignment.view.activities.secondactivity.SecondActivity;
import com.example.week4daily3homeassignment.view.activities.thirdactivity.ThirdActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MainActivityContract {

    MainActivityPresenter mainActivityPresenter;
    CallbackManager callbackManager;
    LoginButton loginButton;

    FirebaseAuth firebaseAuth;
    EditText emailEditTextView;
    EditText passwordEditTextView;
    TextView loginResultTextView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        mainActivityPresenter = new MainActivityPresenter(this);

        emailEditTextView = findViewById(R.id.emailEditTextViewId);
        passwordEditTextView = findViewById(R.id.passwordEditTextViewId);
        loginResultTextView = findViewById(R.id.loginResultTextViewId);

        callbackManager = CallbackManager.Factory.create();

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_birthday"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                    mainActivityPresenter.getFbUserInfo(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();



            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void onClick(View view) {

        String email = emailEditTextView.getText().toString();
        String password = passwordEditTextView.getText().toString();

        switch (view.getId()) {

            case R.id.signInButtonId:
                mainActivityPresenter.userSignIn(firebaseAuth, email, password);
                break;

            case R.id.signUpButtonId:
                mainActivityPresenter.userSignUp(firebaseAuth, email, password);
                break;

        }

    }


    private void successfullLogin(FirebaseUser userInfo) {

        Intent intent = new Intent(this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", userInfo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void sendToActivity(FirebaseUser user) {

        if(user != null){

            successfullLogin(user);

        }
        else {

            Toast.makeText(this, "User Don't Exist", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void sendUserInfoToActivity(User aUser) {

        user = aUser;
        Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);

    }
}
