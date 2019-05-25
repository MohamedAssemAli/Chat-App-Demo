package com.assem.chatappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.activity_sign_in_email_edit_text)
    TextInputEditText emailTxt;
    @BindView(R.id.activity_sign_in_password_edit_text)
    TextInputEditText passwordTxt;

    @OnClick(R.id.activity_sign_in_button)
    void signUp() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailTxt.getText().toString(), passwordTxt.getText().toString())
                .addOnSuccessListener(authResult -> {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                })
                .addOnFailureListener(e -> Toast.makeText(SignInActivity.this, R.string.error, Toast.LENGTH_LONG).show());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }
}
