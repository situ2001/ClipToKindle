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
        serializer = new Serializer("DisplayableList.dat");
        list = (ArrayList<Displayable>) serializer.load();
        if (list == null) {
            list = new ArrayList<>();
        }
    }

    public void add(Displayable o) {
        if (o != null) {
            list.add(o);
            serializer.save(list);
        }
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

    public boolean contains(Displayable t) {
        for (Displayable e : list) {
            if (e.equals(t))
                return true;
        }

        return false;
    }
}
