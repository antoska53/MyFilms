package ru.myacademyhomework.myfilms.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.data.Actor

class ActorAdapter(var actors: List<Actor>?): RecyclerView.Adapter<ActorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(actors?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return actors?.size!!
    }
}