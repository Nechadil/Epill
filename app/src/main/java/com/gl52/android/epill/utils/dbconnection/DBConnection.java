package com.gl52.android.epill.utils.dbconnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gl52.android.epill.entities.Medicament;
import com.gl52.android.epill.entities.Ordonnance;
import com.gl52.android.epill.entities.PriseMedicament;

import java.lang.reflect.Constructor;
import java.lang.Class;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Nechadil on 2017/5/18.
 */

public class DBConnection extends SQLiteOpenHelper{
    /** Database name */
    private static final String DATABASE_NAME = "epill_database";

    /** Database version */
    private static final int DATABASE_VERSION = 4;

    /** Table names */
    private static final String MEDICAMENT_TABLE   = "medicament";
    private static final String ORDONNANCE_TABLE   = "ordonnance";
    private static final String PRISEMEDICAMENT_TABLE = "prise";

    //medicament_ordonnance link table
    private static final String CONTENIR_TABLE     = "contenir";

    /** Common column name*/
    public static final String KEY_ROWID = "id";

    /** medicament table columns*/
    private static final String KEY_MEDICAMENT_NAME        = "name";
    private static final String KEY_MEDICAMENT_FREQUENCE   = "frequence";
    private static final String KEY_MEDICAMENT_DURATION    ="duration";

    /** ordonnance table columns*/
    private static final String KEY_ORDONNANCE_NAME           = "name";
    private static final String KEY_ORDONNANCE_MAILDOC       = "mailDoc";
    private static final String KEY_ORDONNANCE_DESCRIPTION   ="description";

    /** prise table colums*/
    private static final String KEY_PRISE_MEDICAMENTID       = "medicamentId";
    private static final String KEY_PRISE_HOUR                ="hour";
    private static final String KEY_PRISE_MINUTE              ="minute";
    private static final String KEY_PRISE_DATE                ="date";
    private static final String KEY_PRISE_ORDONNANCEID       ="ordonnanceId";

    /** contenir table columns*/
    private static final String KEY_CONTENIR_ORDONNANCE       = "ordonnance_id";
    private static final String KEY_CONTENIR_MEDICAMENT       = "medicament_id";

    /** Medicament Table: create statement */
    private static final String CREATE_MEDICAMENT_TABLE =
            "create table " + MEDICAMENT_TABLE + "("
                    + KEY_ROWID + " integer primary key not null,"
                    + KEY_MEDICAMENT_NAME + " text not null,"
                    + KEY_MEDICAMENT_FREQUENCE + " text not null,"
                    + KEY_MEDICAMENT_DURATION + " text not null)";

    /** Ordonnance Table: create statement */
    private static final String CREATE_ORDONNANCE_TABLE =
            "create table " + ORDONNANCE_TABLE+ "("
                    + KEY_ROWID + " integer primary key not null,"
                    + KEY_ORDONNANCE_NAME + " text not null,"
                    + KEY_ORDONNANCE_MAILDOC + " text not null,"
                    + KEY_ORDONNANCE_DESCRIPTION + " text not null)";
    /** Prise Table: create statement */
    private static final String CREATE_PRISE_TABLE =
            "create table " + PRISEMEDICAMENT_TABLE+ "("
                    + KEY_ROWID + " integer primary key not null,"
                    + KEY_PRISE_MEDICAMENTID + " integer not null,"
                    + KEY_PRISE_ORDONNANCEID + " integer not null,"
                    + KEY_PRISE_DATE + " integer not null,"
                    + KEY_PRISE_HOUR + " integer not null,"
                    + KEY_PRISE_MINUTE + " integer not null)";

    /** Contenir Table: create statement */
    private static final String CREATE_CONTENIR_TABLE =
            "create table " + CONTENIR_TABLE+ "("
                    + KEY_ROWID + " integer primary key not null,"
                    + KEY_CONTENIR_MEDICAMENT + " integer not null,"
                    + KEY_CONTENIR_ORDONNANCE + " integer not null)";

