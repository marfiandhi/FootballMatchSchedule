package divascion.marfiandhi.footballmatchschedule.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import divascion.marfiandhi.footballmatchschedule.Model.Events.Schedule
import divascion.marfiandhi.footballmatchschedule.R
import kotlinx.android.synthetic.main.item_list.view.*
import java.text.SimpleDateFormat

/**
 * Created by Marfiandhi on 9/12/2018.
 */
class MainAdapter(private val context: Context, private val events: List<Schedule>, private val listener: (Schedule) -> Unit)
    : RecyclerView.Adapter<MainAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

        @SuppressLint("SimpleDateFormat")
        fun bindItem(events: Schedule, listener: (Schedule) -> Unit) {
            itemView.TeamA.text = events.home
            itemView.TeamB.text = events.away
            if(events.homeScore!=null){
                itemView.scoreTeamA.text = events.homeScore.toString()
                itemView.scoreTeamB.text = events.awayScore.toString()
            } else {
                itemView.scoreTeamA.text = ""
                itemView.scoreTeamB.text = ""
            }
            val dateEvent = SimpleDateFormat("EEE, dd MMM yyyy").format(events.date).toString()
            itemView.date.text = dateEvent

            itemView.setOnClickListener { listener(events) }

        }
    }

}
