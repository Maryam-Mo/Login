package com.example.maryam.log_in.item.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maryam.log_in.R;
import com.example.maryam.log_in.dto.Item;

import java.util.List;

/**
 * Created by maryam on 9/9/19.
 */

public class CustomItemListAdapter extends ArrayAdapter {
    private final Activity context;
    List<Item> itemsList;
    private TextView nameFieldTable;
    private TextView quantityFieldTable;
    private TextView priceFieldTable;

    public CustomItemListAdapter(Activity context, List<Item> itemList) {
        super(context, R.layout.listview_item_row, itemList);
        itemsList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_item_row, null,true);
        nameFieldTable = rowView.findViewById(R.id.nameTxt);
        priceFieldTable = rowView.findViewById(R.id.priceTxt);
        quantityFieldTable = rowView.findViewById(R.id.quantityTxt);
        Item receivedItem = (Item) getItem(position);
        nameFieldTable.setText(receivedItem.getName());
        priceFieldTable.setText(String.valueOf(receivedItem.getPrice()));
        quantityFieldTable.setText(String.valueOf(receivedItem.getQuantity()));
        return rowView;
    }


}
