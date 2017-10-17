package com.example.wollf.togather;

/**
 * Created by wollf on 17-10-2017.
 */

import java.util.Arrays;
import java.util.List;

/**
 * Class for talking with the specific database connection, to set-up in the future.
 */
public class DataBase {
    private static List<User> DUMMYDATA = Arrays.asList(new User("user1@mail.com","password1"), new User("user2@mail.com","password2"), new User("user3@mail.com", "password3"));
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
}
