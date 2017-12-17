package com.example.wollf.togather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wollf on 17-12-2017.
 */

public class GroupBalanceAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> itemsArrayList;

    public GroupBalanceAdapter(Context context, List<User> itemsArrayList) {

        super(context, R.layout.user_balance_row, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.user_balance_row, parent, false);
        TextView user = (TextView) rowView.findViewById(R.id.balanceUserName);
        TextView balance = (TextView) rowView.findViewById(R.id.balanceUserBalance);

        String currentGroupID = "test"; //to be replaced by setting group balance;
        user.setText(itemsArrayList.get(position).getName());
        balance.setText(itemsArrayList.get(position).getGroupById(currentGroupID).getBalanceFor(itemsArrayList.get(position).getUniqueID()).toString());
        return rowView;
    }
}
