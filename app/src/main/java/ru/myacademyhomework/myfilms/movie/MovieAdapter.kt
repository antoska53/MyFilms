package ru.myacademyhomework.myfilms.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myacademyhomework.myfilms.MovieListListener
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.data.Movie

class MovieAdapter(private val listener: MovieListListener) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val listMovies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.onBind(listMovies.get(position), listener)
    }

    override fun getItemCount() = listMovies.size

    fun updateData(list: List<Movie>) {
        listMovies.clear()
        listMovies.addAll(list)
        //val oldSize = listMovies.size
        //notifyItemRangeChanged(0, list.size)
        notifyDataSetChanged()
    }

}