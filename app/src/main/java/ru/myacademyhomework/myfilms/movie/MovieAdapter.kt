package ru.myacademyhomework.myfilms.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.bumptech.glide.util.ViewPreloadSizeProvider
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.MovieListListener
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.data.Movie

class MovieAdapter(
    private val listener: FragmentMoviesList,
//    private val sizeProvider: FixedPreloadSizeProvider<Movie>
    private val sizeProvider: ViewPreloadSizeProvider<Movie>
) :
    RecyclerView.Adapter<MovieViewHolder>(), ListPreloader.PreloadModelProvider<Movie> {

    private val _listMovies: MutableList<Movie> = mutableListOf()
    val listMovies: List<Movie> = _listMovies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieViewHolder = MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )
        sizeProvider.setView(movieViewHolder.imageMovie)
        return movieViewHolder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemView.transitionName = holder.itemView.context.getString(R.string.movie_card_transition_name) + position
        holder.onBind(_listMovies.get(position), listener)
    }

    override fun getItemCount() = _listMovies.size

    fun updateData(list: List<Movie>) {
        _listMovies.clear()
        _listMovies.addAll(list)
        //notifyDataSetChanged()
    }

    override fun getPreloadItems(position: Int): MutableList<Movie> {
        return _listMovies.subList(position, position + 1)
    }

    override fun getPreloadRequestBuilder(item: Movie): RequestBuilder<*>? {
        return Glide.with(listener).load(BuildConfig.BASE_IMAGE_URL + item.poster)
    }

}