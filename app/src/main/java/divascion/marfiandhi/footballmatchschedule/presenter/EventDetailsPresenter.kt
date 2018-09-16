package divascion.marfiandhi.footballmatchschedule.presenter

import com.google.gson.Gson
import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.team.EPLTeamDetailsApi
import divascion.marfiandhi.footballmatchschedule.model.team.TeamResponse
import divascion.marfiandhi.footballmatchschedule.view.EventDetailsView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Marfiandhi on 9/15/2018.
 */
class EventDetailsPresenter(private val view: EventDetailsView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson) {
    fun getDetails(team: String) {
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(EPLTeamDetailsApi.getDetails(team)),
                    TeamResponse::class.java
            )

            uiThread {
                view.showTeamDetails(data.teams, team)
            }
        }
    }
}