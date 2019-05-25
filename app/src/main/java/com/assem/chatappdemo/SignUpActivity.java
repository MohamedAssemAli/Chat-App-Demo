package com.assem.chatappdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    private String sampleProfileImgUrl = "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200";
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
                        FirebaseDatabase.getInstance().getReference().child("users").child(authResult.getUser().getUid()).setValue(new User(authResult.getUser().getUid(), email, password, sampleProfileImgUrl));
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName("Jane Q. User")
                                .setPhotoUri(Uri.parse(sampleProfileImgUrl))
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User profile updated.");
                                        }
                                    }
                                });
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
