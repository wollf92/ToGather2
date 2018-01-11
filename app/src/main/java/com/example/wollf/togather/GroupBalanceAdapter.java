package com.example.wollf.togather;

import android.content.Context;
import android.content.SharedPreferences;
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
    private final SharedPreferences sp;

    public GroupBalanceAdapter(Context context, List<User> itemsArrayList) {

        super(context, R.layout.user_balance_row, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.sp = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.user_balance_row, parent, false);
        TextView user = rowView.findViewById(R.id.balanceUserName);
        TextView balance = rowView.findViewById(R.id.balanceUserBalance);
        DataBase db = new DataBase();
        User curUser = itemsArrayList.get(position);
        Group curGroup = db.getGroup(sp.getString("groupID", null));
        double total = db.getCalculatorForGroup(curGroup).getUserTotalPayments(curUser);
        user.setText(curUser.getName());
        balance.setText(Double.toString(total));
        return rowView;
    }
}
