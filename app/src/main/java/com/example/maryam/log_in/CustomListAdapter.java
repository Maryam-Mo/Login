package com.example.maryam.log_in;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maryam.log_in.dto.User;

import java.util.List;

/**
 * Created by maryam on 8/23/19.
 */

public class CustomListAdapter extends ArrayAdapter {
    private final Activity context;
    List<User> usersList;
    private TextView usernameFieldTable;
    private TextView firstNameFieldTable;
    private TextView lastNameFieldTable;

    public CustomListAdapter(Activity context, List<User> userList) {
        super(context, R.layout.listview_row, userList);
        usersList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);
        usernameFieldTable = rowView.findViewById(R.id.usernameTxt);
        firstNameFieldTable = rowView.findViewById(R.id.firstNameTxt);
        lastNameFieldTable = rowView.findViewById(R.id.lastNameTxt);
        User user = (User) getItem(position);
        usernameFieldTable.setText(user.getUsername());
        firstNameFieldTable.setText(user.getFirstName());
        lastNameFieldTable.setText(user.getLastName());
        return rowView;
    }
}
