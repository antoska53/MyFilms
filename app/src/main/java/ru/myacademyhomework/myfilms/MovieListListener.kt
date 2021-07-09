package ru.myacademyhomework.myfilms

import android.view.View

interface MovieListListener {
    fun itemClicked(id: Int, cardView: View)
}