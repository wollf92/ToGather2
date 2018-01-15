package com.example.wollf.togather;

/**
 * Created by wollf on 17-10-2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ShareActionProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class for talking with the specific database connection, to set-up in the future.
 */
public class DataBase {
    String gpps;
    public DataBase(){ gpps = null;
    }
    public DataBase(String groupPayments){
        this.gpps = groupPayments;
    }

    private static List<User> DUMMYDATA = new ArrayList<User>(Arrays.asList(
            new User("Michael de Kaste","user1@mail.com","password1", "NL61RABO0107981491", "31618388915",
                    "d3d1506b-7ddf-4f38-93ae-7ccb872e3609", "af7d203f-03f4-4dfd-8faa-9fb814933490"),
            new User("Matea","user2@mail.com","password2", "INGB1234567809", "385925033629",
                    "6725ce28-38c8-4c4b-9c7d-bc94ce3eda93", "75d588c0-a9f9-4153-94f6-ad699066606c"),
            new User("Michael Zhang","user3@mail.com", "password3","ABN1292932992122", "31633463822",
                    "dc5df003-30d8-4c17-918d-77e448c9766f", "18aa4a81-06e9-40d5-94c3-5620a87a2d63"),
            new User("Pim","user4@mail.com", "password4","NL20RABO0s65404548","31683238852",
                    "7adf40d9-c729-4f68-a4fb-b2d7f69d61fc", "14f52f93-307f-4765-a1a1-db5a72a08d5b")
    ));

    private static ArrayList<Group> Groups = new ArrayList<Group>(Arrays.asList(
            new Group(
                    "Best friends",
                    Arrays.asList(
                            DUMMYDATA.get(0),
                            DUMMYDATA.get(1),
                            DUMMYDATA.get(2),
                            DUMMYDATA.get(3)
//                            getUsers().get(0),
//                            getUsers().get(1)
//                            new User("John","user1@mail.com","password1"),
//                            new User("Adam","user3@mail.com", "password3","ABN129293299122", "0653255325")
                    )
            ),
            new Group(
                    "Classmates",
                    Arrays.asList(
                            DUMMYDATA.get(0),
                            DUMMYDATA.get(1)
                    )
            ),
            new Group(
                    "Classmates2",
                    Arrays.asList(
                            DUMMYDATA.get(1),
                            DUMMYDATA.get(2)
                    )
            ),
            new Group(
                    "Classmates3",
                    Arrays.asList(
                            DUMMYDATA.get(0),
                            DUMMYDATA.get(2)
                    )
            ),
            new Group(
                    "Classmates4",
                    Arrays.asList(
                            new User("John","user1@mail.com","password1"),
                            new User("Karin","user2@mail.com","password2")
                    )
            ),
            new Group(
                    "Classmates5",
                    Arrays.asList(
                            new User("John","user1@mail.com","password1"),
                            new User("Karin","user2@mail.com","password2")
                    )
            )
    ));

    private static ArrayList<Event> Events = new ArrayList<Event>(Arrays.asList(
            new Event("Trip to Las vegas", "This is going to be fun!",
                    getDate(2015, 11, 23),
                    getDate(2016,2,5), 1400, 1840, Groups.get(0)),
            new Event("Movie night", "We are watching Breaking bad...",
                    getDate(2013, 6, 11),
                    getDate(2017, 5, 2), 1640, 2400, Groups.get(1)),
            new Event("Diner", "We need to eat", getDate(2012, 1, 6),
                    getDate(2015, 11, 23), 1800, 2000, Groups.get(2)),
            new Event( "Diner2", "We need to eat", getDate(2012,1,6),
                    getDate(2015,11,23), 1800, 2000, Groups.get(3)),
            new Event( "Diner3", "We need to eat", getDate(2012,1,6),
                    getDate(2015,11,23), 1800, 2000, Groups.get(4))

    ));

