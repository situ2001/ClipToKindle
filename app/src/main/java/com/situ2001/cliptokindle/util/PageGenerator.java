package com.situ2001.cliptokindle.util;

import androidx.annotation.NonNull;

import com.situ2001.cliptokindle.bean.DisplayableList;

/**
 * A simple tool for generating static web page
 */
public class PageGenerator {
    private static PageGenerator pageGenerator;

    private StringBuilder msg;
    private final DisplayableList displayableList;

    public static PageGenerator getPageGenerator() {
        return pageGenerator;
    }

    private PageGenerator(DisplayableList displayableList) {
        this.displayableList = displayableList;
    }

    /**
     * build a PageGenerator
     * @param list An DisplayList that will be used by PageGenerator
     */
    public synchronized static void build(DisplayableList list) {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator(list);
        }
    }

    @NonNull
    public String generate() {
        msg = new StringBuilder();

        begin();
        displayableList.forEach(text -> msg.append(text.toHtml()));
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
