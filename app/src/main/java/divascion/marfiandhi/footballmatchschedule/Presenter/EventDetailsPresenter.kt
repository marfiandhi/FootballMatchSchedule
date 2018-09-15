package divascion.marfiandhi.footballmatchschedule.Presenter

import com.google.gson.Gson
import divascion.marfiandhi.footballmatchschedule.Model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.Model.Team.EPLTeamDetailsApi
import divascion.marfiandhi.footballmatchschedule.Model.Team.TeamResponse
import divascion.marfiandhi.footballmatchschedule.View.EventDetailsView
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