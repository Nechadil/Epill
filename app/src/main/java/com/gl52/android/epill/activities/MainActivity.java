package com.gl52.android.epill.activities;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.gl52.android.epill.R;
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
        mFragments= new Fragment[4];
        mFragments[0] = new SuiviFragment();
        mFragments[1] = new OrdonnanceListFragment();
        mFragments[2] = new Fragment();
        mFragments[3] = new Fragment();
        getFragmentManager().beginTransaction().add(R.id.fragmentContainer,mFragments[0]).commit();
        initView();
       /*new Thread(new Runnable() {
            @Override
            public void run() {

                InputStream is = null;
                try {
                    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                    Set<BluetoothDevice> set = adapter.getBondedDevices();
                    BluetoothDevice device;
                    for(BluetoothDevice b: set){
                        if (b.getName().equals("Adafruit Bluefruit LE")){
                            device = b;
                        }
                    }
                    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    BluetoothServerSocket serverSocket = adapter.listenUsingRfcommWithServiceRecord("serverSocket", uuid);
                    Handler mHandler = new Handler(getApplicationContext().getMainLooper());
                    mHandler.sendEmptyMessage(1);
                    BluetoothSocket accept = serverSocket.accept();
                    is = accept.getInputStream();

                    byte[] bytes = new byte[1024];
                    int length = is.read(bytes);
                    Toast.makeText(MainActivity.this, "xxxx", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
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
        mTabLayout.addTab(mTabLayout.newTab().setText("Box"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Count"));
       /* Intent intent = new Intent(getBaseContext(), AlertActivity.class);
        PendingIntent operation = PendingIntent.getActivity(getBaseContext(), (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+5000,operation);
        Toast.makeText(getBaseContext(), "Alarm  setted", Toast.LENGTH_SHORT).show();*/

    }
    private void onTabItemSelected(int position){
        Fragment fragment = null;
        fragment = mFragments[position];
        if(fragment!=null) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        }
    }
}

