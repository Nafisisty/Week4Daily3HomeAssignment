package com.example.week4daily3homeassignment.view.activities.thirdactivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.week4daily3homeassignment.R;
import com.example.week4daily3homeassignment.model.user.User;

public class ThirdActivity extends AppCompatActivity implements ThirdActivityContract {

    ThirdActivityPresenter thirdActivityPresenter;

    TextView userNameTextView;
    TextView userEmailTextView;
    TextView userBirthDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        userNameTextView = findViewById(R.id.userNameTextViewId);
        userEmailTextView = findViewById(R.id.userEmailTextViewId);
        userBirthDateTextView = findViewById(R.id.userBirthDateTextViewId);

        thirdActivityPresenter = new ThirdActivityPresenter(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            thirdActivityPresenter.setUserData(bundle);

        }
    }

    @Override
    public void sendToActivity(@NonNull User user) {

        userNameTextView.setText(user.getUserName());
        userEmailTextView.setText(user.getUserEmail());
        userBirthDateTextView.setText(user.getUserBirthDate());
    }
}
