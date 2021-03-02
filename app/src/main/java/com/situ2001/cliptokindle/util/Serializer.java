package com.situ2001.cliptokindle.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer {
    private final File file;

    public Serializer(String filename) {
        this.file = new File(Utils.getStoragePath(), filename);
    }

    private Serializer(File file) {
        this.file = file;
    }

    public void save(Object o) {
        if (o instanceof Serializable) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(o);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
