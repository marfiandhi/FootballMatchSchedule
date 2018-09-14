package divascion.marfiandhi.footballmatchschedule.View

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.google.gson.Gson
import divascion.marfiandhi.footballmatchschedule.*
import divascion.marfiandhi.footballmatchschedule.Adapter.MainAdapter
import divascion.marfiandhi.footballmatchschedule.Model.Events.ApiRepository
import divascion.marfiandhi.footballmatchschedule.Model.Events.Schedule
import divascion.marfiandhi.footballmatchschedule.Presenter.MainPresenter
import divascion.marfiandhi.footballmatchschedule.Utils.invisible
import divascion.marfiandhi.footballmatchschedule.Utils.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private var events: MutableList<Schedule> = mutableListOf()

    private lateinit var recycler: RecyclerView

    private lateinit var presenter: MainPresenter
    private lateinit var adapter : MainAdapter

    private val nextEvents = "eventsnextleague.php"
    private val pastEvents = "eventspastleague.php"
    private val league = "English Premier League"

       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_main)

           recycler = findViewById(R.id.recycler)
           recycler.layoutManager = LinearLayoutManager(this)

           adapter = MainAdapter(this, events) {
               val toast = Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT)
               toast.show()
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

