package ru.myacademyhomework.myfilms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter: RecyclerView.Adapter<MovieViewHolder>() {
    private var listMovie = listOf("avengers", "tenet", "black widow", "wonder woman")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        TODO("Not yet implemented")
        var view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent,false)
        MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        TODO("Not yet implemented")
        holder.onBind(listMovie.get(position))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        listMovie.size
    }
}