package com.example.tanishka.project;

import android.content.Context;
import android.database.CursorWrapper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanishka on 18-05-2016.
 */
public class EventLab  {
    private List<Event> event_list;

    public EventLab() {

        event_list=new ArrayList<Event>();

    }

    public List<Event> getEvent_list() {
        return event_list;
    }

    public void generate_list(String event_name,String from_hour,String from_minute,String to_hour,String to_minute){
        Event event=new Event();
        event.setEvent_Name(event_name);
        event.setFrom_Hour(from_hour);
        event.setFrom_Minute(from_minute);
        event.setTo_Hour(to_hour);
        event.setTo_Minute(to_minute);
        event_list.add(event);
    }


}
