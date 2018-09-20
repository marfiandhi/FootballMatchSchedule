package divascion.marfiandhi.footballmatchschedule.model.favorite

/**
 * Created by Marfiandhi on 9/21/2018.
 */
data class Favorite(val id: Long?, val homeId: String?, val homeName: String?, val homeScore: String?, val awayId: String?,
                    val awayName: String?, val awayScore: String?, val date: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val HOME_ID: String = "HOME_ID"
        const val HOME_NAME: String = "HOME_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_ID: String = "AWAY_ID"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val DATE: String = "DATE"
    }
}