package ru.skillbranch.devintensive.extensions

import java.lang.StringBuilder

fun String.truncate(count: Int = 16): String {
    val text = this.trim()
    return if (text.length > count) "${text.substring(0, count).trim()}..." else text
}

fun String.stripHtml(): String {
    val stringBuilder = StringBuilder()
    var isOpenTag = true
    var isSpace = false
    this.forEach { c ->
        when {
            c == '<' -> isOpenTag = false
            c == '>' -> isOpenTag = true
            c == '&' -> isOpenTag = false
            c == ';' -> isOpenTag = true
            c == '"' -> none()
            c == '\'' -> none()
            isOpenTag -> {
                when {
                    c.isWhitespace() -> {
                        if (!isSpace) {
                            stringBuilder.append(c)
                            isSpace = true
                        }
                    }
                    else -> {
                        stringBuilder.append(c)
                        isSpace = false
                    }
                }
            }
        }
    }
    return stringBuilder.toString().trim()
}
