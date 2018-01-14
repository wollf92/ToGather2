package com.example.wollf.togather;

/**
 * Created by wollf on 17-10-2017.
 */

import android.content.Context;

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

    private static List<User> DUMMYDATA = Arrays.asList(
            new User("John D","user1@mail.com","password1", "SNSB1234567890", " 0612340128"),
            new User("Karin S","user2@mail.com","password2", "INGB1234567809", "0625412587"),
            new User("Adam A","user3@mail.com", "password3","ABN129293299122", "0653255325"),
            new User("Kate","user3@mail.com", "password3","NL20RABO06","87104545")
            //new User("Kate","user3@mail.com", "password3","NL20RABO06","87104545",Calendar.getInstance())
            //new User("Kate","user3@mail.com", "password3","NL20RABO06","87104545",Calendar.getInstance())
    );

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
        balance1.put(g.getUsers().get(1), new ArrayList<Double>());
        balance1.get(g.getUsers().get(1)).add(2.0);
        balance1.get(g.getUsers().get(1)).add(4.0);
        balance1.put(g.getUsers().get(2),new ArrayList<Double>());
        balance1.get(g.getUsers().get(2)).add(3.0);
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
    public static User GetUser(String id){
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
        return bc;
    }
}
