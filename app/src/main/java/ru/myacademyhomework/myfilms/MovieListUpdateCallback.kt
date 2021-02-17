package ru.myacademyhomework.myfilms

import androidx.recyclerview.widget.ListUpdateCallback
import ru.myacademyhomework.myfilms.movie.MovieAdapter

class MovieListUpdateCallback(private val adapter: MovieAdapter) : ListUpdateCallback {
    var firstInsert: Int = -1
        private set

    override fun onInserted(position: Int, count: Int) {
        if (firstInsert == -1 || firstInsert > position) {
            firstInsert = position
        }
        adapter.notifyItemRangeInserted(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        if (firstInsert == -1 || firstInsert > position) {
            firstInsert = position
        }
        adapter.notifyItemRangeRemoved(position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {

        adapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        if (firstInsert == -1 || firstInsert > position) {
            firstInsert = position
        }
        adapter.notifyItemRangeChanged(position, count)
    }
}