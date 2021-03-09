package com.mthaler.history

class History<T> {

    private val data = ArrayList<T>()
    private var position = 0

    fun iterator(): Iterator<T> {
        TODO("Not yet implemented")
    }

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
    fun current(): T? {
        TODO("Not yet implemented")
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