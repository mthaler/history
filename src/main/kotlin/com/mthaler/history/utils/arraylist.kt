package com.mthaler.history.utils

/**
 * Truncates an array list to n elements by removing elements at the end of the list
 * If the new size is >= the old size the array list will not be changed
 *
 * @param newSize size the list should be truncated to
 */
fun <T> ArrayList<T>.truncate(newSize: Int) {
    val oldSize = this.size
    if (oldSize > newSize) {
        this.subList(newSize, oldSize).clear()
    }
}