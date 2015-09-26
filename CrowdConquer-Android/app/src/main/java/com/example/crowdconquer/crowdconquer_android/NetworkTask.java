package com.example.crowdconquer.crowdconquer_android;

import android.util.Log;
import com.example.crowdconquer.crowdconquer_android.NaiveSSLContext;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONObject;

/**
 * Created by michel on 9/24/15.
 */
public class NetworkTask extends Thread{
    private String T="Network"; // tag for Log
    private String socketEndpoint = "wss://192.168.1.78:8080";
    private RPC rpc;

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

            ws = factory.createSocket(socketEndpoint)
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket ws, String message) {
                            // Received a response. Print the received message.
                            System.out.println(message);

                            // Close the WebSocket connection.
                            //ws.disconnect();
                        }
                    })
                    .connect();

            Callback callback = new Callback() {
                @Override
                public void action() {
                    Log.d(T, "received response, WoW!");
                }
            };
            rpc = new RPC(ws);

            rpc.hello(callback); // this is not called here, it's just for testing

            Log.d(T, "connected");
        }catch (Exception e){
            Log.d(T, "could not connect");
            e.printStackTrace();
        }
    }

}
