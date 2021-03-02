package com.situ2001.cliptokindle.bean.text;

import com.situ2001.cliptokindle.bean.Displayable;

import java.io.Serializable;

public class Text implements Serializable, Displayable {
    private final String text;

    public Text(String text) {
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
}
