package com.example.wollf.togather;

/**
 * Created by wollf on 17-10-2017.
 */

import android.content.Context;

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

    private static List<Event> Events = Arrays.asList(
            new Event("Trip to Las vegas", "This is going to be fun!", getDate(2015, 11, 23),
                    getDate(2016,2,5), 1400, 1840
            ),
            new Event("Movie night", "We are watching Breaking bad...", getDate(2013, 6, 11),
                    getDate(2017, 5, 2), 1640, 2400),
            new Event("Diner", "We need to eat", getDate(2012, 1, 6),
                    getDate(2015, 11, 23), 1800, 2000)
    );

    private static List<User> DUMMYDATA = Arrays.asList(
            new User("John","user1@mail.com","password1"),
            new User("Karin","user2@mail.com","password2"),
            new User("Adam","user3@mail.com", "password3", "ABN129293299122", "0653255325")
            //new User("Kate","user3@mail.com", "password3","NL20RABO06","87104545",Calendar.getInstance())
            //new User("Kate","user3@mail.com", "password3","NL20RABO06","87104545",Calendar.getInstance())
            //new User("Kate","user3@mail.com", "password3","NL20RABO06","87104545",Calendar.getInstance())
    );
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
    public void addUser(User u){
        DUMMYDATA.add(u);
    }
    public void addEvent(Event e) {
        Events.add(e);
    }


    static Calendar getDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal;
    }
}
