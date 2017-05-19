package com.gl52.android.epill.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gl52.android.epill.R;
import com.gl52.android.epill.activities.OrdonnanceActivity;
import com.gl52.android.epill.entities.Medicament;
import com.gl52.android.epill.entities.Ordonnance;
import com.gl52.android.epill.entities.OrdonnanceLab;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by dc on 2017/5/17.
 */

public class OrdonnanceFragment extends ListFragment{
    private ArrayList<Medicament> mMedicaments;
    private Ordonnance mOrdonnance;
    public static final String EXTRA_ORDONNANCE_ID = "com.gl52.android.epill.ordonnance_id";

    //Register data in the Bundle
    public static OrdonnanceFragment newInstance(String ordonnanceId){
        Bundle args = new Bundle();
        args.putString(EXTRA_ORDONNANCE_ID, ordonnanceId);
        OrdonnanceFragment fragment = new OrdonnanceFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the ordonnance with the id
        String ordonnanceId = (String)getArguments().getString(EXTRA_ORDONNANCE_ID);
        mOrdonnance = OrdonnanceLab.get(getActivity()).getOrdonnance(ordonnanceId);
        MedicamentAdapter adapter = new MedicamentAdapter(mOrdonnance.getMedicaments());
        setListAdapter(adapter);

    }

    private class MedicamentAdapter extends ArrayAdapter<Medicament> {

        public MedicamentAdapter(ArrayList<Medicament> medicaments){
            super(getActivity(),0,medicaments);
        };

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_ordonnance, null);
            }
            Medicament m = getItem(position);

            TextView name = (TextView)convertView.findViewById(R.id.ordonnance_list_item_name);

            name.setText(m.getName());

            TextView description = (TextView)convertView.findViewById(R.id.ordonnance_list_item_description);
            description.setText(m.getFrequence()+" Times in"+m.getDuration()+" Days");

            return convertView;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Medicament m = ((OrdonnanceFragment.MedicamentAdapter)getListAdapter()).getItem(position);
        //Start ordonnance activity
        Intent i = new Intent(getActivity(), OrdonnanceActivity.class);
        i.putExtra(MedicamentFragment.EXTRA_MEDICAMENT_ID, m.getId());
        startActivity(i);
    }
}
