package ru.myacademyhomework.myfilms


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageMovie: ImageView = itemView.findViewById(R.id.iv_avengers_movie)
    private val tvNameMovie: TextView = itemView.findViewById(R.id.tv_name)

    fun onBind(nameMovie: String, listener: MovieListListener?) {
        itemView.setOnClickListener(View.OnClickListener {
            listener?.itemClicked(FragmentMoviesDetails.newInstance(nameMovie))
        })
        imageMovie.setImageResource(R.drawable.avengers_movie)
        when (nameMovie) {
            "avangers" -> {
                imageMovie.setImageResource(R.drawable.avengers_movie)
                tvNameMovie.setText(R.string.movie_name_avengers)
            }
            "tenet" -> {
                imageMovie.setImageResource(R.drawable.tenet_movie)
                tvNameMovie.setText(R.string.movie_name_tenet)
            }
            "black widow" -> {
                imageMovie.setImageResource(R.drawable.black_widow_movie)
                tvNameMovie.setText(R.string.movie_name_black_widow)
            }
            "wonder woman" -> {
                imageMovie.setImageResource(R.drawable.wonder_woman_movie)
                tvNameMovie.setText(R.string.movie_wonder_woman)
            }
        }
    }
}