package com.gl52.android.epill.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gl52.android.epill.R;
import com.gl52.android.epill.entities.Medicament;
import com.gl52.android.epill.entities.OrdonnanceLab;

import static com.gl52.android.epill.fragments.OrdonnanceFragment.EXTRA_ORDONNANCE_ID;

/**
 * Created by dc on 2017/5/22.
 */

public class AddOrUpdateMedicamentFragment extends Fragment {
    public static String EXTRA_MEDICAMENT_ID = "com.gl52.android.epill.medicament_id";
    private Medicament mMedicament;
    private EditText mNameField;
    private EditText mFrequenceField;
    private EditText mDurationField;

    public static AddOrUpdateMedicamentFragment newInstance(String ordonnanceId,String medicamentId){
        Bundle args = new Bundle();
        args.putString(EXTRA_MEDICAMENT_ID, medicamentId);
        args.putString(EXTRA_ORDONNANCE_ID, ordonnanceId);
        AddOrUpdateMedicamentFragment fragment = new AddOrUpdateMedicamentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Medicament Information");
        //Get the medicament with the id
        String medicamentId = (String)getArguments().getString(EXTRA_MEDICAMENT_ID);
        String ordonnanceId = (String)getArguments().getString(EXTRA_ORDONNANCE_ID);
        mMedicament = OrdonnanceLab.get(getActivity()).getOrdonnance(ordonnanceId).getMedicament(medicamentId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_medicament, parent,false);
        mNameField = (EditText)v.findViewById(R.id.medicament_name);
        mNameField.setText(mMedicament.getName());
        mNameField.setEnabled(false);
        //Action when the text is changed
        /*
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        */
        mFrequenceField = (EditText)v.findViewById(R.id.medicament_frequence);
        mFrequenceField.setText(mMedicament.getFrequence());
        mFrequenceField.setEnabled(false);
        mDurationField = (EditText)v.findViewById(R.id.medicament_duration);
        mDurationField.setText(mMedicament.getDuration());
        mDurationField.setEnabled(false);

        return v;

    }
}
