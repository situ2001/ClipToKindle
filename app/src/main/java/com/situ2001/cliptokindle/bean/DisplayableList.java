package com.situ2001.cliptokindle.bean;

import com.situ2001.cliptokindle.bean.text.Text;
import com.situ2001.cliptokindle.util.Serializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

public class DisplayableList implements Serializable {
    private java.util.List<Displayable> list;
    private final Serializer serializer;

    public DisplayableList() {
        serializer = new Serializer("list.dat");
        list = (ArrayList<Displayable>) serializer.load();
        if (list == null) {
            list = new ArrayList<>();
        }
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

    public int size() {
        return list.size();
    }

    public void forEach(Consumer<? super Displayable> c) {
        list.forEach(c);
    }

    public boolean contains(Text t) {
        return list.stream().anyMatch(text -> ((Text) text).getText().equals(t.getText()));
    }

    public void show() {
        list.forEach(System.out::print);
    }
}
