package divascion.marfiandhi.footballmatchschedule.Model.Events

import divascion.marfiandhi.footballmatchschedule.BuildConfig

/**
 * Created by Marfiandhi on 9/12/2018.
 */
object TheSportDBApi {
    fun getSchedule(target: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/"+ target + "?id=4328"
    }
}