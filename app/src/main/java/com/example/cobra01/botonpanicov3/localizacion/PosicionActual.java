package com.example.cobra01.botonpanicov3.localizacion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.cobra01.botonpanicov3.R;
import com.example.cobra01.botonpanicov3.alerta.MensajePanico;
import com.example.cobra01.botonpanicov3.alerta.SMSAdapter;

import static android.location.Criteria.ACCURACY_FINE;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Created by cobra01 on 31/08/2017.
 */

public class PosicionActual {

    String local;
    double lat = 0.0;
    double lon = 0.0;
    Context context;
    public static final String GOOGLE_MAP_URL = "https://maps.google.com/maps?q=";
    public static final String VIA = " via ";
    String num= "3133054895";

    public void Posicion() {
        PosicionListener gt = new PosicionListener(context);
        Location l = gt.getLocation();
        if( l == null){
            System.out.println("encienda gps");
        }else {
            Log.e("sec","mostrando posicion");
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            System.out.println("GPS Lat = "+lat+"\n lon = "+lon);
        }

    }
    public void enviarparam(){
        Log.e("*****", "enviando parametro");
        MensajePanico mp = new MensajePanico(context);
       /* mp.sendMessage(local,num);*/
    }



}
