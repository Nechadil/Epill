package com.gl52.android.epill.entities;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Nechadil on 2017/6/7.
 */

public class PriseMedicament implements Comparable<PriseMedicament>{
    private String id;
    private String medicamentId;
    private int hour;
    private int minute;
    private Date date;
    private String ordonnanceId;

    public String getOrdonnanceId() {
        return ordonnanceId;
    }

    public void setOrdonnanceId(String ordonnanceId) {
        this.ordonnanceId = ordonnanceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(String medicamentId) {
        this.medicamentId = medicamentId;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public int compareTo(PriseMedicament p) {
        if (hour < p.getHour())
            return -1;
        else if (hour > p.getHour())
            return 1;
        else {
            if (minute < p.getMinute())
                return -1;
            else if (minute > p.getMinute())
                return 1;
            else
                return 0;
        }
    }
}
