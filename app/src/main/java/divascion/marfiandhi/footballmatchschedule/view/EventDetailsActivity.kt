package divascion.marfiandhi.footballmatchschedule.view

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.team.Team
import divascion.marfiandhi.footballmatchschedule.presenter.EventDetailsPresenter
import divascion.marfiandhi.footballmatchschedule.R
import divascion.marfiandhi.footballmatchschedule.R.menu.*
import divascion.marfiandhi.footballmatchschedule.R.drawable.*
import divascion.marfiandhi.footballmatchschedule.R.id.add_to_favorite
import divascion.marfiandhi.footballmatchschedule.database.database
import divascion.marfiandhi.footballmatchschedule.model.events.Schedule
import divascion.marfiandhi.footballmatchschedule.model.favorite.Favorite
import kotlinx.android.synthetic.main.details_event.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat

/**
 * Created by Marfiandhi on 9/14/2018.
 */
class EventDetailsActivity: AppCompatActivity(), EventDetailsView {

    private var teams: MutableList<Team> = mutableListOf()

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var presenter: EventDetailsPresenter

    private var mHomeId: String = ""
    private var mAwayId: String = ""

    private var check = true

    private lateinit var itemCache: Schedule
    private lateinit var itemCacheFavorite: Favorite

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var item: Schedule
        lateinit var itemFavorite: Favorite

        check = intent.getBooleanExtra("boolean", true)

