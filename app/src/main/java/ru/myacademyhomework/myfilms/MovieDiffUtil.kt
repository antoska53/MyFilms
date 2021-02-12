package ru.myacademyhomework.myfilms

import androidx.recyclerview.widget.DiffUtil
import ru.myacademyhomework.myfilms.data.Movie

class MovieDiffUtil(val oldList: List<Movie>, val newList: List<Movie>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).id == newList.get(newItemPosition).id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition))
    }
}