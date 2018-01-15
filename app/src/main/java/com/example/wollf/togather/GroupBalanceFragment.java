package com.example.wollf.togather;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class GroupBalanceFragment extends Fragment {

    public GroupBalanceFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_group_balance, container, false);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String name = prefs.getString("name", null);
        String email = prefs.getString("email", null);
        String groupID = prefs.getString("groupID", null);

        TextView groupName = rootView.findViewById(R.id.groupNameBalance);
        ListView listView = rootView.findViewById(R.id.groupBalanceList);
        ListView listView2 = rootView.findViewById(R.id.calculatedBalanceList);


        Log.i("groupIDGET", groupID);
        DataBase db = new DataBase(prefs.getString("groupPayments",null));
        List<User> listOfUsers = db.getGroup(groupID).getUsers(); // Do async task as it may take long time with real DB
        GroupBalanceAdapter adapter = new GroupBalanceAdapter(getContext(), listOfUsers);
        BalanceCalculator bc = db.getCalculatorForGroup(db.getGroup(groupID));
        List<Transaction> transaction = getMyTransactions(bc.calculateTransaction(), name);
        CalculatedBalanceAdapter adapter2 = new CalculatedBalanceAdapter(getContext(), transaction);

        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
        groupName.setText(db.getGroup(groupID).getGroupName());

        return rootView;
    }

    private List<Transaction> getMyTransactions(List<Transaction> all, String me){
        List<Transaction> toReturn = new ArrayList<>();
        for(Transaction t : all){
            if(t.getTo().equals(me))
                toReturn.add(t);
        }
        return toReturn;
    }
}
