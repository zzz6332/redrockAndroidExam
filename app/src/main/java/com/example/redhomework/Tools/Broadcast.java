package com.example.redhomework.Tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

public class Broadcast {

    public static void register(Context context,BroadcastReceiver receiver, String action) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        context.registerReceiver(receiver, filter);
    }
    public static void unRegister(Context context,BroadcastReceiver receiver){
        context.unregisterReceiver(receiver);
    }
}
