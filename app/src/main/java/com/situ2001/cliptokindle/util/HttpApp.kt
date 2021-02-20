package com.situ2001.cliptokindle.util

import fi.iki.elonen.NanoHTTPD
import java.io.IOException

class HttpApp(port: Int) : NanoHTTPD(port) {
    @Throws(IOException::class)
    override fun start() {
        start(SOCKET_READ_TIMEOUT, true)
    }

    override fun serve(session: IHTTPSession): Response {
        return newFixedLengthResponse(PageGenerator.getPageGenerator()?.generate())
        //return newFixedLengthResponse(msg.toString() + "</body></html>\n");
    }
}