package com.situ2001.cliptokindle.text

object TextSetHelper {
    //private static final TextSet textSet = new TextSet();
    private val textSet: TextSet = TextSet()
    fun get(): TextSet {
        return textSet
    }

    fun getList(): MutableList<Text> {
        return textSet.getList()
    }
}