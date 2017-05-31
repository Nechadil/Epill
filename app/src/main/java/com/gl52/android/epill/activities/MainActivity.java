package com.gl52.android.epill.activities;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.gl52.android.epill.R;
import com.gl52.android.epill.fragments.MedicamentInfoFragment;
import com.gl52.android.epill.fragments.MedicamentListFragment;
import com.gl52.android.epill.fragments.OrdonnanceInfoFragment;
import com.gl52.android.epill.fragments.OrdonnanceListFragment;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragments= new Fragment[4];
        mFragments[0] = new OrdonnanceListFragment();
        mFragments[1] = new Fragment();
        mFragments[2] = new Fragment();
        mFragments[3] = new Fragment();
        //getFragmentManager().beginTransaction().add(R.id.fragmentContainer,mFragments[0]).commit();
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

        mTabLayout.addTab(mTabLayout.newTab().setText("Prescription"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Suivi"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Distributor"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Count"));
    }
    private void onTabItemSelected(int position){
        Fragment fragment = null;
        fragment = mFragments[position];
        if(fragment!=null) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        }
    }
}

