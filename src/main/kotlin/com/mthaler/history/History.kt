package com.mthaler.history

import com.mthaler.history.utils.elementsAtStart
import com.mthaler.history.utils.removeAtStart
import com.mthaler.history.utils.truncate

class History<T>(val maximumSize: Long = Int.MAX_VALUE.toLong(), val removeSize: Int = 1) : Iterable<T> {

    private val data = ArrayList<T>()
    private var position = 0
    private var removalListener: RemovalListener<T>? = null

    fun add(element: T) {
        if (data.size + 1 >= maximumSize + removeSize) {
            removalListener?.let {
                val toRemove = data.elementsAtStart(removeSize)
                it.historyRemoved(toRemove)
            }
            data.removeAtStart(removeSize)
            position -= removeSize
        }
        if (position < data.size) {
            data.truncate(position)
        }
        data.add(element)
        position += 1
    }

    /**
     * Indicates if undo is possible
     */
    val canUndo: Boolean
        get() = position > 0

    fun undo() {
        if (canUndo) {
            position -= 1
        }
    }

    /**
     * Indicates if redo is possible
     */
    val canRedo: Boolean
        get() = position < data.size

    fun redo() {
        if (canRedo) {
            position += 1
        }
    }

    /**
     * Current element or null if the history is empty
     */
    val current: T?
        get() {
        if (position > 0) {
            return data[position - 1]
        } else {
            return null
        }
    }

    val past: List<T>
        get() {
            if (position < 2) {
                return emptyList()
            } else {
                return data.subList(0, position - 1)
            }
        }

    val future: List<T>
        get() {
            if (position < data.size) {
                return data.subList(position, data.size)
            } else {
                return emptyList()
            }
        }

    /**
     * Returns an iterator that iterates over the history. The iterator is only valid until the history is modified!
     *
     * @return iterator
     */
    override fun iterator(): Iterator<T> {
        return object: Iterator<T> {
            private var pos = 0

            override fun hasNext(): Boolean = pos < position

            override fun next(): T {
                if (!hasNext()) {
                    throw NoSuchElementException()
                } else {
                    val result = data[pos]
                    pos++
                    return result
                }
            }
        }
    }

    fun setRemovalListener(removalListener: RemovalListener<T>?) {
        this.removalListener = removalListener
    }

    companion object {

        operator fun <T>invoke(vararg elements: T): History<T> {
            val history = History<T>()
            for (elem in elements) {
                history.add(elem)
            }
            return history
        }
    }
}