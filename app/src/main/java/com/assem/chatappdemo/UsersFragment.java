package com.assem.chatappdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersFragment extends Fragment {
    private static final String TAG = UsersFragment.class.getSimpleName();
    // Vars
    private ArrayList<User> usersArrayList;
    private UsersAdapter usersAdapter;
    // Firebase
    private DatabaseReference mRef;
    // Views
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_users_recycler_view)
    RecyclerView usersRecycler;
    @BindView(R.id.fragment_users_empty_recycler)
    TextView emptyUsersRecycler;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);
        mRef = FirebaseDatabase.getInstance().getReference();
        usersArrayList = new ArrayList<>();
        usersAdapter = new UsersAdapter(requireContext(), usersArrayList);
        usersRecycler.setAdapter(usersAdapter);
        new ViewsUtils().setupLinearVerticalRecView(requireContext(), usersRecycler);
        getUsers();
        return view;
    }


    private void getUsers() {
        toggleLayout(false);
        mRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "hahaha : " + dataSnapshot);
                if (dataSnapshot.exists()) {
                    usersArrayList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "hahaha : " + snapshot.getValue(User.class));
                        User user = snapshot.getValue(User.class);
                        assert user != null;
                        if (!user.getId().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                            usersArrayList.add(user);
                        usersAdapter.notifyDataSetChanged();
                    }
                    toggleLayout(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
                Log.d(TAG, databaseError.getDetails());
                Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void toggleLayout(boolean flag) {
        if (flag) {
            usersRecycler.setVisibility(View.VISIBLE);
            progressLayout.setVisibility(View.GONE);
            progressBar.hide();
        } else {
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.show();
            usersRecycler.setVisibility(View.GONE);
        }
    }
}
