package com.example.wollf.togather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    private final Context context;
    private final List<Event> itemsArrayList;
    private final SharedPreferences sharedPreferences;

    public EventAdapter(Context context, List<Event> itemsArrayList) {

        super(context, R.layout.event_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.sharedPreferences = context.getSharedPreferences("user_data",Context.MODE_PRIVATE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.event_row, parent, false);
        TextView title = rowView.findViewById(R.id.title);
        TextView desc = rowView.findViewById(R.id.desc);
        TextView date = rowView.findViewById(R.id.date);
        TextView groupName = rowView.findViewById(R.id.groupName);

        ImageButton more = rowView.findViewById(R.id.more_event);

        title.setText(itemsArrayList.get(position).getTitle());
        desc.setText(itemsArrayList.get(position).getDescription());

        String startDate = String.format("%1$te %1$tb", itemsArrayList.get(position).getStartDate());
        String endDate = String.format("%1$te %1$tb", itemsArrayList.get(position).getEndDate());

        date.setText(startDate + " - " + endDate);

        groupName.setText(itemsArrayList.get(position).group.groupName);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, position);
            }
        });

        return rowView;
    }

    public void showPopup(View v, final int pos) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.getMenu().add(0,0,0,"Add Balance");
        popup.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //ADD EVENT TO SHARED
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putString("eventID", itemsArrayList.get(pos).uniqueID);
                e.putString("groupID", itemsArrayList.get(pos).getGroup().uniqueID);
                e.commit();
                Intent i = new Intent(getContext(), AddEventBalance.class);
                getContext().startActivity(i);
                return true;
            }
        });
        inflater.inflate(R.menu.event_row_menu, popup.getMenu());

        popup.show();
    }
}
