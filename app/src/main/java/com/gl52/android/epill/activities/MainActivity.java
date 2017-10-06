package com.gl52.android.epill.activities;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.gl52.android.epill.R;
import com.gl52.android.epill.fragments.AlertFragment;
import com.gl52.android.epill.fragments.OrdonnanceListFragment;
import com.gl52.android.epill.fragments.SuiviFragment;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragments= new Fragment[2];
        mFragments[0] = new SuiviFragment();
        mFragments[1] = new OrdonnanceListFragment();
        getFragmentManager().beginTransaction().add(R.id.fragmentContainer,mFragments[0]).commit();
        initView();
    }
    private void initView(){
        mTabLayout = (TabLayout)findViewById(R.id.bottom_tab_layout);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setText("Suivi"));
        mTabLayout.addTab(mTabLayout.newTab().setText("List"));

    }
    private void onTabItemSelected(int position){
        Fragment fragment = null;
        fragment = mFragments[position];
        if(fragment!=null) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        }
    }
}

