package divascion.marfiandhi.footballmatchschedule.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import divascion.marfiandhi.footballmatchschedule.*
import divascion.marfiandhi.footballmatchschedule.adapter.MainAdapter
import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.events.Schedule
import divascion.marfiandhi.footballmatchschedule.presenter.MainPresenter
import divascion.marfiandhi.footballmatchschedule.utils.invisible
import divascion.marfiandhi.footballmatchschedule.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), MainView {

    private var events: MutableList<Schedule> = mutableListOf()

    private lateinit var recycler: RecyclerView

    private lateinit var presenter: MainPresenter
    private lateinit var adapter : MainAdapter

    private val nextEvents = "eventsnextleague.php"
    private val pastEvents = "eventspastleague.php"

       @SuppressLint("SimpleDateFormat")
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_main)

           recycler = findViewById(R.id.recycler)
           recycler.layoutManager = LinearLayoutManager(this)

           adapter = MainAdapter(this, events) {
               val date = SimpleDateFormat("EEE, dd MMM yyyy").format(it.date).toString()
               startActivity(intentFor<EventDetailsActivity>(
                       "date" to date,
                       "home" to it.home,
                       "away" to it.away,
                       "homeScore" to it.homeScore.toString(),
                       "awayScore" to it.awayScore.toString(),
                       "homeGoal" to it.homeGoalDetails,
                       "awayGoal" to it.awayGoalDetails,
                       "homeShots" to it.homeShots.toString(),
                       "awayShots" to it.awayShots.toString(),
                       "homeGK" to it.homeGoalKeeper,
                       "awayGK" to it.awayGoalKeeper,
                       "homeDef" to it.homeDefense,
                       "awayDef" to it.awayDefense,
                       "homeMid" to it.homeMidfield,
                       "awayMid" to it.awayMidfield,
                       "homeFwd" to it.homeForward,
                       "awayFwd" to it.awayForward,
                       "homeSubst" to it.homeSubstitutes,
                       "awaySubst" to it.awaySubstitutes,
                       "idHome" to it.idHome,
                       "idAway" to it.idAway))
               intent.clearTask()
           }
           recycler.adapter = adapter

           val request = ApiRepository()
           val gson = Gson()
           presenter = MainPresenter(this, request, gson)
           presenter.getSchedule(pastEvents)

           prevMatch.setOnClickListener{
               txtPrevMatch.setTextColor(Color.parseColor("#c90000"))
               txtPrevMatch.textSize = 12F
               txtNextMatch.setTextColor(Color.parseColor("#000000"))
               txtNextMatch.textSize = 8F
               imgPrevMatch.setColorFilter(ContextCompat.getColor(this, R.color.red))
               imgNextMatch.setColorFilter(ContextCompat.getColor(this, R.color.black))
               presenter.getSchedule(pastEvents)
           }

           nextMatch.setOnClickListener{
               txtNextMatch.setTextColor(Color.parseColor("#c90000"))
               txtNextMatch.textSize = 12F
               txtPrevMatch.setTextColor(Color.parseColor("#000000"))
               txtPrevMatch.textSize = 8F
               imgNextMatch.setColorFilter(ContextCompat.getColor(this, R.color.red))
               imgPrevMatch.setColorFilter(ContextCompat.getColor(this, R.color.black))
               presenter.getSchedule(nextEvents)
           }
    }

    override fun showLoading() {
        progressBars.visible()
    }

    override fun hideLoading() {
        progressBars.invisible()
    }

    override fun showSchedule(data: List<Schedule>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

}

