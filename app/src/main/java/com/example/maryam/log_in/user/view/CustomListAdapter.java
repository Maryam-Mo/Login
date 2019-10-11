package com.example.maryam.log_in.user.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.maryam.log_in.R;
import com.example.maryam.log_in.dto.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maryam on 8/23/19.
 */

public class CustomListAdapter extends ArrayAdapter implements Filterable {
    private final Activity context;
    List<User> usersList;
    private TextView usernameFieldTable;
    private TextView firstNameFieldTable;
    private TextView lastNameFieldTable;
    List<String> arrayList;
    List<String> mOriginalValues;

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

//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//
//            @SuppressWarnings("unchecked")
//            @Override
//            protected void publishResults(CharSequence constraint,FilterResults results) {
//
//                arrayList = (List<String>) results.values; // has the filtered values
//                notifyDataSetChanged();  // notifies the data with new filtered values
//            }
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
//                List<String> FilteredArrList = new ArrayList<String>();
//
//                if (mOriginalValues == null) {
//                    mOriginalValues = new ArrayList<String>(arrayList); // saves the original data in mOriginalValues
//                }
//
//                /********
//                 *
//                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
//                 *  else does the Filtering and returns FilteredArrList(Filtered)
//                 *
//                 ********/
//                if (constraint == null || constraint.length() == 0) {
//
//                    // set the Original result to return
//                    results.count = mOriginalValues.size();
//                    results.values = mOriginalValues;
//                } else {
//                    constraint = constraint.toString().toLowerCase();
//                    for (int i = 0; i < mOriginalValues.size(); i++) {
//                        String data = mOriginalValues.get(i);
//                        if (data.toLowerCase().contains(constraint.toString())) {
//                            FilteredArrList.add(data);
//                        }
//                    }
//                    // set the Filtered result to return
//                    results.count = FilteredArrList.size();
//                    results.values = FilteredArrList;
//                }
//                return results;
//            }
//        };
//        return filter;
//    }
}
