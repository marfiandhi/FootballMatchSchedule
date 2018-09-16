package divascion.marfiandhi.footballmatchschedule.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.team.Team
import divascion.marfiandhi.footballmatchschedule.presenter.EventDetailsPresenter
import divascion.marfiandhi.footballmatchschedule.R
import divascion.marfiandhi.footballmatchschedule.model.events.Schedule
import kotlinx.android.synthetic.main.details_event.*
import java.text.SimpleDateFormat

/**
 * Created by Marfiandhi on 9/14/2018.
 */
class EventDetailsActivity: AppCompatActivity(), EventDetailsView {

    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var presenter: EventDetailsPresenter

    private var mHomeId: String = ""
    private var mAwayId: String = ""

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val item = intent.getParcelableExtra<Schedule>("extra_item")

        val date = SimpleDateFormat("EEE, dd MMM yyyy").format(item.date).toString()

        mHomeId = item.idHome!!
        mAwayId = item.idAway!!

        setContentView(R.layout.details_event)

        detailsDate.text = date

        homeName.text = item.home
        awayName.text = item.away

        if(item.homeScore!=null){
            homeScore.text = item.homeScore.toString()
            homeGoals.text = item.homeGoalDetails
            homeShots.text = item.homeShots.toString()
            homeGK.text = item.homeGoalKeeper
            homeDef.text = item.homeDefense
            homeMid.text = item.homeMidfield
            homeFwd.text = item.homeForward
            homeSubst.text = item.homeSubstitutes

            awayScore.text = item.awayScore.toString()
            awayGoals.text = item.awayGoalDetails
            awayShots.text = item.awayShots.toString()
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