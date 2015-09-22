package com.crowdconquer.crowdconquer;

import android.net.http.SslCertificate;
import android.os.AsyncTask;
import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.net.ConnectException;
import java.net.Socket;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by michel on 9/22/15.
 */
public class NetworkManager extends AsyncTask<String, Object, Object> {
    WebSocket ws;
    String T = "NetworkManager";
    protected void onPostExecute() {

    }

    @Override
    protected Object doInBackground(String... params) {
        try{
            WebSocketFactory factory = new WebSocketFactory();

            factory
                    .createSocket("ws://localhost:8080")
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket ws, String message) {
                            // Received a response. Print the received message.
                            System.out.println(message);

                            // Close the WebSocket connection.
                            ws.disconnect();
                        }
                    })
                    .connect()
                    .sendText("Hello.");

            Log.d(T, "ok");
        }catch (Exception e){
            ConnectException ce = (ConnectException) e;
            Log.d(T, "could not connect");
            e.printStackTrace();
        }

        return new Object();
    }
}

