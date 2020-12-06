package ru.myacademyhomework.myfilms


import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val imageMovie: ImageView = itemView.findViewById(R.id.iv_avengers_movie)

    fun onBind(nameMovie : String){
//        when(nameMovie){
//            "avangers" -> imageMovie.setImageResource(R.drawable.avengers_movie)
//            "tenet" -> imageMovie.setImageResource(R.drawable.tenet_movie)
//            "black widow" -> imageMovie.setImageResource(R.drawable.black_widow_movie)
//            "wonder woman" -> imageMovie.setImageResource(R.drawable.wonder_woman_movie)
//        }
    }
}