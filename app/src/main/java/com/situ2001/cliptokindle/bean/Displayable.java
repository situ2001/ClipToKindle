package com.situ2001.cliptokindle.bean;

/**
 * The object that can be displayed at HTML page and RecyclerView
 */
public interface Displayable {
    String getTitle();
    String toHtml();
}
