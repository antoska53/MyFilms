package ru.myacademyhomework.myfilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ActorAdapter: RecyclerView.Adapter<ActorViewHolder>() {
    private var listActor = listOf(
        "Robert Downey Jr",
        "Mark Ruffalo",
        "Chris Evans",
        "Chris Hemsworth",
        "Robert Downey Jr",
        "Mark Ruffalo",
        "Chris Evans",
        "Chris Hemsworth")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(listActor.get(position))
    }

    override fun getItemCount(): Int = listActor.size
}