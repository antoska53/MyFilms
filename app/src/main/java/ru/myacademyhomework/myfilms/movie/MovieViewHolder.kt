package ru.myacademyhomework.myfilms.movie


import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.movieDetails.FragmentMoviesDetails
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.network.MovieNetworkModel

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageMovie: ImageView = itemView.findViewById(R.id.iv_avengers_movie)
    private val tvNameMovie: TextView = itemView.findViewById(R.id.tv_name)
    private val tvReview :TextView = itemView.findViewById(R.id.review)
    private val tvGenre :TextView = itemView.findViewById(R.id.movieGenre)
    private val tvRuntime :TextView = itemView.findViewById(R.id.tv_runtime)
    private val tvMinimumAge :TextView = itemView.findViewById(R.id.tv_minimum_age)
    private val ratingBar :RatingBar = itemView.findViewById(R.id.rating_bar)


    companion object{
        val TAG :String = "TAG"
    }

    fun onBind(movie: MovieNetworkModel, listener: FragmentMoviesList.Companion.Listener?) {
        itemView.setOnClickListener(View.OnClickListener {
            listener?.itemClicked(FragmentMoviesDetails.newInstance(movie.movieId, ""))
        })

        Glide.with(itemView.context)
            .load(BuildConfig.BASE_IMAGE_URL + movie.posterPath)
            .into(imageMovie)

        tvNameMovie.text = movie.title
        //tvGenre.text = movie.genres.joinToString { genre -> genre.name }
        tvGenre.text = "tratata"
        tvMinimumAge.text = if(movie.adult) "16+" else "13+"
        tvReview.text = movie.voteCount.toString() + " REVIEWS"
        tvRuntime.text = "999 MIN" //movie.runtime.toString() + " MIN"
        ratingBar.rating = movie.voteAverage.toFloat() //movie.ratings / 2
    }
}