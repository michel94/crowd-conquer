package com.crowdconquer.crowdconquer.network;

import com.crowdconquer.crowdconquer.utils.JSONable;
import com.crowdconquer.crowdconquer.utils.RPCCallback;
import com.neovisionaries.ws.client.WebSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentSkipListMap;


public class RPC {
    private WebSocket ws;
    private ConcurrentSkipListMap<Integer, RPCCallback> callbackMap;
    private ConcurrentSkipListMap<Integer, Object> responseMap;
    private int callbackCount = 1;

    public RPC(){

    }

    public RPC(WebSocket ws){
        this.ws = ws;
        callbackMap = new ConcurrentSkipListMap<Integer, RPCCallback>();
        responseMap = new ConcurrentSkipListMap<Integer, Object>();
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

    public RPCCallback getCallback(int id){
        return callbackMap.get(id);
    }

    public int registerCallback(RPCCallback callback){
        callbackMap.put(callbackCount++, callback);
        return callbackCount-1;
    }

    public boolean addResponse(int id, Object response){
        if(callbackMap.containsKey(id)) {
            responseMap.put(id, response);
            return true;
        }
        return false;


    }

    public Object getResponse(int id){
        return responseMap.get(id);
    }

    public void hello(RPCCallback callback){
        JSONObject jRoot = genJson("hello", callback, new String[0]);


        ws.sendText(jRoot.toString());

    }
}

