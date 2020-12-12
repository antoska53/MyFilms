package ru.myacademyhomework.myfilms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private val listener: FragmentMoviesList.Companion.Listener?): RecyclerView.Adapter<MovieViewHolder>() {
    private var listMovie = listOf("avengers", "tenet", "black widow", "wonder woman")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(listMovie.get(position), listener)

    }

    override fun getItemCount(): Int {
        return listMovie.size
    }


}