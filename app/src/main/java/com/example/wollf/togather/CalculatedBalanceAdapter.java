package com.example.wollf.togather;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wollf on 11-1-2018.
 */

public class CalculatedBalanceAdapter extends ArrayAdapter<Transaction> {
    private final Context context;
    private final List<Transaction> itemsArrayList;
    private final SharedPreferences sp;

    public CalculatedBalanceAdapter(Context context, List<Transaction> itemsArrayList) {

        super(context, R.layout.user_balance_row, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.sp = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.calculated_balance_row, parent, false);
        TextView from = rowView.findViewById(R.id.calculated_balance_from);
        TextView total = rowView.findViewById(R.id.calculated_balance_total);
        TextView to = rowView.findViewById(R.id.calculated_balance_to);
        DataBase db = new DataBase();
        User curUser = db.GetUser(sp.getString("ID",null));
        Transaction transaction = itemsArrayList.get(position);
        from.setText(transaction.getFrom());
        total.setText(Double.toString(transaction.getAmount()));
        to.setText(transaction.getTo());
        if(curUser.getName().equals(transaction.getFrom()))
            rowView.findViewById(R.id.calculated_balance_pay).setVisibility(View.VISIBLE);

        return rowView;
    }
}
