package com.situ2001.cliptokindle.bean;

import android.net.Uri;

import com.situ2001.cliptokindle.util.Serializer;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DisplayableList implements Serializable {
    private java.util.List<Displayable> list;
    private java.util.Map<String, Uri> fileMap;
    private final Serializer serializer;

    public DisplayableList() {
        serializer = new Serializer("DisplayableList.dat");
        list = (ArrayList<Displayable>) serializer.load();
        if (list == null) {
            list = new ArrayList<>();
        }
    }

    public void add(Displayable o) {
        if (o != null && !this.contains(o)) {
            list.add(o);
            serializer.save(list);
        }
    }

    public void remove(int index) {
        fileMap.remove(list.get(index).getTitle());
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
        return list.stream().anyMatch(e -> e.equals(t));
    }

    public Map<String, Uri> getMap() {
        if (fileMap == null) {
            fileMap = new HashMap<>();
        }
        return fileMap;
    }
}
