package com.example.wollf.togather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.util.Log;
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

import static android.content.Context.MODE_PRIVATE;

public class GroupAdapter extends ArrayAdapter<Group> {
    private final Context context;
    private final List<Group> itemsArrayList;
    private final SharedPreferences.Editor editor;

    public GroupAdapter(Context context, List<Group> itemsArrayList) {

        super(context, R.layout.group_row, itemsArrayList);
        this.editor = context.getSharedPreferences("user_data", MODE_PRIVATE).edit();
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.group_row, parent, false);
        TextView title = rowView.findViewById(R.id.title);
        TextView desc = rowView.findViewById(R.id.desc);
        TextView date = rowView.findViewById(R.id.date);
        Button button = (Button)rowView.findViewById(R.id.balance_button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i("groupIDSET", itemsArrayList.get(position).uniqueID);
                editor.putString("groupID", itemsArrayList.get(position).uniqueID);
                editor.commit();
                Intent i = new Intent(getContext(), GroupBalance.class);
                getContext().startActivity(i);
            }
        });
        ImageButton more = rowView.findViewById(R.id.more_event);

        String users = "";
        for (User a : itemsArrayList.get(position).getUsers()) {
            users += " • " + a.getName() + "\n";
        }

        title.setText(itemsArrayList.get(position).getGroupName());
        desc.setText(users);

        return rowView;
    }

    /*
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
    */
}
