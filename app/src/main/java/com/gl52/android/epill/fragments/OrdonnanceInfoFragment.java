package com.gl52.android.epill.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gl52.android.epill.R;
import com.gl52.android.epill.activities.OrdonnanceListActivity;
import com.gl52.android.epill.entities.Medicament;
import com.gl52.android.epill.entities.Ordonnance;
import com.gl52.android.epill.entities.OrdonnanceLab;

import static com.gl52.android.epill.fragments.OrdonnanceFragment.EXTRA_ORDONNANCE_ID;

/**
 * Created by dc on 2017/5/23.
 */

public class OrdonnanceInfoFragment extends Fragment {
    private Ordonnance mOrdonnance;
    private EditText mIdField;
    private EditText mNameField;
    private EditText mMailDocField;
    private EditText mDescriptionField;
    private Button mConfirmButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Ordonnance Information");
        mOrdonnance = new Ordonnance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_ordonnance, parent, false);
        mNameField = (EditText) v.findViewById(R.id.ordonnance_name);
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOrdonnance.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMailDocField = (EditText) v.findViewById(R.id.ordonnance_mailDoc);
        mMailDocField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOrdonnance.setMailDoc(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDescriptionField = (EditText) v.findViewById(R.id.ordonnance_description);
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOrdonnance.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mConfirmButton = (Button) v.findViewById(R.id.medicament_confirm);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdonnanceLab.get(getActivity()).addOrdonnance(mOrdonnance);
                Intent i = new Intent(getActivity(),OrdonnanceListActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}
