package com.crowdconquer.crowdconquer.utils;

import android.os.Handler;
import android.os.Message;

import com.crowdconquer.crowdconquer.utils.RPC;

import java.lang.ref.WeakReference;

/**
 * Created by michel on 9/27/15.
 */

public class ExtendedHandler extends Handler {
    private RPC rpc;

    public void setRPC(RPC rpc) {
        this.rpc = rpc;
    }

    public ExtendedHandler() {
    }

    @Override
    public void handleMessage(Message message) {
        int id = message.getData().getInt("callbackId");
        RPCCallback callback = rpc.getCallback(id);
        Object response = rpc.getResponse(id);

        callback.action(response);
    }
}