package divascion.marfiandhi.footballmatchschedule.View

import divascion.marfiandhi.footballmatchschedule.Model.Team.Team

/**
 * Created by Marfiandhi on 9/15/2018.
 */
interface EventDetailsView {
    fun showTeamDetails(data: List<Team>, name: String)
}