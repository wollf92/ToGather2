package com.example.wollf.togather;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wollf.togather.Event;
import com.example.wollf.togather.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends ArrayAdapter<Event> {
    private final Context context;
    private final List<Event> itemsArrayList;

    public EventAdapter(Context context, List<Event> itemsArrayList) {

        super(context, R.layout.event_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
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
        inflater.inflate(R.menu.event_row_menu, popup.getMenu());

        // Edit btn
        popup.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(context, itemsArrayList.get(pos).getUniqueID(), Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
        });

        popup.show();
    }
}
