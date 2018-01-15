package com.example.wollf.togather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
        String s = getIntent().getStringExtra("EVENT_ID");
        Event e = db.getEvent(s);

        setTitle(e.getTitle());

        TextView title = findViewById(R.id.event_name_show);
        TextView desc = findViewById(R.id.event_desc_show);
        TextView starts = findViewById(R.id.event_from_show);
        TextView ends = findViewById(R.id.event_to_show);
        TextView group = findViewById(R.id.event_group_show);

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
        group.setText(e.getGroup().getGroupName());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
