package divascion.marfiandhi.footballmatchschedule.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.team.Team
import divascion.marfiandhi.footballmatchschedule.presenter.EventDetailsPresenter
import divascion.marfiandhi.footballmatchschedule.R
import kotlinx.android.synthetic.main.details_event.*

/**
 * Created by Marfiandhi on 9/14/2018.
 */
class EventDetailsActivity: AppCompatActivity(), EventDetailsView {

    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var presenter: EventDetailsPresenter

    private var date: String? = null

    private var mHome: String = ""
    private var mHomeScore: String = ""
    private var mHomeGoal: String = ""
    private var mHomeShots: String = ""
    private var mHomeGK: String = ""
    private var mHomeDef: String = ""
    private var mHomeMid: String = ""
    private var mHomeFwd: String = ""
    private var mHomeSubst: String = ""
    private var mHomeId: String = ""

    private var mAway: String = ""
    private var mAwayScore: String = ""
    private var mAwayGoal: String = ""
    private var mAwayShots: String = ""
    private var mAwayGK: String = ""
    private var mAwayDef: String = ""
    private var mAwayMid: String = ""
    private var mAwayFwd: String = ""
    private var mAwaySubst: String = ""
    private var mAwayId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent

        date = intent.getStringExtra("date")

        mHome = intent.getStringExtra("home")
        mAway = intent.getStringExtra("away")

        if(intent.getStringExtra("homeGoal")!=null) {
            mHomeScore = intent.getStringExtra("homeScore")
            mHomeGoal = intent.getStringExtra("homeGoal")
            mHomeShots = intent.getStringExtra("homeShots")
            mHomeGK = intent.getStringExtra("homeGK")
            mHomeDef = intent.getStringExtra("homeDef")
            mHomeMid = intent.getStringExtra("homeMid")
            mHomeFwd = intent.getStringExtra("homeFwd")
            mHomeSubst = intent.getStringExtra("homeSubst")

            mAwayScore = intent.getStringExtra("awayScore")
            mAwayGoal = intent.getStringExtra("awayGoal")
            mAwayShots = intent.getStringExtra("awayShots")
            mAwayGK = intent.getStringExtra("awayGK")
            mAwayDef = intent.getStringExtra("awayDef")
            mAwayMid = intent.getStringExtra("awayMid")
            mAwayFwd = intent.getStringExtra("awayFwd")
            mAwaySubst = intent.getStringExtra("awaySubst")

        }

        mHomeId = intent.getStringExtra("idHome")

        mAwayId = intent.getStringExtra("idAway")

        setContentView(R.layout.details_event)

        detailsDate.text = date

        homeName.text = mHome
        awayName.text = mAway

        homeScore.text = mHomeScore
        homeGoals.text = mHomeGoal
        homeShots.text = mHomeShots
        homeGK.text = mHomeGK
        homeDef.text = mHomeDef
        homeMid.text = mHomeMid
        homeFwd.text = mHomeFwd
        homeSubst.text = mHomeSubst

        awayScore.text = mAwayScore
        awayGoals.text = mAwayGoal
        awayShots.text = mAwayShots
        awayGK.text = mAwayGK
        awayDef.text = mAwayDef
        awayMid.text = mAwayMid
        awayFwd.text = mAwayFwd
        awaySubst.text = mAwaySubst

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