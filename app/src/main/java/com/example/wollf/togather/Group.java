package com.example.wollf.togather;

import java.util.List;

/**
 * Created by wollf on 17-10-2017.
 */

public class Group {
    String groupName;
    List<User> users;
    public void addUser(User u){
        users.add(u);
    }

}
