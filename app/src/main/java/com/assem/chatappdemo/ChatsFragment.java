package com.assem.chatappdemo;

import android.content.Context;
import android.net.Uri;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatsFragment extends Fragment {
    private static final String TAG = UsersFragment.class.getSimpleName();
    // Vars
    private ArrayList<Chat> chatsArrayList;
    private ChatsAdapter chatsAdapter;
    // Firebase
    private DatabaseReference mRef;
    // Views
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.fragment_chats_recycler_view)
    RecyclerView chatsRecycler;
    @BindView(R.id.fragment_chats_empty_recycler)
    TextView emptyChatsRecycler;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        ButterKnife.bind(this, view);
        mRef = FirebaseDatabase.getInstance().getReference();
        chatsArrayList = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference();
        chatsAdapter = new ChatsAdapter(requireContext(), chatsArrayList);
        chatsRecycler.setAdapter(chatsAdapter);
        new ViewsUtils().setupLinearVerticalRecView(requireContext(), chatsRecycler);
        getChats();
        return view;
    }

    private void getChats() {
        toggleLayout(false);
        mRef.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "hahaha : " + dataSnapshot);
                if (dataSnapshot.exists()) {
                    chatsArrayList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "hahaha : " + snapshot.getValue(Chat.class));
                        Chat chat = snapshot.getValue(Chat.class);
                        chatsArrayList.add(chat);
                        chatsAdapter.notifyDataSetChanged();
                    }
                    toggleLayout(true);
                    emptyChatsRecycler.setVisibility(View.GONE);
                } else {
                    toggleLayout(true);
                    emptyChatsRecycler.setVisibility(View.VISIBLE);
                    chatsRecycler.setVisibility(View.GONE);
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
            chatsRecycler.setVisibility(View.VISIBLE);
            progressLayout.setVisibility(View.GONE);
            progressBar.hide();
        } else {
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.show();
            chatsRecycler.setVisibility(View.GONE);
        }
    }
}
