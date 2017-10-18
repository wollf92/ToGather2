package com.example.wollf.togather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class Groups extends AppCompatActivity {
    private List<Group> groupsToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
    }
}
