package com.assem.chatappdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ChatMessagesHolder> {

    // Constants
    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    // Vars
    private Context context;
    private ArrayList<ChatMessage> chatMessageArrayList;
    private String receiverUserId;
    private String senderUserId;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    public ChatMessagesAdapter(Context context, ArrayList<ChatMessage> chatMessageArrayList, String senderUserId) {
        this.context = context;
        this.chatMessageArrayList = chatMessageArrayList;
        this.senderUserId = senderUserId;
    }

    @NonNull
    @Override
    public ChatMessagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_right, parent, false);
            return new ChatMessagesAdapter.ChatMessagesHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_left, parent, false);
            return new ChatMessagesAdapter.ChatMessagesHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessagesHolder holder, int position) {
        ChatMessage chatMessage = chatMessageArrayList.get(position);
        holder.msg.setText(chatMessage.getMessage());
        if (position == chatMessageArrayList.size() - 1) {
            holder.seen.setVisibility(View.VISIBLE);
            if (chatMessage.isSeen()) {
                holder.seen.setText(R.string.seen);
            } else {
                holder.seen.setText(R.string.delivered);
            }
        } else {
            holder.seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessageArrayList.get(position).getSender().equals(senderUserId))
            return MSG_TYPE_RIGHT;
        else
            return MSG_TYPE_LEFT;
    }

    class ChatMessagesHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_chat_user_img)
        ImageView userImg;
        @BindView(R.id.item_chat_msg)
        TextView msg;
        @BindView(R.id.item_chat_seen)
        TextView seen;

        public ChatMessagesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
