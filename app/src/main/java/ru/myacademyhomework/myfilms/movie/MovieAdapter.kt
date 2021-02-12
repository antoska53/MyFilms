package ru.myacademyhomework.myfilms.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.data.Movie

class MovieAdapter(private val listener: FragmentMoviesList.Companion.Listener?): RecyclerView.Adapter<MovieViewHolder>() {

    val listMovies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent,false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(listMovies.get(position), listener)
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    fun updateData(list: List<Movie>){
        listMovies.clear()
        listMovies.addAll(list)
    }

}