package com.example.wollf.togather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wollf on 11-1-2018.
 */

class CalculatedBalanceAdapter extends ArrayAdapter<Transaction> {
    private final Context context;
    private final List<Transaction> itemsArrayList;
    private final SharedPreferences sp;
    private View rowView;
    private TextView from;
    private TextView total;
    private Button btn;

    public CalculatedBalanceAdapter(Context context, List<Transaction> itemsArrayList) {

        super(context, R.layout.user_balance_row, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.sp = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Context context = parent.getContext();
        setVars(inflater, parent);

        DataBase db = new DataBase(sp.getString("groupBalance",null));
        final User curUser = DataBase.getUser(sp.getString("ID",null));
        final Transaction transaction = itemsArrayList.get(position);
        from.setText(transaction.getFrom());
        total.setText("€"+Double.toString(transaction.getAmount()));
        if(curUser.getName().equals(transaction.getFrom()))
            rowView.findViewById(R.id.calculated_balance_pay).setVisibility(View.VISIBLE);
        final Double amount = ((transaction.getAmount() - transaction.getAmount() % 1) * 100) + transaction.getAmount() % 1 * 100;
        setOnClickListener(curUser, amount, transaction);

        return rowView;
    }

    private void setOnClickListener(final User curUser, final Double amount, final Transaction transaction){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tikkie tikkie = new Tikkie(context);
                JSONObject response = tikkie.get_payment_request(
                        curUser.getTikkie_user_token(),
                        curUser.getTikkie_iban_token(),
                        amount.intValue()
                );
                String link = getLink(response,"paymentRequestUrl");
                setIntentInfo(transaction, link);
            }
        });
    }

    private String getLink(JSONObject response, String getString){
        String link = null;
        try {
            link = response.getString(getString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return link;
    }

    private void setIntentInfo(Transaction transaction, String link){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey, I just calculated out mutual balance and you owe me €" + transaction.getAmount() +
                        ". Would you like to pay me? You can do so using this Tikkie below.\n\n" + link);
        sendIntent.setType("text/plain");
        if (isAppInstalled(context))
            sendIntent.setPackage("com.whatsapp"); // If you dont have whatsapp there is crash
        context.startActivity(sendIntent);
    }


    private void setVars(LayoutInflater inflater, ViewGroup parent){
        rowView = inflater.inflate(R.layout.calculated_balance_row, parent, false);
        from = rowView.findViewById(R.id.calculated_balance_from);
        total = rowView.findViewById(R.id.calculated_balance_total);
        btn = rowView.findViewById(R.id.calculated_balance_pay);
    }

    private static boolean isAppInstalled(Context context) {
        try {
            context.getPackageManager().getApplicationInfo("com.whatsapp", 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
