package com.example.cobra01.botonpanicov3;

import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cobra01.botonpanicov3.configuracion.Conexion;
import com.example.cobra01.botonpanicov3.configuracion.MySettings;
import com.example.cobra01.botonpanicov3.localizacion.PosicionListener;
import com.example.cobra01.botonpanicov3.trigger.HardwareTriggerService;
import com.example.cobra01.botonpanicov3.usuario.registrar;

public class Login extends AppCompatActivity implements OnClickListener {

    private AutoCompleteTextView nombre;
    private EditText telefono;
    private EditText identificacion;
    private EditText direccion;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }
        new PosicionListener(getApplicationContext());
        sharedpreferences = getSharedPreferences("MySettings", Context.MODE_PRIVATE);
        String flag=sharedpreferences.getString("Activacion", "false");
        Log.e("bandera", String.valueOf(flag));
        if (flag.equals("true")){
            setContentView(R.layout.root_layout);
            startService(new Intent(Login.this, HardwareTriggerService.class));
        }else{
            setContentView(R.layout.activity_login);
            Log.e("configuracion", String.valueOf(sharedpreferences.getString("Activacion", null)));
            nombre = (AutoCompleteTextView) findViewById(R.id.nombre);
            telefono = (EditText) findViewById(R.id.telefono);
            identificacion = (EditText) findViewById(R.id.documento);
            direccion = (EditText) findViewById(R.id.direccion);
            Button btnregistrar = (Button) findViewById(R.id.btn_registrar);
            Log.d("APP", "INICIANDO");
            btnregistrar.setOnClickListener(this);
        }
    }

    public void onClick(View v) {

        registrar res = new registrar();
        Conexion con = new Conexion();

        if(con.hasActiveInternetConnection(getApplicationContext())){

           res.recibir(nombre.getText().toString(), identificacion.getText().toString(), telefono.getText().toString(), direccion.getText().toString(), getApplicationContext());

                editor = sharedpreferences.edit();
                editor.putString("Activacion", "true");
                editor.apply();
                Log.d("BTN", "REGISTRANDO CLICK");
                Log.e("sharedpreferences",sharedpreferences.getString("Activacion","false"));
                Toast.makeText(getApplicationContext(),"Registro Exitoso",Toast.LENGTH_LONG).show();
                startService(new Intent(Login.this, HardwareTriggerService.class));
                setContentView(R.layout.root_layout);

        }else{
            Toast.makeText(getApplicationContext(),"Sin Conexion a Internet",Toast.LENGTH_LONG).show();
            Log.e("Conexion","Sin Conexion a Internet");
        }
    }
}


