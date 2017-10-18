package com.example.wollf.togather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class Events extends AppCompatActivity {
    private List<Event> eventsToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
    }
}
