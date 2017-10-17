package com.example.cobra01.botonpanicov3.usuario;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.cobra01.botonpanicov3.configuracion.Conexion;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.StringTokenizer;

/**
 * Created by cobra01 on 29/09/2017.
 */

public class registrar {

    private Context context;
    Socket socket;
    Boolean CONEXION = false;
    final static String URL_SOCKET = "http://192.168.100.206:70";
    final static String PARAM_ID = "imeiTel";
    final static String PARAM_NOM = "nombre";
    final static String PARAM_TEL = "telefono";
    final static String PARAM_DOC = "documento";
    final static String PARAM_DIR = "direccion";
    {
        try{
            socket = IO.socket(URL_SOCKET);
            Log.e("Socket","Direccion de socket establecida");
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    public void recibir(final String nomb, final String docum, final String tel, final String direccion, final Context context) {
        socket.connect();

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d("SOCKET", "CONEXION ESTABLECIDA");
                String myIMEI = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                JSONObject obj = new JSONObject();
                Log.e("IMEI", myIMEI);
                try {
                    obj.put(PARAM_ID, myIMEI);
                    obj.put(PARAM_NOM, nomb);
                    obj.put(PARAM_DOC, docum);
                    obj.put(PARAM_TEL, tel);
                    obj.put(PARAM_DIR, direccion);
                    socket.emit("login", obj);
                    Log.d("SOCKET", "DATOS ENVIADOS");
                    socket.disconnect();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(">>>>>>>>>", "CONECTADO");
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                CONEXION = false;
                Log.e(">>>>>>>>>", "DESCONECTADO");
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e(">>>>>>>>>", "SERVIDOR NO ENCONTRADO");
            }
        });
    }
}
