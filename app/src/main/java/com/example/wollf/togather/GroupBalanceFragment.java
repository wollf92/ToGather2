package com.example.wollf.togather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class GroupBalanceFragment extends Fragment {

    public GroupBalanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_balance, container, false);

        ListView listView = rootView.findViewById(R.id.groupBalanceList);
        ListView listView2 = rootView.findViewById(R.id.calculatedBalanceList);
        Button tikkieButton = rootView.findViewById(R.id.calculate_tikkies_button);

//        tikkieButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), CalculatedBalance.class);
//                getContext().startActivity(i);
//            }
//        });

        SharedPreferences prefs = this.getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String name = prefs.getString("name", null);
        String email = prefs.getString("email", null);
        String groupID = prefs.getString("groupID", null);

        Log.i("groupIDGET", groupID);
        DataBase db = new DataBase();
        List<User> listOfUsers = db.getGroup(groupID).getUsers(); // Do async task as it may take long time with real DB
        GroupBalanceAdapter adapter = new GroupBalanceAdapter(getContext(), listOfUsers);
        BalanceCalculator bc = db.getCalculatorForGroup(db.getGroup(groupID));
        CalculatedBalanceAdapter adapter2 = new CalculatedBalanceAdapter(getContext(), bc.calculateTransaction());

        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
        return rootView;
    }
}
