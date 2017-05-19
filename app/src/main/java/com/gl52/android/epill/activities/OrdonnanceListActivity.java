package com.gl52.android.epill.activities;

import android.app.Fragment;

import com.gl52.android.epill.fragments.OrdonnanceListFragment;

/**
 * Created by dc on 2017/5/16.
 */

public class OrdonnanceListActivity extends  SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new OrdonnanceListFragment();
    }
}
