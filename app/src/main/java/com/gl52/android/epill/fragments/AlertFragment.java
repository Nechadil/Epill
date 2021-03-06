package com.gl52.android.epill.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.app.DialogFragment;
import android.view.WindowManager;

import com.gl52.android.epill.activities.AlertActivity;

/**
 * Created by Nechadil on 2017/6/6.
 */

public class AlertFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Turn Screen On and Unlock the keypad when this alert dialog is displayed
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Epill");
        //Making it so notification can only go away by pressing the buttons
        setCancelable(false);
        builder.setMessage("Time to take pill");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
