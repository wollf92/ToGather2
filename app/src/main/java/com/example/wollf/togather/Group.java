package com.example.wollf.togather;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wollf on 17-10-2017.
 */

public class Group {

    String uniqueID;
    String groupName;
    private List<User> users;
    private Map<String, Double> balances;

    Group(String name, List<User> users){
        this.groupName = name;
        this.users = users;
        this.uniqueID = UUID.randomUUID().toString();
    }

    public void addUser(User u){
        users.add(u);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return groupName.equals(group.groupName) && users.equals(group.users);

    }

    @Override
    public int hashCode() {
        int result = groupName.hashCode();
        result = 31 * result + users.hashCode();
        return result;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Double getBalanceFor(String userID){
        return balances.get(userID);
    }
}
