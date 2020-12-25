package ru.myacademyhomework.myfilms

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.myacademyhomework.myfilms.data.Actor

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var ivActor: ImageView = itemView.findViewById(R.id.iv_actor)
    private var tvActorName: TextView = itemView.findViewById(R.id.tv_actor_name)

    fun onBind(actor: Actor) {
        Log.d(MovieViewHolder.TAG, "onBind: actorName " + actor.name)
        Log.d(MovieViewHolder.TAG, "onBind: actorPicture " + actor.picture)
        Glide.with(itemView)
            .load(actor.picture)
            .into(ivActor)

        tvActorName.setText(actor.name)
    }
}