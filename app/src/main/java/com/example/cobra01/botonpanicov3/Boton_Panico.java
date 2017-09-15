package com.example.cobra01.botonpanicov3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.cobra01.botonpanicov3.localizacion.PosicionListener;
import com.example.cobra01.botonpanicov3.trigger.HardwareTriggerService;

public class Boton_Panico extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_layout);
        startService(new Intent(Boton_Panico.this, HardwareTriggerService.class));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            getApplication().startService(new Intent(getApplicationContext(), HardwareTriggerService.class));
        }
        new PosicionListener(getApplicationContext());
           }

}
