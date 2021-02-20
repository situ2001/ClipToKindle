package com.example.cliptokindle;

import androidx.annotation.NonNull;

public class PageGenerator {
    private static StringBuilder msg;
    private static TextSet textSet;

    public static void setTextSet(TextSet t) {
        textSet = t;
    }

    @NonNull
    public static String generate() {
        msg = new StringBuilder();

        begin();
        textSet.forEach(text -> msg.append(text.toHtml()));
        end();

        return msg.toString();
    }

    private static void begin() {
        msg.append("<html><body><h1>Clip to Kindle</h1>\n");
        msg.append("<a href=\"/\"><h2>Reload this page</h2></a>\n<br>\n");
        msg.append("<h2>Links</h2>\n");
    }

    private static void end() {
        msg.append("</body></html>\n");
    }
}
