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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.activity_sign_up_email_edit_text)
    TextInputEditText emailTxt;
    @BindView(R.id.activity_sign_up_password_edit_text)
    TextInputEditText passwordTxt;

    @OnClick(R.id.activity_sign_up_button)
    void signUp() {
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseDatabase.getInstance().getReference().child("users").child(authResult.getUser().getUid()).setValue(new User(authResult.getUser().getUid(), email, password));
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, R.string.error, Toast.LENGTH_LONG).show());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }
}
