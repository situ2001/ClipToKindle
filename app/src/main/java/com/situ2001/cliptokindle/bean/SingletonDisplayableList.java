package com.situ2001.cliptokindle.bean;

import com.situ2001.cliptokindle.util.Serializer;

import java.io.Serializable;
import java.util.ArrayList;

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

    public static class DisplayableList implements Serializable {
        private java.util.List<Displayable> list;
        private final Serializer serializer;

        public DisplayableList() {
            list = new ArrayList<>();
            serializer = new Serializer("list.dat");
        }

        public void add(Displayable o) {
            list.add(o);
            serializer.save(list);
        }

        public void remove(int index) {
            list.remove(index);
            serializer.save(list);
        }

        public Displayable get(int index) {
            return list.get(index);
        }

        public void show() {
            list.forEach(System.out::print);
        }

        public static DisplayableList loadFromFile() {
            return (DisplayableList) new Serializer("list.dat").load();
        }
    }
}
