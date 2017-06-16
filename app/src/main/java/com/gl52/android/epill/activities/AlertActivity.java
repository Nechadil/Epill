package com.gl52.android.epill.activities;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.gl52.android.epill.fragments.AlertFragment;

/**
 * Created by Nechadil on 2017/6/6.
 */

public class AlertActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /** Creating an Alert Dialog Window */
        AlertFragment alert = new AlertFragment();
    /** Opening the Alert Dialog Window. This will be opened when the alarm goes off */
        alert.show(getFragmentManager(), "AlertFragment");
    }
}
