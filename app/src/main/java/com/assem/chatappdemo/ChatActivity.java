package com.assem.chatappdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

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
    Button sendBtn;

    // OnClicks
    @OnClick(R.id.activity_chat_send_btn)
    void sendMsg() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
    }
}
