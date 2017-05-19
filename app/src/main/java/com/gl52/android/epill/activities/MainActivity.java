package com.gl52.android.epill.activities;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gl52.android.epill.R;
import com.gl52.android.epill.fragments.OrdonnanceListFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new OrdonnanceListFragment();
    }
}

