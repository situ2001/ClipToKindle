package com.situ2001.cliptokindle.bean.text;

import java.util.List;

public class TextSetHelper {
    //private static final TextSet textSet = new TextSet();
    private static final TextSet textSet = new TextSet();

    public static TextSet get() {
        return textSet;
    }

    public static List<Text> getList() {
        return textSet.getList();
    }
}
