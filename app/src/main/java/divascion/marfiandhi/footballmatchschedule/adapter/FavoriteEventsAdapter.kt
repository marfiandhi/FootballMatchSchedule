package divascion.marfiandhi.footballmatchschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import divascion.marfiandhi.footballmatchschedule.R
import divascion.marfiandhi.footballmatchschedule.model.favorite.Favorite
import kotlinx.android.synthetic.main.item_list.view.*

/**
 * Created by Marfiandhi on 9/21/2018.
 */
class FavoriteEventsAdapter(private val context: Context, private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteEventsAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
            itemView.TeamA.text = favorite.home
            itemView.TeamB.text = favorite.away
            if(favorite.homeScore!="null"){
                itemView.scoreTeamA.text = favorite.homeScore.toString()
                itemView.scoreTeamB.text = favorite.awayScore.toString()
            } else {
                itemView.scoreTeamA.text = ""
                itemView.scoreTeamB.text = ""
            }
            itemView.date.text = favorite.date

            itemView.setOnClickListener { listener(favorite) }
        }
    }

}