package com.assem.chatappdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {

    private Context context;
    private ArrayList<User> usersArrayList;

    public UsersAdapter(Context context, ArrayList<User> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        final User user = usersArrayList.get(position);
        holder.userEmail.setText(user.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("receiver_user_id", user.getId());
                intent.putExtra("sender_user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_user_user_img)
        ImageView userImg;
        @BindView(R.id.item_user_email)
        TextView userEmail;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
