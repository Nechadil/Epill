package com.gl52.android.epill.activities;

import android.app.Fragment;

import com.gl52.android.epill.entities.Ordonnance;
import com.gl52.android.epill.fragments.MedicamentFragment;
import com.gl52.android.epill.fragments.OrdonnanceFragment;

/**
 * Created by dc on 2017/5/18.
 */

public class MedicamentInfoActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        String ordonnanceId = (String)getIntent().getStringExtra(OrdonnanceFragment.EXTRA_ORDONNANCE_ID);
        String medicamentId = (String)getIntent().getStringExtra(MedicamentFragment.EXTRA_MEDICAMENT_ID);
        return MedicamentFragment.newInstance(ordonnanceId,medicamentId);
    }
}
