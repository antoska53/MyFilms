package ru.myacademyhomework.myfilms.movieDetails

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myacademyhomework.myfilms.BuildConfig
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.movie.MovieViewHolder

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var ivActor: ImageView = itemView.findViewById(R.id.iv_actor)
    private var tvActorName: TextView = itemView.findViewById(R.id.tv_actor_name)

    fun onBind(actor: Actor) {
        Glide.with(itemView)
            .load(BuildConfig.BASE_IMAGE_URL +  actor.picture)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(ivActor)

        tvActorName.setText(actor.name)
    }
}