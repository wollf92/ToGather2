package com.example.wollf.togather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wollf.togather.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

class GroupAdapter extends ArrayAdapter<Group> {
    private final Context context;
    private final List<Group> itemsArrayList;
    private final SharedPreferences.Editor editor;

    private View rowView;
    private TextView title;
    private Button button;
    private LinearLayout members_view;

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
        setVars(inflater, parent);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i("groupIDSET", itemsArrayList.get(position).uniqueID);
                editor.putString("groupID", itemsArrayList.get(position).uniqueID);
                editor.commit();
                Intent i = new Intent(getContext(), GroupBalance.class);
                getContext().startActivity(i);
            }
        });

        addViewsForAll(inflater, parent, position);
        title.setText(itemsArrayList.get(position).getGroupName());

        return rowView;
    }

    private void addViewsForAll(LayoutInflater inflater, ViewGroup parent, int position) {
        for (final User a : itemsArrayList.get(position).getUsers()) {
            View group_member = inflater.inflate(R.layout.group_member, parent, false);
            TextView member_name = group_member.findViewById(R.id.member_name);
            ImageButton member_send = group_member.findViewById(R.id.member_send);

            member_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openWhatsApp(a.getPhone().substring(1), a.getName());
                }
            });
            member_name.setText(a.getName());
            members_view.addView(group_member);
        }
    }

    private void setVars(LayoutInflater inflater, ViewGroup parent) {
        rowView = inflater.inflate(R.layout.group_row, parent, false);
        title = rowView.findViewById(R.id.title);
        button = rowView.findViewById(R.id.balance_button);
        members_view = rowView.findViewById(R.id.group_members);
    }

    private void openWhatsApp(String number, String name) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello " + name);
        sendIntent.putExtra("jid", number + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        context.startActivity(sendIntent);
    }
}
