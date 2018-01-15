package com.example.wollf.togather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.Calendar;
import java.util.List;

public class AddEvent extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText fromDateText = null;
    private EditText toDateText = null;
    RadioGroup ll = null;
    private DataBase db = new DataBase();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final EditText titleInput = findViewById(R.id.event_title);
        final EditText descInput = findViewById(R.id.event_desc);
        final Button fromDateBtn = findViewById(R.id.fromBtn);
        Button toDateBtn = findViewById(R.id.toBtn);
        Button addEventBtn = findViewById(R.id.addEventBtn);
        toDateText = findViewById(R.id.toDateLabel);
        fromDateText = findViewById(R.id.fromDateLabel);
        final RadioGroup ll = findViewById(R.id.groupsRadio);


        Calendar now = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        final TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true
        );

        fromDateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    tpd.show(getFragmentManager(), "fromTime");
                    dpd.show(getFragmentManager(), "fromDate");
                }
            }
        });

        toDateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    tpd.show(getFragmentManager(), "toTime");
                    dpd.show(getFragmentManager(), "toDate");
                }
            }
        });

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioButtonID = ll.getCheckedRadioButtonId();

                if(titleInput.getText().toString().isEmpty() ||
                        descInput.getText().toString().isEmpty() ||
                        fromDateText.getText().toString().isEmpty() ||
                        toDateText.getText().toString().isEmpty() ||
                        radioButtonID == -1){
                    // Data is missing
                    Toast.makeText(getApplicationContext(), "Data is missing try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar calFrom = Calendar.getInstance();
                Calendar calTo = Calendar.getInstance();
                String[] fromString = fromDateText.getText().toString().split(" ");
                String[] fromDate = fromString[0].split("/");
                String[] fromTime = fromString[1].split(":");
                calFrom.set(Integer.parseInt(fromDate[0]),
                        Integer.parseInt(fromDate[1]),
                        Integer.parseInt(fromDate[2]));

                String[] toString = toDateText.getText().toString().split(" ");
                String[] toDate = toString[0].split("/");
                String[] toTime = toString[1].split(":");
                calTo.set(Integer.parseInt(toDate[0]),
                        Integer.parseInt(toDate[1]),
                        Integer.parseInt(toDate[2]));

                View radioButton = ll.findViewById(radioButtonID);
                int idx = ll.indexOfChild(radioButton);
                RadioButton r = (RadioButton)  ll.getChildAt(idx);
                Group chosenGroup = (Group) r.getTag();

                Event newEvent = new Event(
                        titleInput.getText().toString(),
                        descInput.getText().toString(),
                        calFrom,
                        calTo,
                        200,
                        300,
                        chosenGroup
                );
                db.addEvent(newEvent);
                Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(i);
            }
        });

        List<Group> groups = db.getGroups();
        int i = 0;
        for (Group x : groups) {
            RadioButton rb = new RadioButton(this);
            rb.setTag(x);
            rb.setId(i++);
            rb.setText(x.getGroupName());
            ll.addView(rb);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getTag().equals("fromDate")){
            fromDateText.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year + " ");
        } else {
            toDateText.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year + " ");
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if (view.getTag().equals("fromTime")) {
            fromDateText.append(hourOfDay + ":" + minute);
        } else {
            toDateText.append(hourOfDay + ":" + minute);
        }
    }
}