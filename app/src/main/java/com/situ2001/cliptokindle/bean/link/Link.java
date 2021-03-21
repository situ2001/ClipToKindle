package com.situ2001.cliptokindle.bean.link;

import com.situ2001.cliptokindle.bean.Displayable;

import java.io.Serializable;

public class Link implements Serializable, Displayable {
    private final String text;

    private Link(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String toHtml() {
        return "<div><a href=\"" + text + "\" target=\"_blank\"><b>" + text + "</b></a>" + "</div><br><br><br>\n";
    }

    @Override
    public String getTitle() {
        return text;
    }

    public static Link build(String text) {
        if (text != null) {
            return new Link(text);
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Link) {
            return text.equals(((Link) o).getText());
        }

        //If the o passed into this method is not a Text
        return false;
    }
}
