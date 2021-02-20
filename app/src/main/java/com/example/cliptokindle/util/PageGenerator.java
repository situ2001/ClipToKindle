package com.example.cliptokindle.util;

import androidx.annotation.NonNull;

import com.example.cliptokindle.text.TextSet;

public class PageGenerator {
    private static PageGenerator pageGenerator;

    private StringBuilder msg;
    private final TextSet textSet;

    public static PageGenerator getPageGenerator() {
        return pageGenerator;
    }

    private PageGenerator(TextSet textSet) {
        this.textSet = textSet;
    }

    public synchronized static void build(TextSet textSet) {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator(textSet);
        }
    }

    @NonNull
    public String generate() {
        msg = new StringBuilder();

        begin();
        textSet.forEach(text -> msg.append(text.toHtml()));
        end();

        return msg.toString();
    }

    private void begin() {
        msg.append("<html><body><h1>Clip to Kindle</h1>\n");
        msg.append("<a href=\"/\"><h2>Reload this page</h2></a>\n<br>\n");
        msg.append("<h2>Links</h2>\n");
    }

    private void end() {
        msg.append("</body></html>\n");
    }
}
