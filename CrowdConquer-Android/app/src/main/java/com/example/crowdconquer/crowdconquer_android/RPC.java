package com.example.crowdconquer.crowdconquer_android;

import com.neovisionaries.ws.client.WebSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by michel on 9/26/15.
 */
public class RPC {
    private WebSocket ws;
    private Hashtable<Integer, Callback> callbackMap;
    private int callbackCount = 1;

    public RPC(){

    }
    public RPC(WebSocket ws){
        this.ws = ws;
        callbackMap = new Hashtable<Integer, Callback>();
    }

    private JSONObject genJson(String name, Callback callback, String[] names, JSONable... args){

        JSONObject jRoot = new JSONObject();
        try { // will be done elsewhere
            jRoot.put("protocol", "rpc");
            JSONObject jRpc = new JSONObject();

            JSONObject jArgs = new JSONObject();
            for(int i=0; i<args.length; i++){
                jArgs.put(names[i], args[i].toJSON());
            }
            jRpc.put("args", jArgs);
            jRpc.put("methodName", name);

            if(callback != null) {
                int id = registerCallback(callback);
                jRpc.put("callbackId", id);
            }
            jRoot.put("rpc", jRpc);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jRoot;
    }

    public void hello(Callback callback){
        JSONObject jRoot = genJson("hello", callback, new String[0]);

        ws.sendText(jRoot.toString());

    }

    public int registerCallback(Callback callback){
        callbackMap.put(callbackCount++, callback);
        return callbackCount-1;
    }
}
