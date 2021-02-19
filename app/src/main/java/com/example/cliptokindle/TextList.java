package com.example.cliptokindle;

import java.util.LinkedHashSet;
import java.util.function.Consumer;

public class TextList {
    private final LinkedHashSet<String> texts;

    public TextList() {
        texts = new LinkedHashSet<>();
    }

    public void add(String s) {
        texts.add(s);
    }

    public void forEach(Consumer<? super String> c) {
        texts.forEach(c);
    }
}
