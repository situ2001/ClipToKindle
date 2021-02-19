package com.example.cliptokindle;

import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class HttpApp extends NanoHTTPD {
    private static final StringBuilder msg = new StringBuilder();

    public HttpApp() {
        super(8080);
        init();
    }

    @Override
    public void start() throws IOException {
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, true);
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public Response serve(IHTTPSession session) {
        return newFixedLengthResponse(msg.toString() + "</body></html>\n");
    }

    public void init() {
        msg.append("<html><body><h1>Clip to Kindle</h1>\n");
        msg.append("<a href=\"/\"><h2>Reload this page</h2></a>\n<br>\n");
        msg.append("<h2>Links</h2>");
    }
    
    public static void add(Text text) {
        msg.append(text.toHtml());
    }
}
