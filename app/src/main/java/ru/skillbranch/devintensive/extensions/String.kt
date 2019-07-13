package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String {
    val text = this.trim()
    return if (text.length > count) "${text.substring(0, count).trim()}..." else text
}