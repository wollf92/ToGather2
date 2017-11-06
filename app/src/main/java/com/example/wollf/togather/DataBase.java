package com.example.wollf.togather;

/**
 * Created by wollf on 17-10-2017.
 */

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for talking with the specific database connection, to set-up in the future.
 */
public class DataBase {

    private static List<User> DUMMYDATA = Arrays.asList(
        new User("John","user1@mail.com","password1"),
        new User("Karin","user2@mail.com","password2")
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
    public List<User> getUsers(){
        return DUMMYDATA;
    }
    public void addUser(User u){
        DUMMYDATA.add(u);
    }
    public User GetUser(String id){
        return DUMMYDATAMAP.get(id);
    }
}
