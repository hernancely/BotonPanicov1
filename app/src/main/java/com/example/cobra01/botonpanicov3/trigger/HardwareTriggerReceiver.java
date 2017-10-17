package com.example.cobra01.botonpanicov3.trigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.util.Log;
import com.example.cobra01.botonpanicov3.alerta.MensajePanico;
import com.example.cobra01.botonpanicov3.localizacion.FormatoPosicion;
import com.example.cobra01.botonpanicov3.localizacion.PosicionListener;

import static android.content.Intent.ACTION_SCREEN_OFF;
import static android.content.Intent.ACTION_SCREEN_ON;

public class HardwareTriggerReceiver extends BroadcastReceiver {

    private static final String TAG = HardwareTriggerReceiver.class.getName();
    protected MultiClickEvent multiClickEvent;

    public HardwareTriggerReceiver() {
        resetEvent();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(">>>>>>>", "Recibiendo evento de pantalla ON/OFF");
        String action = intent.getAction();

        if (!isCallActive(context) && (action.equals(ACTION_SCREEN_OFF) || action.equals(ACTION_SCREEN_ON))) {
            multiClickEvent.registerClick(System.currentTimeMillis());
            if(multiClickEvent.skipCurrentClick()){
                Log.e("*****", "Tiempo de espera superado para confirmacion");
                multiClickEvent.resetSkipCurrentClickFlag();
            }
            else if(multiClickEvent.canStartVibration()){
                Log.e("*****", "Click completado, Iniciando evento");
                PosicionListener pl =  new PosicionListener(context);
                Location lo = pl.getLocation();
                Log.e("POSICION", String.valueOf(lo));
                String locationString = new FormatoPosicion(lo).format();
                MensajePanico mpo = new MensajePanico(context);
                Log.e("POSICION FORMATEO",locationString);
                try {
                   mpo.recibir(locationString);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (multiClickEvent.isActivated()) {
                Log.e("*****", "Alerta activada");
                resetEvent();
            }
        }
    }

    protected void resetEvent() {
        multiClickEvent = new MultiClickEvent();
    }

    private boolean isCallActive(Context context) {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return manager.getMode() == AudioManager.MODE_IN_CALL;
    }

}
