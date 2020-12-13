package ru.myacademyhomework.myfilms

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var ivActor: ImageView = itemView.findViewById(R.id.iv_actor)
    private var tvActorName: TextView = itemView.findViewById(R.id.tv_actor_name)

    fun onBind(nameActor: String) {
        when (nameActor) {
            "Robert Downey Jr" -> {
                ivActor.setImageResource(R.drawable.robert_downey)
                tvActorName.setText(R.string.robert_downey_jr)
            }
            "Mark Ruffalo" -> {
                ivActor.setImageResource(R.drawable.mark_ruffalo)
                tvActorName.setText(R.string.mark_ruffalo)
            }
            "Chris Evans" -> {
                ivActor.setImageResource(R.drawable.chris_evans)
                tvActorName.setText(R.string.chris_evans)
            }
            "Chris Hemsworth" -> {
                ivActor.setImageResource(R.drawable.chris_hemsworth)
                tvActorName.setText(R.string.chris_hemsworth)
            }
        }
    }
}