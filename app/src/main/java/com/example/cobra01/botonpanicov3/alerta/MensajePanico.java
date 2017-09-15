package com.example.cobra01.botonpanicov3.alerta;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.example.cobra01.botonpanicov3.http.httpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.StringTokenizer;

/**
 * Created by cobra01 on 31/08/2017.
 */

public class MensajePanico {

    private Context context;
    Socket socket;
    final static String URL_SOCKET = "http://192.168.100.206:70";
    final static String MY_EVENT = "my event";
    final static String PARAM_LAT = "latitud";
    final static String PARAM_LON = "longitud";
    protected LocationListener locationListener;
    protected LocationManager locationManager;
    public MensajePanico(Context context) {
        this.context = context;
    }
    public void conectar(){
        try{
  /* Instance object socket */
            socket = IO.socket(URL_SOCKET);

        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener(){
            @Override
            public void call(Object... args) {
    /* Our code */
                Log.e(">>>>>>>>>","CONECTADO");
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener(){
            @Override
            public void call(Object... args) {
    /* Our code */
                Log.e(">>>>>>>>>","DESCONECTADO");
            }
        });
    }
    public void mensaje(){


/* Emit event */


    }
    public void recibir(String urlpos){
        conectar();
        socket.connect();

        Log.e("*****", "Enviando Mensaje a Destino");
        String num="3184362590";
        StringTokenizer tokens = new StringTokenizer(urlpos, ",");
        Log.e(">>>>>>>>>","stringtokeneizer");
        String first = tokens.nextToken();
        String second = tokens.nextToken();
        Log.d(">>ENVIANDO POSICION>",urlpos);
        JSONObject obj = new JSONObject();


        try {
            obj.put(PARAM_LAT, first);
            obj.put(PARAM_LON, second);
            socket.emit("posicion",obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*socket.emit(MY_EVENT, obj);*/
        /*sendMessage(urlpos,num);*/
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
