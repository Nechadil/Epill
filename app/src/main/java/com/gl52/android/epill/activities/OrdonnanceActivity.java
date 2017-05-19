package com.gl52.android.epill.activities;

import android.app.Fragment;

import com.gl52.android.epill.entities.Ordonnance;
import com.gl52.android.epill.fragments.OrdonnanceFragment;

import java.util.UUID;


/**
 * Created by dc on 2017/5/17.
 */

public class OrdonnanceActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        String ordonnnaceId = (String)getIntent().getStringExtra(OrdonnanceFragment.EXTRA_ORDONNANCE_ID);
        return OrdonnanceFragment.newInstance(ordonnnaceId);
    }
}
