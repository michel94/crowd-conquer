package com.crowdconquer.crowdconquer.services;

import com.crowdconquer.crowdconquer.utils.Request;

public class Api {
    public static void sendLocation() {
        Request request = new Request("post", "location");
        request.execute();
    }
}
