package com.example.wollf.togather;

/**
 * Created by wollf on 17-10-2017.
 */

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class for talking with the specific database connection, to set-up in the future.
 */
public class DataBase {

    private static List<Event> Events = Arrays.asList(
            new Event("Trip to Las vegas", "This is going to be fun!", new Date(343543534),
                    new Date(34932423), 1400, 1840
            ),
            new Event("Movie night", "We are watching Breaking bad...", new Date(348323245),
                    new Date(39421520), 1640, 2400),
            new Event("Diner", "We need to eat", new Date(348323245),
                    new Date(39421520), 1800, 2000)
    );

    private static List<User> DUMMYDATA = Arrays.asList(
        new User("John","user1@mail.com","password1"),
        new User("Karin","user2@mail.com","password2")
            //new User("Kate","user3@mail.com", "password3","NL20RABO06","87104545",Calendar.getInstance())
            //new User("Kate","user3@mail.com", "password3","NL20RABO06","87104545",Calendar.getInstance())
            //new User("Kate","user3@mail.com", "password3","NL20RABO06","87104545",Calendar.getInstance())
    );
    /**
     * Dummy data for now
     * @return
     */
    public List<User> getUsers(){
        return DUMMYDATA;
    }
    public List<Event> getEvents(){
        return Events;
    }
    public void addUser(User u){
        DUMMYDATA.add(u);
    }
    public void addEvent(Event e){
        Events.add(e);
    }
}
