package com.example.wollf.togather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.wollf.togather.Event;
import com.example.wollf.togather.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    private final Context context;
    private final List<Event> itemsArrayList;

    public EventAdapter(Context context, List<Event> itemsArrayList) {

        super(context, R.layout.event_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.event_row, parent, false);

        TextView labelView = (TextView) rowView.findViewById(R.id.title);
        TextView valueView = (TextView) rowView.findViewById(R.id.desc);

        labelView.setText(itemsArrayList.get(position).getTitle());
        valueView.setText(itemsArrayList.get(position).getDescription());

        return rowView;
    }
}
