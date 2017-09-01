package com.example.cobra01.botonpanicov3.alerta;

import android.content.Context;
import android.util.Log;

/**
 * Created by cobra01 on 31/08/2017.
 */

public class MensajePanico {

    private Context context;

    public MensajePanico(Context context) {
        this.context = context;
    }

    public void recibir(String urlpos){
        Log.e("*****", "Enviando Mensaje a Destino");
        String num="3133054895";
        sendMessage(urlpos,num);
    }
    public void sendMessage(String message,String numero){
        Log.e("*****", "Enviando SmsAdapter");
        SMSAdapter smsAdapter = getSMSAdapter();
            smsAdapter.sendSMS(context, numero, message);
    }
    SMSAdapter getSMSAdapter() {
        return new SMSAdapter();
    }
}
