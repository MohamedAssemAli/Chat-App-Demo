package com.assem.chatappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    @OnClick(R.id.start_activity_sign_in)
    void goToSignIn() {
        startActivity(new Intent(this, SignInActivity.class));
    }

    @OnClick(R.id.start_activity_sign_up)
    void goToSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }
}
