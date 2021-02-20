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
        return newFixedLengthResponse(PageGenerator.generate());
        //return newFixedLengthResponse(msg.toString() + "</body></html>\n");
    }
    
    public static void add(Text text) {
        msg.append(text.toHtml());
    }
}
