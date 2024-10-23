package com.knu.mockin.util

infix fun String.tag(tag: String): String {
    return "$this-$tag"
}