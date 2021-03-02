package com.situ2001.cliptokindle.bean;

import com.situ2001.cliptokindle.bean.text.Text;
import com.situ2001.cliptokindle.util.Serializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

public class DisplayableList implements Serializable {
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

    public static DisplayableList loadFromFile() {
        return (DisplayableList) new Serializer("list.dat").load();
    }
}
