package com.assem.chatappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();

    // Vars
    private String receiverUserId, test;
    private String senderUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private ChatMessagesAdapter chatMessagesAdapter;
    private ArrayList<ChatMessage> chatMessageArrayList;
    // Views
    @BindView(R.id.activity_chat_user_img)
    ImageView chatUserImg;
    @BindView(R.id.activity_chat_user_email)
    TextView userEmail;
    @BindView(R.id.activity_chat_recycler)
    RecyclerView chatRecycler;
    @BindView(R.id.activity_chat_message_edit_text)
    EditText msgTxt;
    @BindView(R.id.activity_chat_send_btn)
    ImageButton sendBtn;

    // OnClicks
    @OnClick(R.id.activity_chat_send_btn)
    void send() {
        if (!msgTxt.getText().toString().equals("")) {
            sendMsg();
            msgTxt.setText("");
        } else {
            Toast.makeText(ChatActivity.this, "Can't send empty msg!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
        test = mAuth.getCurrentUser().getUid();

        // get users data
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser == null) {
//            Log.d(TAG, "No logged user");
//            Toast.makeText(this, "No logged user", Toast.LENGTH_LONG).show();
//        }
//        senderUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        receiverUserId = getIntent().getStringExtra("receiver_user_id");
        senderUserId = getIntent().getStringExtra("sender_user_id");
        getReceiverData();
        // get chat data
        chatMessageArrayList = new ArrayList<>();
        chatMessagesAdapter = new ChatMessagesAdapter(this, chatMessageArrayList, test);
        new ViewsUtils().setupLinearVerticalRecView(this, chatRecycler);
        chatRecycler.setAdapter(chatMessagesAdapter);
        readMsg();
    }

    private void getReceiverData() {
        mRef.child("users").child(receiverUserId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);
                            userEmail.setText(user.getEmail());
                        } else {
                            Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ChatActivity.this, databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void sendMsg() {
        ChatMessage chatMessage = new ChatMessage(test, receiverUserId, msgTxt.getText().toString(), false);
        mRef.child("chats").child(test).push().setValue(chatMessage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChatActivity.this, "Msg is sent", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void readMsg() {
        mRef.child("chats").child(test)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG, "hahaha : " + dataSnapshot);
                        if (dataSnapshot.exists()) {
                            chatMessageArrayList.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d(TAG, "hahaha : " + snapshot.getValue(ChatMessage.class));
                                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                                chatMessageArrayList.add(chatMessage);
                                chatMessagesAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());
                        Log.d(TAG, databaseError.getDetails());
                        Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void readMsg_() {
        mRef.child("chats").child(test)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG, "hahaha : " + dataSnapshot);
                        if (dataSnapshot.exists()) {
                            chatMessageArrayList.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Log.d(TAG, "hahaha : " + snapshot.getValue(ChatMessage.class));
                                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                                chatMessageArrayList.add(chatMessage);
                                chatMessagesAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());
                        Log.d(TAG, databaseError.getDetails());
                        Toast.makeText(ChatActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
