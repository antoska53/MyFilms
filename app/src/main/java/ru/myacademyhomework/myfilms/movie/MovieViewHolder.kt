package ru.myacademyhomework.myfilms.movie


import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.MovieListListener
import ru.myacademyhomework.myfilms.movieDetails.FragmentMoviesDetails
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.data.Movie

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageMovie: ImageView = itemView.findViewById(R.id.iv_avengers_movie)
    private val tvNameMovie: TextView = itemView.findViewById(R.id.tv_name)
    private val tvReview: TextView = itemView.findViewById(R.id.review)
    private val tvGenre: TextView = itemView.findViewById(R.id.movieGenre)
    private val tvRuntime: TextView = itemView.findViewById(R.id.tv_runtime)
    private val tvMinimumAge: TextView = itemView.findViewById(R.id.tv_minimum_age)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)


    fun onBind(movie: Movie, listener: MovieListListener) {
        itemView.setOnClickListener{
            listener.itemClicked(movie.id, it)
        }

        Glide.with(itemView)
            .load(BuildConfig.BASE_IMAGE_URL + movie.poster)
          //  .placeholder(R.drawable.ic_launcher_foreground)
            .into(imageMovie)

        tvNameMovie.text = movie.title
        tvGenre.text = movie.genresString
        tvMinimumAge.text = movie.minimumAge.toString() + "+"
        tvReview.text = movie.numberOfRatings.toString() + " REVIEWS"
        tvRuntime.text = movie.runtime.toString() + " MIN"
        ratingBar.rating = movie.ratings.toFloat()
    }
}