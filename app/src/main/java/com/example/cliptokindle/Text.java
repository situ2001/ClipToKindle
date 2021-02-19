package com.example.cliptokindle;

//link html object
public class Text {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    public String toHtml() {
        return "<div><a href=\"" + text + "\" target=\"_blank\"><b>" + text + "</b></a>" + "</div><br><br><br>";
    }
}
