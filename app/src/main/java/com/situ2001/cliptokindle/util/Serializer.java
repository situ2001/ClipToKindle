package com.situ2001.cliptokindle.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Provide convenience for serializing and deserializing
 */
public class Serializer {
    private final File file;

    /**
     * Construct a Serializer
     * @param filename The name of file which the object will be saved
     */
    public Serializer(String filename) {
        this.file = new File(Utils.getStoragePath(), filename);
    }

    /**
     * If an object is Serializable, it will be saved to local storage.
     * @param o The object to be saved
     */
    public void save(Object o) {
        if (o instanceof Serializable) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load the Object from the file.
     * @return An object read from the file or null if an IOException or ClassNotFoundException occurred
     */
    public Object load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //else
        return null;
    }
}
