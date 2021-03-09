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

    fun current(): T? {
        TODO("Not yet implemented")
    }
}