    public DBConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ORDONNANCE_TABLE);
        db.execSQL(CREATE_MEDICAMENT_TABLE);
        db.execSQL(CREATE_CONTENIR_TABLE);
        db.execSQL(CREATE_PRISE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MEDICAMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ORDONNANCE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONTENIR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRISEMEDICAMENT_TABLE);
        onCreate(db);
    }


    //Insert methods
    public String createOrdonnance(Ordonnance o) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ORDONNANCE_NAME,o.getName());
        values.put(KEY_ORDONNANCE_MAILDOC,o.getMailDoc());
        values.put(KEY_ORDONNANCE_DESCRIPTION,o.getDescription());
        long ordonnance_id = db.insert(ORDONNANCE_TABLE, null, values);
        return String.valueOf(ordonnance_id);
    }

    public String createMedicament(Medicament m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MEDICAMENT_NAME,m.getName());
        values.put(KEY_MEDICAMENT_DURATION,m.getDuration());
        values.put(KEY_MEDICAMENT_FREQUENCE,m.getFrequence());
        long medicament_id = db.insert(MEDICAMENT_TABLE, null, values);
        return String.valueOf(medicament_id);
    }

    public String createPrise(PriseMedicament p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PRISE_MEDICAMENTID,p.getMedicamentId());
        values.put(KEY_PRISE_HOUR,p.getHour());
        values.put(KEY_PRISE_MINUTE,p.getMinute());
        values.put(KEY_PRISE_ORDONNANCEID,p.getOrdonnanceId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(p.getDate());
        values.put(KEY_PRISE_DATE,dateString);
        long prise_id = db.insert(PRISEMEDICAMENT_TABLE, null, values);
        return String.valueOf(prise_id);
    }

    //Save the ordonnance medicament relationship in the Contenir table
    public void createOMRealation(String oId, String mId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENIR_MEDICAMENT, mId);
        values.put(KEY_CONTENIR_ORDONNANCE, oId);
        db.insert(CONTENIR_TABLE, null, values);
    }

    //Achieve methods
    public ArrayList<Ordonnance> getOrdonnanceLab(){
        ArrayList<Ordonnance> ordonnanceLab = new ArrayList<Ordonnance>();
        SQLiteDatabase db = this.getReadableDatabase();
        String dbOrdonnance = "select * from "
                + ORDONNANCE_TABLE ;
        Cursor c = db.rawQuery(dbOrdonnance, null);
        while (c.moveToNext()) {
                Ordonnance ordonnance = new Ordonnance();
                ordonnance.setId(c.getString(c.getColumnIndex(KEY_ROWID)));
                ordonnance.setName(c.getString(c.getColumnIndex(KEY_ORDONNANCE_NAME)));
                ordonnance.setMailDoc(c.getString((c.getColumnIndex(KEY_ORDONNANCE_MAILDOC))));
                ordonnance.setDescription(c.getString(c.getColumnIndex(KEY_ORDONNANCE_DESCRIPTION)));
                ordonnance.setMedicaments(this.getMedicamentList(ordonnance.getId()));
                ordonnanceLab.add(ordonnance);
        }
        c.close();
        return ordonnanceLab;
    };

    public ArrayList<Medicament> getMedicamentList(String ordonnanceId){
        ArrayList<Medicament> mList = new ArrayList<Medicament>();
        SQLiteDatabase db = this.getReadableDatabase();
        String dbOrdonnance = "select * from "
                + CONTENIR_TABLE + " where "
                + KEY_CONTENIR_ORDONNANCE  + " = "
                + "'"   + ordonnanceId  + "'";
        Cursor c = db.rawQuery(dbOrdonnance, null);
        while (c.moveToNext()) {
                Medicament m = new Medicament();
                m = this.getMedicament(c.getString(c.getColumnIndex(KEY_CONTENIR_MEDICAMENT)));
                mList.add(m);
        }
        c.close();
        return mList;
    };

    public ArrayList<PriseMedicament> getSchedule(){
        ArrayList<PriseMedicament> schedule = new ArrayList<PriseMedicament>();
        SQLiteDatabase db = this.getReadableDatabase();
        String dbOrdonnance = "select * from "
                + PRISEMEDICAMENT_TABLE ;
        Cursor c = db.rawQuery(dbOrdonnance, null);
        while(c.moveToNext()){
            PriseMedicament p = new PriseMedicament();
            p.setId(c.getString(c.getColumnIndex(KEY_ROWID)));
            p.setHour(c.getInt(c.getColumnIndex(KEY_PRISE_HOUR)));
            p.setMinute(c.getInt(c.getColumnIndex(KEY_PRISE_MINUTE)));
            p.setOrdonnanceId(c.getString(c.getColumnIndex(KEY_PRISE_ORDONNANCEID)));
            p.setMedicamentId(c.getString(c.getColumnIndex(KEY_PRISE_MEDICAMENTID)));
            String date = c.getString(c.getColumnIndex(KEY_PRISE_DATE));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                p.setDate(formatter.parse(date));
            }
            catch(Exception e){

            }
            schedule.add(p);
        }
        c.close();
        return schedule;
    }

    public Medicament getMedicament(String medicamentId){
        SQLiteDatabase db = this.getReadableDatabase();
        String dbOrdonnance = "select * from "
                + MEDICAMENT_TABLE        + " where "
                + KEY_ROWID      + " = "
                + "'"   + medicamentId  + "'";
        Cursor c = db.rawQuery(dbOrdonnance, null);
        if (c.moveToFirst() && c.getCount() >= 1) {
            Medicament m = new Medicament();
            m.setId(c.getString(c.getColumnIndex(KEY_ROWID)));
            m.setName(c.getString(c.getColumnIndex(KEY_MEDICAMENT_NAME)));
            m.setFrequence(c.getString(c.getColumnIndex(KEY_MEDICAMENT_FREQUENCE)));
            m.setDuration(c.getString(c.getColumnIndex(KEY_MEDICAMENT_DURATION)));
            c.close();
            return m;
        }
        return null;
    }
    public void clear(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + MEDICAMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ORDONNANCE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONTENIR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRISEMEDICAMENT_TABLE);
        onCreate(db);
    }

}
