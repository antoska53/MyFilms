package ru.myacademyhomework.myfilms.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myacademyhomework.myfilms.R
import ru.myacademyhomework.myfilms.data.Actor
import ru.myacademyhomework.myfilms.data.Movie

class ActorAdapter(): RecyclerView.Adapter<ActorViewHolder>() {
    private var actors: List<Actor> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(actors.get(position))
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    fun updateData(listActors: List<Actor>){
        actors = listActors
        notifyDataSetChanged()
    }
}