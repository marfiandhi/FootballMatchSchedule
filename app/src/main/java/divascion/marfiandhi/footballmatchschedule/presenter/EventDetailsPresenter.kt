package divascion.marfiandhi.footballmatchschedule.presenter

import com.google.gson.Gson
import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.team.EPLTeamDetailsApi
import divascion.marfiandhi.footballmatchschedule.model.team.TeamResponse
import divascion.marfiandhi.footballmatchschedule.utils.CoroutineContextProvider
import divascion.marfiandhi.footballmatchschedule.view.EventDetailsView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by Marfiandhi on 9/15/2018.
 */
class EventDetailsPresenter(private val view: EventDetailsView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getDetails(team: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(EPLTeamDetailsApi.getDetails(team)),
                        TeamResponse::class.java
                )
            }
            view.hideLoading()
            view.showTeamDetails(data.await().teams, team)
        }
    }
}