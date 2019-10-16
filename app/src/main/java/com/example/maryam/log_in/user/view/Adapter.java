package com.example.maryam.log_in.user.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maryam.log_in.R;
import com.example.maryam.log_in.dto.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maryam on 10/16/19.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewUserHolder> {
    private List<User> usersList;
    private OnUserClickListener onUserClickListener;

    public interface OnUserClickListener {
        void onUserClick(int position);
    }

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    public static class ViewUserHolder extends RecyclerView.ViewHolder {
        private TextView usernameFieldTable;
        private TextView firstNameFieldTable;


        public ViewUserHolder(View itemView, final OnUserClickListener onUserClickListener) {
            super(itemView);
            usernameFieldTable = itemView.findViewById(R.id.usernameTxt);
            firstNameFieldTable = itemView.findViewById(R.id.firstNameTxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onUserClickListener!= null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onUserClickListener.onUserClick(position);
                        }
                    }
                }
            });
        }
    }

    public Adapter(List<User> userList) {
        this.usersList = userList;
    }

    @Override
    public ViewUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        ViewUserHolder view = new ViewUserHolder(v, onUserClickListener);
        return view;
    }

    @Override
    public void onBindViewHolder(ViewUserHolder holder, int position) {
        User user = usersList.get(position);
        holder.usernameFieldTable.setText(user.getUsername());
        holder.firstNameFieldTable.setText(user.getFirstName());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

}
