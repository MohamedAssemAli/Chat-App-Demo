package com.assem.chatappdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatHolder> {

    private Context context;
    private ArrayList<Chat> chatsArrayList;

    public ChatsAdapter(Context context, ArrayList<Chat> chatsArrayList) {
        this.context = context;
        this.chatsArrayList = chatsArrayList;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
        return new ChatsAdapter.ChatHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        final Chat chat = chatsArrayList.get(position);
        holder.userEmail.setText(chat.getEmail());

    }

    @Override
    public int getItemCount() {
        return chatsArrayList.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_chat_user_img)
        ImageView userImg;
        @BindView(R.id.item_chat_email)
        TextView userEmail;
        @BindView(R.id.item_chat_last_msg)
        TextView lastMsg;
        @BindView(R.id.item_chat_date)
        TextView date;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
