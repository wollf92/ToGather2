package com.example.wollf.togather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class AddGroup extends AppCompatActivity {

    DataBase db = new DataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayout ll = findViewById(R.id.checkbox_list);
        Button addBtn = findViewById(R.id.addGroupBtn);
        final EditText groupNameText = findViewById(R.id.group_name);

        List<User> users = db.getUsers();

        final ArrayList<CheckBox> cbList = new ArrayList<CheckBox>();

        for (User x : users) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setTag(x);
            cb.setText(Html.fromHtml("<big>" + x.getName() + "</big>"));
            ll.addView(cb);
            cbList.add(cb);
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> usersInGroup = new ArrayList<>();
                for (CheckBox cbx : cbList) {
                    if (cbx.isChecked()) {
                        User x = (User) cbx.getTag();
                        usersInGroup.add(x);
                    }
                }
                Group newGroup = new Group(groupNameText.getText().toString(), usersInGroup);
                db.addGroup(newGroup);
                Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                i.putExtra("tab", 1);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}