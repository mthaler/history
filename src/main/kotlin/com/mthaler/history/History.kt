package com.mthaler.history

class History<T> : Iterable<T> {

    private val data = ArrayList<T>()
    private var position = 0

    fun add(element: T) {
        if (position < data.size) {
            val s = data.size
            if (s > position) {
                data.subList(position, s).clear()
            }
        }
        data.add(element)
        position += 1
    }

    val canUndo: Boolean
        get() = position > 0

    fun undo() {
        if (canUndo) {
            position -= 1
        }
    }

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
        if (currentSize > 0) {
            return data[position - 1]
        } else {
            return null
        }
    }

    /**
     * Returns a boolean indicating if the history is empty
     *
     * @return boolean indicating if history is empty
     */
    fun isEmpty(): Boolean = position == 0

    /**
     * Current size of the history
     */
    val currentSize: Int
        get() = position


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