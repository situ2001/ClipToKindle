package com.situ2001.cliptokindle.util

import com.situ2001.cliptokindle.text.Text
import com.situ2001.cliptokindle.text.TextSet

class PageGenerator private constructor(private val textSet: TextSet) {
    fun generate(): String {
        val msg = StringBuilder()
        begin(msg)
        textSet.forEach { msg.append(it.toHtml()) }
        end(msg)
        return msg.toString()
    }

    private fun begin(msg: StringBuilder) {
        msg.append("<html><body><h1>Clip to Kindle</h1>\n")
        msg.append("<a href=\"/\"><h2>Reload this page</h2></a>\n<br>\n")
        msg.append("<h2>Links</h2>\n")
    }

    private fun end(msg: StringBuilder) {
        msg.append("</body></html>\n")
    }

    companion object {
        private var pageGenerator: PageGenerator? = null
        fun getPageGenerator(): PageGenerator? {
            return pageGenerator
        }

        @Synchronized
        fun build(textSet: TextSet) {
            if (pageGenerator == null) {
                pageGenerator = PageGenerator(textSet)
            }
        }
    }
}