package com.gl52.android.epill.fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gl52.android.epill.R;

import com.gl52.android.epill.activities.AlertActivity;
import com.gl52.android.epill.activities.OrdonnanceInfoActivity;
import com.gl52.android.epill.entities.Medicament;
import com.gl52.android.epill.entities.Ordonnance;
import com.gl52.android.epill.entities.OrdonnanceLab;
import com.gl52.android.epill.entities.PriseMedicament;
import com.gl52.android.epill.entities.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static com.gl52.android.epill.fragments.MedicamentListFragment.EXTRA_ORDONNANCE_ID;

/**
 * Created by Nechadil on 2017/5/18.
 */

public class MedicamentInfoFragment extends Fragment {
    public static String EXTRA_MEDICAMENT_ID = "com.gl52.android.epill.medicament_id";
    public static String EXTRA_MEDICAMENT_EDITABLE = "com.gl52.android.epill.medicament_editable";
    private String mOrdonnanceId;
    private Medicament mMedicament;
    private ArrayList<PriseMedicament> mPriseMedicaments;
    private Boolean mEditable;
    private EditText mNameField;
    private EditText mFrequenceField;
    private EditText mDurationField;
    private Button mConfirmButton;
    private Button mSetTimeButton;

    public static MedicamentInfoFragment newInstance(String ordonnanceId, String medicamentId, Boolean editable){
        Bundle args = new Bundle();
        args.putString(EXTRA_MEDICAMENT_ID, medicamentId);
        args.putString(EXTRA_ORDONNANCE_ID, ordonnanceId);
        args.putBoolean(EXTRA_MEDICAMENT_EDITABLE,editable);
        MedicamentInfoFragment fragment = new MedicamentInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Medicine's Information");
        //Get the medicine with the id
        String medicamentId = (String)getArguments().getString(EXTRA_MEDICAMENT_ID);
        mOrdonnanceId = (String)getArguments().getString(EXTRA_ORDONNANCE_ID);
        mPriseMedicaments = new ArrayList<PriseMedicament>();
        mEditable = (Boolean)getArguments().getBoolean(EXTRA_MEDICAMENT_EDITABLE);
        if(medicamentId == null){
            mMedicament = new Medicament();
            getActivity().setTitle("New Medicine");
        } else {
            mMedicament = OrdonnanceLab.get(getActivity())
                                        .getOrdonnance(this.mOrdonnanceId)
                                        .getMedicament(medicamentId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_medicament, parent,false);
        mNameField = (EditText)v.findViewById(R.id.medicament_name);
        mFrequenceField = (EditText)v.findViewById(R.id.medicament_frequence);
        mConfirmButton =(Button)v.findViewById(R.id.medicament_confirm);
        mDurationField = (EditText)v.findViewById(R.id.medicament_duration);
        mSetTimeButton = (Button)v.findViewById(R.id.medicament_time);

        mNameField.setText(mMedicament.getName());
        mFrequenceField.setText(mMedicament.getFrequence());
        mFrequenceField.setInputType(InputType.TYPE_CLASS_NUMBER);
        mDurationField.setText(mMedicament.getDuration());
        mDurationField.setInputType(InputType.TYPE_CLASS_NUMBER);
        if(!mEditable){
            mNameField.setEnabled(false);
            mFrequenceField.setEnabled(false);
            mDurationField.setEnabled(false);
            mConfirmButton.setVisibility(View.GONE);
            mSetTimeButton.setVisibility(View.GONE);
        }


        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMedicament.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFrequenceField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMedicament.setFrequence(s.toString());
                //When the frequence is changed, the priseMedicament list will be initiated
                mPriseMedicaments = new ArrayList<PriseMedicament>();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDurationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMedicament.setDuration(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSetTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f = mMedicament.getFrequence();
                if (f != null && Integer.parseInt(f) >= 0){
                    if(mPriseMedicaments.size() < Integer.parseInt(f)) {
                        //Set timer for the medicaments
                        int mFrequence = Integer.parseInt(mMedicament.getFrequence());
                        Calendar c = Calendar.getInstance();
                        TimePickerDialog picker = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker tp, int hour, int min) {
                                        PriseMedicament p = new PriseMedicament();
                                        //Toast.makeText(getActivity(), hour + "h" + min + "min", Toast.LENGTH_SHORT).show();
                                        p.setHour(hour);
                                        p.setMinute(min);
                                        for (PriseMedicament pm:mPriseMedicaments){
                                            if(p.compareTo(pm) == 0){
                                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                                                        .setTitle("Error")
                                                        .setPositiveButton("OK",null);
                                                alert.setMessage("The time already exists");
                                                alert.show();
                                                return;
                                            }
                                        }
                                        mPriseMedicaments.add(p);
                                        Collections.sort(mPriseMedicaments);
                                    }
                                },
                                c.get(Calendar.HOUR_OF_DAY),
                                c.get(Calendar.MINUTE),
                                true);
                        picker.setTitle("Add medicine take time Number " + (mPriseMedicaments.size() + 1));
                        picker.show();
                    } else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setPositiveButton("OK",null);
                        alert.setMessage("Already enough medicine take times");
                        alert.show();
                    }
                }
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setPositiveButton("OK",null);
                    alert.setMessage("Please input the medicine's frequence");
                    alert.show();
                }
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verify the information
                String name = mMedicament.getName();
                String frequence = mMedicament.getFrequence();
                String duration = mMedicament.getDuration();
                Boolean verified = (name != null)&&(!name.equals(""))
                        &&(frequence != null)&&(!frequence.equals(""))
                        &&(duration != null)&&(!duration.equals(""))
                        &&(Integer.parseInt(frequence) == mPriseMedicaments.size());
                if(verified){
                    Intent intent = new Intent(getActivity().getBaseContext(), AlertActivity.class);
                    //Each time the request code should be unique to set different alarms
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    //Save medicament
                            Ordonnance ordonnance = OrdonnanceLab.get(getActivity()).getOrdonnance(mOrdonnanceId);
                            if(ordonnance == null){
                                ordonnance = OrdonnanceLab.get(getActivity()).getTemporaryOrdonnance();
                            }
                            String medicamentId = OrdonnanceLab.saveMedicament(mMedicament);
                            ordonnance.getMedicaments().add(mMedicament);
                            ArrayList<PriseMedicament> prise = Schedule.get(getActivity()).getTemporaryPrise();
                            if (prise == null)
                                prise = new ArrayList<PriseMedicament>();
                                Schedule.get(getActivity()).setTemporaryPrise(prise);
                            for(PriseMedicament p:mPriseMedicaments) {
                                Calendar calendar = Calendar.getInstance();
                                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                                int currentMinute = calendar.get(Calendar.MINUTE);
                                //When the current time is later than the pill take time, this pill take will start from tomorrow
                                if(p.getHour()<currentHour || (p.getHour()==currentHour && p.getMinute() <= currentMinute)){
                                    calendar.add(Calendar.DAY_OF_YEAR,+1);
                                };
                                calendar.set(Calendar.HOUR_OF_DAY,0);
                                calendar.set(Calendar.MINUTE,0);
                                calendar.set(Calendar.SECOND,0);
                                p.setDate(calendar.getTime());
                                p.setMedicamentId(medicamentId);
                                Date date = new Date(p.toLong());
                                PendingIntent operation = PendingIntent.getActivity(getActivity().getBaseContext(), (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.set(AlarmManager.RTC_WAKEUP,p.toLong(),operation);
                                Toast.makeText(getActivity().getBaseContext(), "Alarm set at"+date.toString(), Toast.LENGTH_SHORT).show();
                                for(int i=1;i<Integer.parseInt(mMedicament.getDuration());i++){
                                    PriseMedicament pm = new PriseMedicament();
                                    pm.setMinute(p.getMinute());
                                    pm.setHour(p.getHour());
                                    calendar.add(Calendar.DAY_OF_YEAR,+1);
                                    pm.setDate(calendar.getTime());
                                    pm.setMedicamentId(medicamentId);
                                    prise.add(pm);
                                    //alarmManager.set(AlarmManager.RTC_WAKEUP,pm.toLong(),operation);
                                    date.setTime(pm.toLong());
                                    //Different alarm should use different request code
                                    operation = PendingIntent.getActivity(getActivity().getBaseContext(), UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP,pm.toLong(),operation);
                                    Toast.makeText(getActivity().getBaseContext(), "Alarm set at"+date.toString(), Toast.LENGTH_SHORT).show();
                                }
                                prise.add(p);
                            }
                            Intent i = new Intent(getActivity(), OrdonnanceInfoActivity.class);
                            i.putExtra(MedicamentListFragment.EXTRA_ORDONNANCE_ID, mOrdonnanceId);
                            i.putExtra(OrdonnanceInfoFragment.EXTRA_ORDONNANCE_EDITABLE,true);
                            startActivity(i);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setPositiveButton("OK",null);
                    if((name == null)||(name.equals(""))){
                        alert.setMessage("Please input the medicine's name");
                    } else if((frequence == null)||(frequence.equals("")||(frequence.equals("0")))){
                        alert.setMessage("Please input the appropriate frequence");
                    } else if((duration== null)||duration.equals("")||(duration.equals("0"))){
                        alert.setMessage("Please input the appropriate duration");
                    } else if(Integer.parseInt(frequence) != mPriseMedicaments.size()){
                        alert.setMessage("Not enough medicine take time added");
                    }
                    alert.show();
                }
            }
        });
        return v;
    }

}
