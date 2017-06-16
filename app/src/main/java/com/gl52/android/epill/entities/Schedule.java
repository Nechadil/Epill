package com.gl52.android.epill.entities;

import android.content.Context;

import com.gl52.android.epill.utils.dbconnection.DBConnection;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Nechadil on 2017/6/7.
 */

public class Schedule {
    private Context mAppContext;
    private static Schedule sSchedule;
    private ArrayList<PriseMedicament> mPrise;
    private ArrayList<PriseMedicament> temporaryPrise;
    private DBConnection db;

    public ArrayList<PriseMedicament> getTemporaryPrise() {
        return temporaryPrise;
    }

    public void setTemporaryPrise(ArrayList<PriseMedicament> temporaryPrise) {
        this.temporaryPrise = temporaryPrise;
    }

    public ArrayList<PriseMedicament> getPrise() {
        return mPrise;
    }

    public void setPrise(ArrayList<PriseMedicament> prise) {
        mPrise = prise;
    }

    public ArrayList<PriseMedicament> getPrise(Calendar calendar) {
        ArrayList<PriseMedicament> prise = new ArrayList<PriseMedicament>();
        Calendar c = Calendar.getInstance();
        for(PriseMedicament p:mPrise){
            c.setTime(p.getDate());
            if(c.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)){
                prise.add(p);
            }
        }
        return prise;
    }
    //initiate the schedule
    private Schedule(Context appContext){
        mAppContext = appContext;
        if(db == null)
            db = new DBConnection(appContext);
        //mPrise = new ArrayList<PriseMedicament>();
        mPrise = db.getSchedule();
    }
    //Save the schedule in the schedule table by using the temporary
    public void addSchedule(String oId){
        ArrayList<PriseMedicament> prise = this.temporaryPrise;
        for(PriseMedicament p:prise){
            p.setOrdonnanceId(oId);
            db.createPrise(p);
            mPrise.add(p);
            Collections.sort(mPrise);
        }
        this.temporaryPrise = null;
    }


    public static Schedule get(Context c){
        if(sSchedule == null){
            sSchedule = new Schedule(c.getApplicationContext());
        }
        return  sSchedule;
    }
}
