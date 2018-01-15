package com.example.wollf.togather;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddEventBalance extends AppCompatActivity {
    TextView amount;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_balance);
        amount = findViewById(R.id.add_event_balance_amount);
        Button submitButton = findViewById(R.id.submit_event_balance);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupString = sharedPreferences.getString("groupID","");
                String userString = sharedPreferences.getString("ID","");
                String whatsThere = sharedPreferences.getString("groupPayments",null);
                Log.i("onbuttonclick: ", groupString + " " + userString + " " + whatsThere);
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putString("groupPayments", whatsThere + (whatsThere == null ? "" : ",") + groupString+";"+userString+";"+amount.getText());
                e.commit();
                Log.i("commited:", sharedPreferences.getString("groupPayments","empty"));
                finish();
            }
        });
    }
}
