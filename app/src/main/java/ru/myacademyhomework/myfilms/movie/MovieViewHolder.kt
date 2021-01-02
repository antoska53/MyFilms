package ru.myacademyhomework.myfilms


import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.myacademyhomework.myfilms.data.Movie

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageMovie: ImageView = itemView.findViewById(R.id.iv_avengers_movie)
    private val tvNameMovie: TextView = itemView.findViewById(R.id.tv_name)
    private val tvReview :TextView = itemView.findViewById(R.id.review)
    private val tvGenre :TextView = itemView.findViewById(R.id.movieGenre)
    private val tvRuntime :TextView = itemView.findViewById(R.id.tv_runtime)
    private val tvMinimumAge :TextView = itemView.findViewById(R.id.tv_minimum_age)
    private val ratingBar :RatingBar = itemView.findViewById(R.id.rating_bar)
    private var scopeIO: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var scopeUI: CoroutineScope = CoroutineScope(Dispatchers.Main)

    companion object{
        public val TAG :String = "TAG"
    }

    fun onBind(movie: Movie, listener: FragmentMoviesList.Companion.Listener?) {
        itemView.setOnClickListener(View.OnClickListener {
            listener?.itemClicked(FragmentMoviesDetails.newInstance(movie.id, ""))
        })
        Log.d(TAG, "onBind: POSTER ${movie.poster}")


//        scopeIO.launch {
//           val rb = Glide.with(itemView)
//                        .load(movie.poster)
//            scopeUI.launch {
//                rb.into(imageMovie)
//            }
//        }
        Glide.with(itemView.context)
            .load(movie.poster)
            .into(imageMovie)


        //imageMovie.setImageResource(R.drawable.avengers_movie)
        tvNameMovie.setText(movie.title)
        tvGenre.setText(movie.genres?.joinToString { genre -> genre.name })
        tvMinimumAge.setText(movie.minimumAge.toString())
        tvReview.setText(movie.numberOfRatings.toString() + " REVIEWS")
        tvRuntime.setText(movie.runtime.toString() + " MIN")
        Log.d(TAG, "onBind: Movie: ${movie.title} Rating: ${movie.ratings / 2}  ")
        ratingBar.setRating(movie.ratings / 2)
    }
}