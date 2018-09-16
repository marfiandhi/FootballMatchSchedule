package divascion.marfiandhi.footballmatchschedule.view

import divascion.marfiandhi.footballmatchschedule.model.team.Team

/**
 * Created by Marfiandhi on 9/15/2018.
 */
interface EventDetailsView {
    fun showTeamDetails(data: List<Team>, name: String)
}