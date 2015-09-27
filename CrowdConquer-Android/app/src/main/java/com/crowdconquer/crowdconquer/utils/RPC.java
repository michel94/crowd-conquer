package com.crowdconquer.crowdconquer.utils;

import com.neovisionaries.ws.client.WebSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by michel on 9/26/15.
 */
public class RPC {
    private WebSocket ws;
    private ConcurrentSkipListMap<Integer, RPCCallback> callbackMap;
    private int callbackCount = 1;

    public RPC(){

    }
    public RPC(WebSocket ws){
        this.ws = ws;
        callbackMap = new ConcurrentSkipListMap<Integer, RPCCallback>();

    }

    private JSONObject genJson(String name, RPCCallback callback, String[] names, JSONable... args){

        JSONObject jRoot = new JSONObject();
        try {
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

    public void handleResponse(int id, Object response){
        callbackMap.get(id).action(response);
    }

    public void hello(RPCCallback callback){
        JSONObject jRoot = genJson("hello", callback, new String[0]);


        ws.sendText(jRoot.toString());

    }

    public int registerCallback(RPCCallback callback){
        callbackMap.put(callbackCount++, callback);
        return callbackCount-1;
    }
}

