package com.gl52.android.epill.activities;

import android.app.Fragment;

import com.gl52.android.epill.fragments.OrdonnanceInfoFragment;

/**
 * Created by dc on 2017/5/23.
 */

public class OrdonnanceInfoActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new OrdonnanceInfoFragment();
    }
}
