package com.example.wollf.togather;

import java.util.Date;
import java.util.List;

/**
 * Created by wollf on 17-10-2017.
 */

public class Event {
    Group from;
    List<User> usersFromGroup;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    int typeOfEvent;
    String title;
    Date startDate;
    int startTime;
    Date endDate;
    int endTime;

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
        if (!startDate.equals(event.startDate)) return false;
        return endDate.equals(event.endDate);

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
