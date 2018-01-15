package com.example.wollf.togather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.Calendar;
import java.util.List;

public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Toolbar toolbar = null;
    private AutoCompleteTextView eventTitle = null;
    private AutoCompleteTextView eventDesc = null;
    private Button fromBtn = null;
    private EditText fromDateLabel = null;
    private EditText toDateLabel = null;
    private Button toBtn = null;
    private RadioGroup groupsRadio = null;
    private Button addEventBtn = null;
    private DataBase dataBase = null;
    private SharedPreferences sharedPreferences = null;
    //EditText fromDateText = null, toDateText = null;
    //RadioGroup ll = null;
    //DataBase db = new DataBase();

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
        setVars();
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
        setOnFocusChangeListener(fromDateLabel, tpd, dpd, true);
        setOnFocusChangeListener(toDateLabel, tpd, dpd, false);
        setOnClickListener();
    }

    private void setVars(){
        setToolbar();
        eventTitle = findViewById(R.id.event_title);
        eventDesc = findViewById(R.id.event_desc);
        fromBtn = findViewById(R.id.fromBtn);
        fromDateLabel = findViewById(R.id.fromDateLabel);
        toDateLabel = findViewById(R.id.toDateLabel);
        toBtn = findViewById(R.id.toBtn);
        groupsRadio = findViewById(R.id.groupsRadio);
        addEventBtn = findViewById(R.id.addEventBtn);
        dataBase = new DataBase();
        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setOnFocusChangeListener(EditText text, final TimePickerDialog tpd, final DatePickerDialog dpd, final boolean isFrom){
        text.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus){
                if(hasFocus){
                    tpd.show(getFragmentManager(), (isFrom ? "from" : "to") + "Time");
                    dpd.show(getFragmentManager(), (isFrom ? "from" : "to") + "Date");
                }
            }
        });
    }

    public void setOnClickListener(){
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonID = groupsRadio.getCheckedRadioButtonId();
                if(isInvalidEvent(radioButtonID)){
                    Toast.makeText(getApplicationContext(), "Data is missing try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar calFrom = getFromCalendar(fromDateLabel);
                Calendar calTo = getToCalendar(toDateLabel);
                Group chosenGroup = getGroupFromRadio(radioButtonID);
                Event newEvent = new Event(
                        eventTitle.getText().toString(),
                        eventDesc.getText().toString(),
                        calFrom,
                        calTo,
                        200,
                        300,
                        chosenGroup
                );
                dataBase.addEvent(newEvent);
                startActivity(new Intent(getApplicationContext(), TabbedActivity.class));
            }
        });
        setRadioGroups();
    }

    private Group getGroupFromRadio(int radioButtonID){
        View radioButton = groupsRadio.findViewById(radioButtonID);
        int idx = groupsRadio.indexOfChild(radioButton);
        RadioButton r = (RadioButton)  groupsRadio.getChildAt(idx);
        return (Group) r.getTag();
    }

    private boolean isInvalidEvent(int radioButtonID){
        return  eventTitle.getText().toString().isEmpty() ||
                eventDesc.getText().toString().isEmpty() ||
                fromDateLabel.getText().toString().isEmpty() ||
                toDateLabel.getText().toString().isEmpty() ||
                radioButtonID == -1;
    }

    private void setRadioGroups(){
        User curUser = DataBase.getUser(sharedPreferences.getString("ID",null));
        List<Group> groups = dataBase.getUserGroups(curUser);
        int i = 0;
        for (Group x : groups) {
            RadioButton rb = new RadioButton(this);
            rb.setTag(x);
            rb.setId(i++);
            rb.setText(x.getGroupName());
            groupsRadio.addView(rb);
        }
    }

    private Calendar getFromCalendar(EditText fromDateText){
        Calendar calFrom = Calendar.getInstance();
        String[] fromString = fromDateText.getText().toString().split(" ");
        String[] fromDate = fromString[0].split("/");
        String[] fromTime = fromString[1].split(":");
        calFrom.set(Integer.parseInt(fromDate[0]),
                Integer.parseInt(fromDate[1]),
                Integer.parseInt(fromDate[2]));
        Log.i("fromDate",fromDateText.getText().toString() + "\t" + fromDate[0] + " " + fromDate[1] + " " + fromDate[2]);
        return calFrom;
    }

    private Calendar getToCalendar(EditText toDateText){
        Calendar calTo = Calendar.getInstance();
        String[] toString = toDateText.getText().toString().split(" ");
        String[] toDate = toString[0].split("/");
        String[] toTime = toString[1].split(":");
        calTo.set(Integer.parseInt(toDate[0]),
                Integer.parseInt(toDate[1]),
                Integer.parseInt(toDate[2]));
        return calTo;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getTag().equals("fromDate")){
            fromDateLabel.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year + " ");
        } else {
            toDateLabel.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year + " ");
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if (view.getTag().equals("fromTime")) {
            fromDateLabel.append(hourOfDay + ":" + minute);
        } else {
            toDateLabel.append(hourOfDay + ":" + minute);
        }
    }
}