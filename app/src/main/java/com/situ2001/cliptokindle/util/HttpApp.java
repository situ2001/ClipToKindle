package com.situ2001.cliptokindle.util;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class HttpApp extends NanoHTTPD {

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
        return newFixedLengthResponse(PageGenerator.getPageGenerator().generate());
    }
}
