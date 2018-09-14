package divascion.marfiandhi.footballmatchschedule.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import divascion.marfiandhi.footballmatchschedule.R
import kotlinx.android.synthetic.main.details_event.*

/**
 * Created by Marfiandhi on 9/14/2018.
 */
class EventDetailsActivity: AppCompatActivity() {

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
        mHomeScore = intent.getStringExtra("homeScore")
        mHomeGoal = intent.getStringExtra("homeGoal")
        mHomeShots = intent.getStringExtra("homeShots")
        mHomeGK = intent.getStringExtra("homeGK")
        mHomeDef = intent.getStringExtra("homeDef")
        mHomeMid = intent.getStringExtra("homeMid")
        mHomeFwd = intent.getStringExtra("homeFwd")
        mHomeSubst = intent.getStringExtra("homeSubst")
        mHomeId = intent.getStringExtra("idHome")

        mAway = intent.getStringExtra("away")
        mAwayScore = intent.getStringExtra("awayScore")
        mAwayGoal = intent.getStringExtra("awayGoal")
        mAwayShots = intent.getStringExtra("awayShots")
        mAwayGK = intent.getStringExtra("awayGK")
        mAwayDef = intent.getStringExtra("awayDef")
        mAwayMid = intent.getStringExtra("awayMid")
        mAwayFwd = intent.getStringExtra("awayFwd")
        mAwaySubst = intent.getStringExtra("awaySubst")
        mAwayId = intent.getStringExtra("idAway")

        setContentView(R.layout.details_event)

        detailsDate.text = date

        homeName.text = mHome
        homeScore.text = mHomeScore
        homeGoals.text = mHomeGoal
        homeShots.text = mHomeShots
        homeGK.text = mHomeGK
        homeDef.text = mHomeDef
        homeMid.text = mHomeMid
        homeFwd.text = mHomeFwd
        homeSubst.text = mHomeSubst

        awayName.text = mAway
        awayScore.text = mAwayScore
        awayGoals.text = mAwayGoal
        awayShots.text = mAwayShots
        awayGK.text = mAwayGK
        awayDef.text = mAwayDef
        awayMid.text = mAwayMid
        awayFwd.text = mAwayFwd
        awaySubst.text = mAwaySubst

        // ERROR PADA SAAT ACTIVITY NEXT EVENTS, BUAT IF ELSE TEXTNYA

    }
}