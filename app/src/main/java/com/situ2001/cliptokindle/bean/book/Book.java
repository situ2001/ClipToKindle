package com.situ2001.cliptokindle.bean.book;

import android.net.Uri;

import androidx.annotation.Nullable;

import com.situ2001.cliptokindle.bean.Displayable;
import com.situ2001.cliptokindle.bean.text.Text;

import java.io.Serializable;

public class Book implements Serializable, Displayable {
    private String name;
    private String path;

    public Book(Uri uri, String name) {
        this.path = uri.toString(); // when you want to use Uri to retrieve the book from local store, use Uri.parse
        this.name = name;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String toHtml() {
        return "<div><a href=\"" + "/?book=" + name + "\" target=\"_blank\"><b>" + name + "</b></a>" + "</div><br><br><br>\n";
    }

    public Uri toUri() {
        return Uri.parse(path); // return a Uri for the subsequent usage (open as a InputStream
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Book) {
            return name.equals(((Book) o).name);
        }

        //If the o passed into this method is not a Book
        return false;
    }
}
