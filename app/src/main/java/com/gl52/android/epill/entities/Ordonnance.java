package com.gl52.android.epill.entities;

import java.util.ArrayList;

/**
 * Created by dc on 2017/5/16.
 */

public class Ordonnance {
    private String id;
    private String name;
    private String mailDoc;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private ArrayList<Medicament> mMedicaments;

    public Ordonnance(){

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
}