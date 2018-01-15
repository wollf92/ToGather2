package com.example.wollf.togather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EventPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DataBase db = new DataBase();
        final SharedPreferences sharedPreferences;
        sharedPreferences = this.getSharedPreferences("user_data",Context.MODE_PRIVATE);
        String s = getIntent().getStringExtra("EVENT_ID");
        final Event e = db.getEvent(s);

        setTitle(e.getTitle());

        TextView title = findViewById(R.id.event_name_show);
        TextView desc = findViewById(R.id.event_desc_show);
        TextView starts = findViewById(R.id.event_from_show);
        TextView ends = findViewById(R.id.event_to_show);
        Button balance_btn = findViewById(R.id.add_balance_btn);

        String startDate = String.format("%1$td.%1$tm.%1$tY", e.getStartDate());
        String endDate = String.format("%1$td.%1$tm.%1$tY", e.getEndDate());

        String start_time = Integer.toString(e.getStartTime());
        start_time = new StringBuffer(start_time).insert(2, ":").toString();

        String end_time = Integer.toString(e.getEndTime());
        end_time = new StringBuffer(end_time).insert(2, ":").toString();

        title.setText(e.getTitle());
        desc.setText(e.getDescription());
        starts.setText(startDate + " " + start_time);
        ends.setText(endDate + " " + end_time);

        balance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("eventID", e.getUniqueID());
                editor.putString("groupID", e.getGroup().uniqueID);
                editor.apply();
                Intent i = new Intent(EventPage.this, AddEventBalance.class);
                EventPage.this.startActivity(i);
            }
        });

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout members_view = findViewById(R.id.event_member_names);

        String res = "";

        for (final User u : e.getGroup().getUsers()){
            View group_member = inflater.inflate(R.layout.group_member, null, false);
            TextView member_name = group_member.findViewById(R.id.member_name);
            ImageButton member_send = group_member.findViewById(R.id.member_send);

            member_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openWhatsApp(u.getPhone().substring(1), u.getName());
                }
            });
            member_name.setText(u.getName());
            members_view.addView(group_member);
        }
    }

    private void openWhatsApp(String number, String name) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello " + name);
        sendIntent.putExtra("jid", number + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        this.startActivity(sendIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
