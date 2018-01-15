package com.example.wollf.togather;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by wollf on 17-10-2017.
 */

class Event {

    private int typeOfEvent;
    private String title;
    private String description;
    private Calendar startDate;
    private int startTime;
    private Calendar endDate;
    private int endTime;
    Group group;
    String uniqueID;

    private Group from;
    private List<User> usersFromGroup;
    //String title, String desc, Calendar startDate, Calendar endDate, int startTime, int endTime, Group group
    public Event(Object...params){
        this.title = (String)params[0];
        this.description = (String)params[1];
        this.startDate = (Calendar)params[2];
        this.endDate = (Calendar)params[3];
        this.startTime = (Integer)params[4];
        this.endTime = (Integer)params[5];
        this.uniqueID = UUID.randomUUID().toString();
        this.group = (Group)params[6];
        this.usersFromGroup = group.getUsers();
    }

    public Group getFrom() {
        return from;
    }

    public void setFrom(Group from) {
        this.from = from;
    }

    public List<User> getUsersFromGroup() {
        return usersFromGroup;
    }

    public void setUsersFromGroup(List<User> usersFromGroup) {
        this.usersFromGroup = usersFromGroup;
    }

    public Group getGroup (){return group;}

    public int getTypeOfEvent() {
        return typeOfEvent;
    }

    public void setTypeOfEvent(int typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getUniqueID() { return uniqueID; }

    public String getDescription(){
        return description;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (typeOfEvent != event.typeOfEvent) return false;
        if (startTime != event.startTime) return false;
        if (endTime != event.endTime) return false;
        if (!from.equals(event.from)) return false;
        if (!usersFromGroup.equals(event.usersFromGroup)) return false;
        if (!title.equals(event.title)) return false;
        return startDate.equals(event.startDate) && endDate.equals(event.endDate);

    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + usersFromGroup.hashCode();
        result = 31 * result + typeOfEvent;
        result = 31 * result + title.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + startTime;
        result = 31 * result + endDate.hashCode();
        result = 31 * result + endTime;
        return result;
    }
}
