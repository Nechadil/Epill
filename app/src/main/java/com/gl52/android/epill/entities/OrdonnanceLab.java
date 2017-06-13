package com.gl52.android.epill.entities;

import android.content.Context;
import android.widget.Toast;

import com.gl52.android.epill.utils.dbconnection.DBConnection;

import java.util.ArrayList;

/**
 * Created by Nechadil on 2017/5/16.
 */

public class OrdonnanceLab {
    private static DBConnection db;
    private static OrdonnanceLab sOrdonnanceLab;
    private ArrayList<Ordonnance> mOrdonnances;
    private Ordonnance mTemporaryOrdonnance;
    private static int id = 100;
    public Ordonnance getTemporaryOrdonnance() {
        return mTemporaryOrdonnance;
    }

    public void setTemporaryOrdonnance(Ordonnance temporaryOrdonnance) {
        mTemporaryOrdonnance = temporaryOrdonnance;
    }

    private Context mAppContext;

    public ArrayList<Ordonnance> getOrdonnances() {
        return mOrdonnances;
    }

    public Ordonnance getOrdonnance(String id){
        for (Ordonnance o : mOrdonnances){
            if(o.getId().equals(id))
                return o;
        }
        return null;
    }

    //initiate the ordonnance Lab
    private OrdonnanceLab(Context appContext){
        //Initiate the dbConnection
        if(db == null)
            db = new DBConnection(appContext);
        mAppContext = appContext;
        mOrdonnances = db.getOrdonnanceLab();
        /*mOrdonnances = new ArrayList<Ordonnance>();
        // TODO: 2017/5/17  Get the ordonnance data from DB
        Ordonnance ordonnance1 = new Ordonnance();
        //ordonnance1.setId("001");
        ordonnance1.setName("Ordonnance 1");
        ordonnance1.setMailDoc("maildoc");
        ordonnance1.setDescription("Description for 1");
        ArrayList<Medicament> mLab = new ArrayList<Medicament>();
        Medicament m1 = new Medicament();
        m1.setName("medicament 1 ");
        m1.setId("0021");
        m1.setDuration("10");
        m1.setFrequence("3");
        mLab.add(m1);
        ordonnance1.setMedicaments(mLab);
        Ordonnance ordonnance2 = new Ordonnance();
        ordonnance2.setId("002");
        ordonnance2.setName("Ordonnance 2");
        ordonnance2.setDescription("Description for 2");
        mOrdonnances.add(ordonnance1);
        mOrdonnances.add(ordonnance2);
        long l = db.createOrdonnance(ordonnance1);
        ordonnance1.setName(String.valueOf(l));*/
    }

    public static OrdonnanceLab get(Context c){
        if(sOrdonnanceLab == null){
            sOrdonnanceLab = new OrdonnanceLab(c.getApplicationContext());
        }
        return  sOrdonnanceLab;
    }

    //// TODO: 2017/5/23 Save ordonnance in the DB
    public void  addOrdonnance(Ordonnance ordonnance){
        // Save ordonnance and get ordonnanceId as return
        String oId = db.createOrdonnance(ordonnance);
        ordonnance.setId(oId);
        // Save medicaments and get medicamentIds as return
        for(Medicament m:ordonnance.getMedicaments()){
            String mId = m.getId();
            // Save medicament-ordonnance link
            db.createOMRealation(oId,mId);
        }
        mOrdonnances.add(ordonnance);
        //Clear the temporaryOrdonnance
        this.mTemporaryOrdonnance = null;
    }

    public static String saveMedicament(Medicament m){
        String medicamentId = db.createMedicament(m);
        return medicamentId;
    }
}
