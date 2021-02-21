package com.situ2001.cliptokindle.text;

import com.situ2001.cliptokindle.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TextSet implements Serializable {
    private List<Text> texts;

    public TextSet() {
        texts = new ArrayList<>();
    }

    public void add(Text t) {
        if (t.getText().trim().matches("http[\\w]?://.*")) {
            if (!contains(t)) {
                texts.add(t);
                save();
            }
        }
    }

    public void remove(int i) {
        texts.remove(i);
        this.save();
    }

    public boolean contains(Text t) {
        return texts.stream().anyMatch(text -> text.getText().equals(t.getText()));
    }

    public void forEach(Consumer<? super Text> c) {
        texts.forEach(c);
    }

    public List<Text> getList() {
        return texts;
    }

    public void save() {
        new Serializer(new File(Utils.getStoragePath(), "TextSet.dat")).save(this.texts);
    }

    public void load() {
        Object o = new Serializer(new File(Utils.getStoragePath(), "TextSet.dat")).load();
        if (o instanceof ArrayList) {
            this.texts = (ArrayList<Text>) o;
        }
    }

    static class Serializer {
        private final File file;

        Serializer(File file) {
            this.file = file;
        }

        public void save(Object o) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Object load() {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                return in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
