package com.example.myapplication

import androidx.lifecycle.ViewModel

class RectanglesViewModel : ViewModel() {
    val items = mutableListOf<Int>()

    fun addItem(obj: Int) {
        items += obj
    }

    fun delete(obj: Int) {
        items.remove(obj)
    }
}
