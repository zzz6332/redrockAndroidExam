package com.example.redhomework.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.redhomework.tools.Internet;

public class InternetReiceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Boolean network = Internet.networkIsavailable(context);
        if (!network) {
            Toast.makeText(context, "当前无网络，部分功能将无法使用", Toast.LENGTH_SHORT).show();
        }
    }
}
