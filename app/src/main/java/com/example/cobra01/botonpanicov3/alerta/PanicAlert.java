package com.example.cobra01.botonpanicov3.alerta;


import android.app.AlarmManager;
import android.content.Context;
import android.location.LocationManager;
import android.os.Vibrator;
import com.example.cobra01.botonpanicov3.common.AppConstants;


public class PanicAlert {
    private static final String TAG = PanicAlert.class.getName();
    private LocationManager locationManager;
    private Context context;
    private AlarmManager alarmManager1, alarmManager2;

    public PanicAlert(Context context) {
        this.context = context;
      vibrateOnce();
    }

    private void vibrateOnce() {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(AppConstants.ALERT_CONFIRMATION_VIBRATION_DURATION);
    }

}