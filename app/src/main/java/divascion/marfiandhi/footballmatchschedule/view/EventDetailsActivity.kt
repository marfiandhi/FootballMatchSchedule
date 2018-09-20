package divascion.marfiandhi.footballmatchschedule.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.team.Team
import divascion.marfiandhi.footballmatchschedule.presenter.EventDetailsPresenter
import divascion.marfiandhi.footballmatchschedule.R
import divascion.marfiandhi.footballmatchschedule.R.menu.*
import divascion.marfiandhi.footballmatchschedule.R.drawable.*
import divascion.marfiandhi.footballmatchschedule.database.database
import divascion.marfiandhi.footballmatchschedule.model.events.Schedule
import kotlinx.android.synthetic.main.details_event.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
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

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val item = intent.getParcelableExtra<Schedule>("extra_item")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val date = SimpleDateFormat("EEE, dd MMM yyyy").format(item.date).toString()

        mHomeId = item.idHome!!
        mAwayId = item.idAway!!

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

    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    /*private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(favorite_bar, menu)
        menuItem = menu
        setFavorite()
        return true
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