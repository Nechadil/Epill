package com.gl52.android.epill.entities;

import java.util.ArrayList;

/**
 * Created by Nechadil on 2017/5/16.
 */

public class Ordonnance {
    private String id;
    private String name;
    private String mailDoc;
    private String description;
    private ArrayList<Medicament> mMedicaments;

    //To generate an id, to be removed after the insert of the DBConnection

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Initiacte an ordonnance
    public Ordonnance(){
        this.mMedicaments = new ArrayList<Medicament>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailDoc() {
        return mailDoc;
    }

    public void setMailDoc(String mailDoc) {
        this.mailDoc = mailDoc;
    }

    public ArrayList<Medicament> getMedicaments() {
        return mMedicaments;
    }

    public void setMedicaments(ArrayList<Medicament> medicaments) {
        mMedicaments = medicaments;
    }

    public Medicament getMedicament(String medicamentId){
        for (Medicament m:mMedicaments){
            if (m.getId().equals(medicamentId)){
                return m;
            }
        }
        return null;
    }

}