    private static Map<Group, Map<User, List<Double>>> GroupPayments = new HashMap<Group, Map<User, List<Double>>>(){{
        Group g = Groups.get(0);
        Map<User, List<Double>> balance1 = new HashMap<>();
        balance1.put(g.getUsers().get(0), new ArrayList<Double>());
        balance1.get(g.getUsers().get(0)).add(10.0);
        balance1.get(g.getUsers().get(0)).add(3.0);
        balance1.get(g.getUsers().get(0)).add(3.0);
        balance1.put(g.getUsers().get(1), new ArrayList<Double>());
        balance1.get(g.getUsers().get(1)).add(2.0);
        balance1.get(g.getUsers().get(1)).add(4.0);
        balance1.get(g.getUsers().get(1)).add(4.0);
        balance1.put(g.getUsers().get(2), new ArrayList<Double>());
        balance1.get(g.getUsers().get(2)).add(4.0);
        balance1.put(g.getUsers().get(3), new ArrayList<Double>());
        balance1.get(g.getUsers().get(3)).add(5.0);
        balance1.get(g.getUsers().get(3)).add(2.0);

        put(g, balance1);
    }};


    private static Map<String, User> DUMMYDATAMAP = toMap(DUMMYDATA);
    private static Map<String, User> toMap(List<User> users){
        Map<String, User> map = new HashMap<>();
        for(User u : users){
            map.put(u.getUniqueID(), u);
        }
        return map;
    }

    /**
     * Dummy data for now
     * @return
     */
    public static User getUser(String id){
        return DUMMYDATAMAP.get(id);
    }
    public static List<User> getUsers(){
        return DUMMYDATA;
    }
    public List<Event> getEvents(){
        return Events;
    }
    public List<Group> getGroups(){
        return Groups;
    }
    public void addUser(User u){
        DUMMYDATA.add(u);
        DUMMYDATAMAP = toMap(DUMMYDATA);
    }
    public void addEvent(Event e) {
        Events.add(e);
    }
    public void addGroup(Group g) {
        Groups.add(g);
    }


    static Calendar getDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal;
    }

    public Group getGroup(String groupID){
        for(Group g : Groups){
            if(g.uniqueID.equals(groupID))
                return g;
        }
        return null;
    }

    public List<Group> getUserGroups(User user){
        List<Group> userGroups = new ArrayList<>();
        for(Group g : getGroups()){
            if(g.getUsers().contains(user))
                userGroups.add(g);
        }
        return userGroups;
    }

    public BalanceCalculator getCalculatorForGroup(Group g){
        BalanceCalculator bc = new BalanceCalculator();
        if(GroupPayments.containsKey(g)) {
            Map<User, List<Double>> payments = GroupPayments.get(g);
            for (Map.Entry<User, List<Double>> e : payments.entrySet()) {
                for (Double d : e.getValue()) {
                    bc.addBalance(e.getKey(), d);
                }
            }
        }
        for(User u : g.getUsers()){
            bc.addUser(u);
        }
        Log.i("contextualv1", gpps == null ? "any" : gpps);
        if(gpps != null){
            String groupPaymentsMore = gpps;
            String[] payments = groupPaymentsMore.split(",");
            for(String gp : payments) {
                String[] groupPayments = gp.split(";");
                Log.i("grouPayments", Arrays.toString(groupPayments));
                if (groupPayments.length == 3) {
                    Group group = getGroup(groupPayments[0]);
                    if (group != null && group.uniqueID == g.uniqueID) {
                        User u = getUser(groupPayments[1]);
                        bc.addBalance(u, Double.parseDouble(groupPayments[2]));
                    }
                }
            }
        }

        return bc;
    }
    public List<Event> getEventsForUser(User u){
        List<Event> toReturn = new ArrayList<>();
        for(Event e : Events){
            if(e.getUsersFromGroup().contains(u))
                toReturn.add(e);
        }
        return toReturn;
    }
}
