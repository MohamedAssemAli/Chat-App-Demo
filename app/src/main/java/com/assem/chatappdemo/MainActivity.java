package com.assem.chatappdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = ChatActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    // Views
    @BindView(R.id.activity_main_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.activity_main_view_pager)
    ViewPager viewPager;

    // OnClicks
    @OnClick(R.id.activity_main_logout)
    void logout() {
        logoutUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        // Setup TabLayout
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ChatsFragment(), getString(R.string.chats));
        viewPagerAdapter.addFragment(new UsersFragment(), getString(R.string.users));
        new ViewsUtils().setupTabLayout(viewPager, viewPagerAdapter, tabLayout, 0);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.d(TAG, "No logged user");
            Toast.makeText(this, "No logged user", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, currentUser.getUid(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // check if user is signed in and update UI accordingly
        if (mAuth.getCurrentUser() == null) {
            sendToStart();
        }
    }

    private void logoutUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mAuth.signOut();
            sendToStart();
        }
    }

    private void sendToStart() {
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }

}
