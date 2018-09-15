package divascion.marfiandhi.footballmatchschedule.Model.Team

import android.net.Uri
import divascion.marfiandhi.footballmatchschedule.BuildConfig

/**
 * Created by Marfiandhi on 9/15/2018.
 */
object EPLTeamDetailsApi {
    fun getDetails(target: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupteam.php")
                .appendQueryParameter("id", target)
                .build()
                .toString()
    }
}