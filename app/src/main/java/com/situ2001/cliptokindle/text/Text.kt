package com.situ2001.cliptokindle.text

import java.io.Serializable

//link html object
class Text(val text: String) : Serializable {
    fun toHtml(): String {
        return "<div><a href=\"$text\" target=\"_blank\"><b>$text</b></a></div><br><br><br>\n"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Text) return other.text == text
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return text.hashCode()
    }
}