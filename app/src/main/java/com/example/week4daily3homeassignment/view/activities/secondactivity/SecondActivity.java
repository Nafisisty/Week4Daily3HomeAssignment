package com.example.week4daily3homeassignment.view.activities.secondactivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.week4daily3homeassignment.R;
import com.example.week4daily3homeassignment.model.user.User;
import com.example.week4daily3homeassignment.view.activities.thirdactivity.ThirdActivity;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity implements SecondActivityContract{

    SecondActivityPresenter secondActivityPresenter;
    EditText displayNameEditText;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        displayNameEditText = findViewById(R.id.displayNameEditTextViewId);
        emailEditText = findViewById(R.id.emailEditTextViewId);

        secondActivityPresenter = new SecondActivityPresenter(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            secondActivityPresenter.setUserData(bundle);

        }
    }

    @Override
    public void sendToActivity(FirebaseUser user) {

        displayNameEditText.setText(user.getDisplayName());
        emailEditText.setText(user.getEmail());

    }

    public void onClick(View view) {

        String displayName = displayNameEditText.getText().toString();
        String email = emailEditText.getText().toString();

        secondActivityPresenter.saveUserInfo(displayName, email);

        Toast.makeText(this,"Saved Successfully", Toast.LENGTH_SHORT).show();

        User user = new User(displayName, email);
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);

    }
}
