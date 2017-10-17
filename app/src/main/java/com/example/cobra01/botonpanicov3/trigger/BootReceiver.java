package com.example.cobra01.botonpanicov3.trigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.ACTION_BOOT_COMPLETED;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Map<String, String> eventLog = new HashMap<String, String>();
        eventLog.put("Reiniciando aplicacion al iniciar telefono", new Date(System.currentTimeMillis()).toString());
        if(intent.getAction().equals(ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, HardwareTriggerService.class));
        }

    }
}
