package ru.skillbranch.devintensive.extensions

import java.lang.StringBuilder

fun String.truncate(count: Int = 16): String {
    val text = this.trim()
    return if (text.length > count) "${text.substring(0, count).trim()}..." else text
}

fun String.stripHtml(): String {
    val stringBuilder = StringBuilder()
    var isTest = true
    var countSpace = 0
    this.forEach { c ->
        if (c.isWhitespace()) countSpace++ else countSpace = 0
        when {
            c == '<' -> isTest = false
            c == '>' -> isTest = true
            c == '&' -> isTest = false
            c == ';' -> isTest = true
            c == '"' -> none()
            c == '\'' -> none()
            isTest.and((countSpace <= 1)) -> stringBuilder.append(c)
        }
    }
    return stringBuilder.toString().trim()
}