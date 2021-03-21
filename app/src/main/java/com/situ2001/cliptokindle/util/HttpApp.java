package com.situ2001.cliptokindle.util;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fi.iki.elonen.NanoHTTPD;

/**
 * The class derived from NanoHTTPD is used to serving a static page.
 */
public class HttpApp extends NanoHTTPD {
    private Map<String, InputStream> fileMap;

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
        Map<String, List<String>> params = session.getParameters();
        if (params.get("book") == null && params.size() == 0) {
            return newFixedLengthResponse(PageGenerator.getPageGenerator().generate());
        } else if (fileMap.containsKey(Objects.requireNonNull(params.get("book")).get(0))) {
            String key = Objects.requireNonNull(params.get("book")).get(0);
            InputStream fis = fileMap.get(key);

            int size = 0;
            try {
                assert fis != null;
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newFixedLengthResponse(Response.Status.OK, "application/pdf", fis, size);
        } else {
            return newFixedLengthResponse("Nothing");
        }
    }

    // update a map when a Book was removed or added
    public void updateFileMap(Map<String, InputStream> map) {
        this.fileMap = map;
    }
}
