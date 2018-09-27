package divascion.marfiandhi.footballmatchschedule.presenter

import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.events.ScheduleResponse
import divascion.marfiandhi.footballmatchschedule.model.events.TheSportDBApi
import divascion.marfiandhi.footballmatchschedule.view.MainView
import divascion.marfiandhi.footballmatchschedule.utils.*
import kotlinx.coroutines.experimental.async
import com.google.gson.Gson
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by Marfiandhi on 9/12/2018.
 */
class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getSchedule(event: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getSchedule(event)),
                        ScheduleResponse::class.java
                )
            }
            view.hideLoading()
            view.showSchedule(data.await().events)
        }
    }
}
