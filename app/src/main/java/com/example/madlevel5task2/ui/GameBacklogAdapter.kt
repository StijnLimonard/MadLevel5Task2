package com.example.madlevel5task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.model.Game

class GameBacklogAdapter(private val gamesList: List<Game>) :
    RecyclerView.Adapter<GameBacklogAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.findViewById<TextView>(R.id.tv_title).text = game.title
            itemView.findViewById<TextView>(R.id.tv_platform).text = game.platform
            itemView.findViewById<TextView>(R.id.tv_date).text = itemView.context.resources.getString(R.string.release_date, game.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(gamesList[position])

    override fun getItemCount(): Int = gamesList.size
}