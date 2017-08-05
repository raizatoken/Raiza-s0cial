package com.project.raizasocial.JSON.models;

import com.project.raizasocial.Constants;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventJSONModel {
    public int event_id;
    public String event_name;
    public String event_description;
    public String event_creator;
    public String personal_feeling;
    public Date start_time;
    public Date end_time;
    public int geofence_id;

    public EventJSONModel() {
        super();
    }

    public int getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public String getEvent_creator() {
        return event_creator;
    }

    public String getPersonal_feeling() {
        return personal_feeling;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public void setEvent_creator(String event_creator) {
        this.event_creator = event_creator;
    }

    public void setPersonal_feeling(String personal_feeling) {
        this.personal_feeling = personal_feeling;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public void setGeofence_id(int geofence_id) {
        this.geofence_id = geofence_id;
    }

    public String getStart_time_as_String() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                Constants.DATE_TIME_FORMAT2, Locale.US);
        return dateFormatter.format(start_time);
    }

    public void setStart_time_from_epoch(String start_time_from_epoch) throws ParseException {
        this.start_time = new Date(Long.parseLong(start_time_from_epoch));
    }

    public void setStart_time2(String start_time) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT2, Locale.US);
        try {
            this.start_time = formatter.parse(start_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setEnd_time_from_epoch(String end_time_from_epoch) throws ParseException {
        this.end_time = new Date(Long.parseLong(end_time_from_epoch));
    }

    public Date getEnd_time() {
        return end_time;
    }

    public String getEnd_time_as_String() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                Constants.DATE_TIME_FORMAT2, Locale.US);
        return dateFormatter.format(end_time);
    }

    public void setEnd_time2(String end_time) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT2, Locale.US);
        //formatter.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
        try {
            this.end_time = formatter.parse(end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getGeofence_id() {
        return geofence_id;
    }
}
