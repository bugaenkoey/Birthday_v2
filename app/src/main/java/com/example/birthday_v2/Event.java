package com.example.birthday_v2;

import java.util.ArrayList;

public class Event {
    String date;
    String event;
    int id;
    int idPerson;
    static ArrayList<Event> arrayListEvent = new ArrayList<Event>();


    public Event() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    @Override
    public String toString() {
//        return "Event{" +
//                "date='" + date + '\'' +
//                ", event='" + event + '\'' +
//                ", id=" + id +
//                ", idPerson=" + idPerson +
//                '}';

        return id+" "+date+" "+event;
    }
}
