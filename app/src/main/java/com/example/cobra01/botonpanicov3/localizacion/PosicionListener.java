package com.example.cobra01.botonpanicov3.localizacion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.cobra01.botonpanicov3.alerta.MensajePanico;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by cobra01 on 30/08/2017.
 */

public class PosicionListener implements LocationListener {

    private Location loc;
    Context context;
    private LocationManager locManager;
    private static final int GPS_TIME_INTERVAL = 60000; // get gps location every 1 min
    private static final int GPS_DISTANCE= 1000; // set the distance value in meter
    private static final int captureFrequencey=3*60*1000;

    public PosicionListener(Context context) {
        super();
        this.context = context;
    }

    public Location getLocation() {

        try {
            LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                Log.e("sec", "GPS activado");
                Log.e("sec", "intentando obtener posicion");
                locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return null;
                }
                loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, captureFrequencey, 100, this);
                Log.e("sec","posicion detectada");
                return loc;
            }else{
                Log.e("sec","GPS desactivado");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override

    public void onLocationChanged(Location location) {
    // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la deteccion de un cambio de ubicacion
        Log.e(">>>>>>", "Enviando posicion Actual.");
        String locationString = new FormatoPosicion(location).format();
        MensajePanico mpo = new MensajePanico(context);
        Log.e("POSICION CAMBIADA",locationString);
        try {
            mpo.recibir(locationString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    // Este metodo se ejecuta cuando el GPS es activado
    }

    @Override
    public void onProviderDisabled(String provider) {
    // Este metodo se ejecuta cuando el GPS es desactivado
    }
}
