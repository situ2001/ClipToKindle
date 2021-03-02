package com.situ2001.cliptokindle.bean;

public class SingletonDisplayableList {
    private static DisplayableList singletonList;

    public static synchronized DisplayableList getSingleton() {
        if (singletonList == null) {
            singletonList = new DisplayableList();
        }
        return singletonList;
    }

    private SingletonDisplayableList() {

    }
}
