package com.gl52.android.epill.activities;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.gl52.android.epill.fragments.AlertFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

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
    /** Send a message to the arduino bluetooth card*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                    adapter.enable();
                    Set<BluetoothDevice> set = adapter.getBondedDevices();
                    BluetoothDevice device;
                    for(BluetoothDevice b: set){
                        if (b.getName().equals("epill")){
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
