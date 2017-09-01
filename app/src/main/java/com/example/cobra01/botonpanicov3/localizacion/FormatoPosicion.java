package com.example.cobra01.botonpanicov3.localizacion;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.example.cobra01.botonpanicov3.R;

public class FormatoPosicion {

    public static final String GOOGLE_MAP_URL = "https://maps.google.com/maps?q=";
    public static final String VIA = " via ";
    private Location location;

    public FormatoPosicion(Location location) {
        this.location = location;
    }

    public String format(Context context) {
        Log.e("*****", "Formateo de posicion a url");
        if (location != null) {
            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            return context.getResources().getString(R.string.i_am_at) + " " + GOOGLE_MAP_URL + latitude + "," + longitude + VIA + provider;
          }
        return "";
    }
}
