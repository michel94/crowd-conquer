package com.example.crowdconquer.crowdconquer_android;

import android.util.Log;
import com.example.crowdconquer.crowdconquer_android.NaiveSSLContext;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

/**
 * Created by michel on 9/24/15.
 */
public class NetworkTask extends Thread{
    private String T="Network"; // tag for Log
    private String socketEndpoint = "wss://192.168.1.8:8080";

    private WebSocket ws;

    public NetworkTask(){
    }

    public WebSocket getSocket() {
        return ws;
    }

    @Override
    public void run() {
        Log.d(T, "Network thread created.");


        try{
            WebSocketFactory factory = new WebSocketFactory();
            factory.setSSLContext(NaiveSSLContext.getInstance("TLS"));

            factory
                    .createSocket(socketEndpoint)
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

            Log.d(T, "connected");
        }catch (Exception e){
            Log.d(T, "could not connect");
            e.printStackTrace();
        }


    }
}
