package divascion.marfiandhi.footballmatchschedule.model.team

import divascion.marfiandhi.footballmatchschedule.BuildConfig

/**
 * Created by Marfiandhi on 9/15/2018.
 */
object EPLTeamDetailsApi {
    fun getDetails(target: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + target
    }
}