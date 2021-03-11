package com.mthaler.history

fun interface RemovalListener<T> {

    fun historyRemoved(items: List<T>)
}
