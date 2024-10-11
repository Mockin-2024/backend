package com.knu.mockin

infix fun <T> T.means(description: String): Pair<T, String> {
    return Pair(this, description)
}
