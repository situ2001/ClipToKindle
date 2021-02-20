package com.situ2001.cliptokindle.text

import com.situ2001.cliptokindle.util.Utils
import java.io.*
import java.util.*
import java.util.function.Consumer

class TextSet : Serializable {
    private var texts: MutableList<Text>
    fun add(s: Text) {
        if (!texts.contains(s)) {
            texts.add(s)
            save()
        }
    }

    fun remove(i: Int) {
        texts.removeAt(i)
        save()
    }

    fun forEach(c: Consumer<in Text>) {
        texts.forEach(c)
    }

    fun getList(): MutableList<Text> {
        return texts
    }

    fun save() {
        println(this.javaClass.typeName)
        Serializer(File(Utils.getStoragePath(), "TextSet.dat")).save(texts)
    }

    fun load() {
        val o = Serializer(File(Utils.getStoragePath(), "TextSet.dat")).load()
        if (o is ArrayList<*>) {
            texts = o as ArrayList<Text>
        }
    }

    internal class Serializer(private val file: File?) {
        fun save(o: Any?) {
            try {
                ObjectOutputStream(FileOutputStream(file)).use { it.writeObject(o) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun load(): Any? {
            try {
                ObjectInputStream(FileInputStream(file)).use { return it.readObject() }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
            return null
        }
    }

    init {
        texts = ArrayList()
    }
}