        if(check) {
            item = intent.getParcelableExtra("extra_item")
            itemCache = item

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val date = SimpleDateFormat("EEE, dd MMM yyyy").format(item.date).toString()

            mHomeId = item.idHome.toString()
            mAwayId = item.idAway.toString()

            setContentView(R.layout.details_event)

            swipeRefresh = detailsSwipeRefresh

            detailsDate.text = date

            homeName.text = item.home
            awayName.text = item.away

            if(item.homeScore!=null){
                homeScore.text = item.homeScore.toString()
                homeGoals.text = item.homeGoalDetails
                homeShots.text = item.homeShots.toString()+""
                homeGK.text = item.homeGoalKeeper
                homeDef.text = item.homeDefense
                homeMid.text = item.homeMidfield
                homeFwd.text = item.homeForward
                homeSubst.text = item.homeSubstitutes

                awayScore.text = item.awayScore.toString()+""
                awayGoals.text = item.awayGoalDetails
                awayShots.text = item.awayShots.toString()+""
                awayGK.text = item.awayGoalKeeper
                awayDef.text = item.awayDefense
                awayMid.text = item.awayMidfield
                awayFwd.text = item.awayForward
                awaySubst.text = item.awaySubstitutes
            }

            val request = ApiRepository()
            val gson = Gson()
            presenter = EventDetailsPresenter(this, request, gson)
            presenter.getDetails(mHomeId)

            presenter.getDetails(mAwayId)

            swipeRefresh.onRefresh {
                presenter.getDetails(mHomeId)
                presenter.getDetails(mAwayId)
            }
        } else {
            itemFavorite = intent.getParcelableExtra("extra_item_favorite")
            itemCacheFavorite = itemFavorite

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            mHomeId = itemFavorite.idHome.toString()
            mAwayId = itemFavorite.idAway.toString()

            setContentView(R.layout.details_event)

            swipeRefresh = detailsSwipeRefresh

            detailsDate.text = itemFavorite.date

            homeName.text = itemFavorite.home
            awayName.text = itemFavorite.away

            if(itemFavorite.homeScore!="null"){
                homeScore.text = itemFavorite.homeScore.toString()
                homeGoals.text = itemFavorite.homeGoalDetails
                homeShots.text = itemFavorite.homeShots.toString()+""
                homeGK.text = itemFavorite.homeGoalKeeper
                homeDef.text = itemFavorite.homeDefense
                homeMid.text = itemFavorite.homeMidfield
                homeFwd.text = itemFavorite.homeForward
                homeSubst.text = itemFavorite.homeSubstitutes

                awayScore.text = itemFavorite.awayScore.toString()+""
                awayGoals.text = itemFavorite.awayGoalDetails
                awayShots.text = itemFavorite.awayShots.toString()+""
                awayGK.text = itemFavorite.awayGoalKeeper
                awayDef.text = itemFavorite.awayDefense
                awayMid.text = itemFavorite.awayMidfield
                awayFwd.text = itemFavorite.awayForward
                awaySubst.text = itemFavorite.awaySubstitutes
            }

            favoriteState()

            val request = ApiRepository()
            val gson = Gson()
            presenter = EventDetailsPresenter(this, request, gson)
            presenter.getDetails(mHomeId)

            presenter.getDetails(mAwayId)

            swipeRefresh.onRefresh {
                presenter.getDetails(mHomeId)
                presenter.getDetails(mAwayId)
            }
        }

    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(HOME_ID = {id})",
                            "id" to mHomeId)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(favorite_bar, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun addToFavorite(){
        try {
            if(check) {
                database.use {
                    insert(Favorite.TABLE_FAVORITE,
                            Favorite.HOME_ID to itemCache.idHome.toString(),
                            Favorite.HOME_NAME to itemCache.home.toString(),
                            Favorite.HOME_SCORE to itemCache.homeScore.toString(),
                            Favorite.AWAY_ID to itemCache.idAway.toString(),
                            Favorite.AWAY_NAME to itemCache.away.toString(),
                            Favorite.AWAY_SCORE to itemCache.awayScore.toString(),
                            Favorite.DATE to SimpleDateFormat("EEE, dd MMM yyyy").format(itemCache.date).toString(),
                            Favorite.HOME_GOAL_DETAIL to itemCache.homeGoalDetails.toString(),
                            Favorite.AWAY_GOAL_DETAIL to itemCache.awayGoalDetails.toString(),
                            Favorite.HOME_SHOT to itemCache.homeShots.toString(),
                            Favorite.AWAY_SHOT to itemCache.awayShots.toString(),
                            Favorite.HOME_GOAL_KEEPER to itemCache.homeGoalKeeper.toString(),
                            Favorite.HOME_DEFENSE to itemCache.homeDefense.toString(),
                            Favorite.HOME_MIDFIELD to itemCache.homeMidfield.toString(),
                            Favorite.HOME_FORWARD to itemCache.homeForward.toString(),
                            Favorite.HOME_SUBSTITUTES to itemCache.homeSubstitutes.toString(),
                            Favorite.AWAY_GOAL_KEEPER to itemCache.awayGoalKeeper.toString(),
                            Favorite.AWAY_DEFENSE to itemCache.awayDefense.toString(),
                            Favorite.AWAY_MIDFIELD to itemCache.awayMidfield.toString(),
                            Favorite.AWAY_FORWARD to itemCache.awayForward.toString(),
                            Favorite.AWAY_SUBSTITUTES to itemCache.awaySubstitutes.toString())
                }
            } else {
                database.use {
                    insert(Favorite.TABLE_FAVORITE,
                            Favorite.HOME_ID to itemCacheFavorite.idHome.toString(),
                            Favorite.HOME_NAME to itemCacheFavorite.home.toString(),
                            Favorite.HOME_SCORE to itemCacheFavorite.homeScore.toString(),
                            Favorite.AWAY_ID to itemCacheFavorite.idAway.toString(),
                            Favorite.AWAY_NAME to itemCacheFavorite.away.toString(),
                            Favorite.AWAY_SCORE to itemCacheFavorite.awayScore.toString(),
                            Favorite.DATE to itemCacheFavorite.date.toString(),
                            Favorite.HOME_GOAL_DETAIL to itemCacheFavorite.homeGoalDetails.toString(),
                            Favorite.AWAY_GOAL_DETAIL to itemCacheFavorite.awayGoalDetails.toString(),
                            Favorite.HOME_SHOT to itemCacheFavorite.homeShots.toString(),
                            Favorite.AWAY_SHOT to itemCacheFavorite.awayShots.toString(),
                            Favorite.HOME_GOAL_KEEPER to itemCacheFavorite.homeGoalKeeper.toString(),
                            Favorite.HOME_DEFENSE to itemCacheFavorite.homeDefense.toString(),
                            Favorite.HOME_MIDFIELD to itemCacheFavorite.homeMidfield.toString(),
                            Favorite.HOME_FORWARD to itemCacheFavorite.homeForward.toString(),
                            Favorite.HOME_SUBSTITUTES to itemCacheFavorite.homeSubstitutes.toString(),
                            Favorite.AWAY_GOAL_KEEPER to itemCacheFavorite.awayGoalKeeper.toString(),
                            Favorite.AWAY_DEFENSE to itemCacheFavorite.awayDefense.toString(),
                            Favorite.AWAY_MIDFIELD to itemCacheFavorite.awayMidfield.toString(),
                            Favorite.AWAY_FORWARD to itemCacheFavorite.awayForward.toString(),
                            Favorite.AWAY_SUBSTITUTES to itemCacheFavorite.awaySubstitutes.toString())
                }
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(HOME_ID = {id})","id" to mHomeId)
            }
            snackbar(swipeRefresh, "Removed from favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_action_star)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_action_star_border)
    }

    private fun loadBadge(team: Team, teamBadge: ImageView) {
        Picasso.get().load(team.teamBadge).into(teamBadge)
    }

    override fun showTeamDetails(data: List<Team>, name: String) {
        teams.clear()
        teams.addAll(data)
        if(name==mHomeId) {
            loadBadge(teams[0], homeImg)
        } else {
            loadBadge(teams[0], awayImg)
        }
    }
}