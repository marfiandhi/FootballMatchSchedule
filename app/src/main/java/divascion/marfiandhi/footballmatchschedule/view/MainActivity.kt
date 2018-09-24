package divascion.marfiandhi.footballmatchschedule.view

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import divascion.marfiandhi.footballmatchschedule.*
import divascion.marfiandhi.footballmatchschedule.adapter.FavoriteEventsAdapter
import divascion.marfiandhi.footballmatchschedule.adapter.MainAdapter
import divascion.marfiandhi.footballmatchschedule.database.database
import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.events.Schedule
import divascion.marfiandhi.footballmatchschedule.model.favorite.Favorite
import divascion.marfiandhi.footballmatchschedule.presenter.MainPresenter
import divascion.marfiandhi.footballmatchschedule.utils.invisible
import divascion.marfiandhi.footballmatchschedule.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class MainActivity : AppCompatActivity(), MainView {

    private var events: MutableList<Schedule> = mutableListOf()
    private var favorites: MutableList<Favorite> = mutableListOf()

    private var checkFavorite = false

    private lateinit var recycler: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var presenter: MainPresenter
    private lateinit var adapter : MainAdapter
    private lateinit var adapterFavorite : FavoriteEventsAdapter

    private val nextEvents = "eventsnextleague.php"
    private val pastEvents = "eventspastleague.php"
    private lateinit var presenterEvents: String

       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_main)
           swipeRefresh = mainSwipeRefresh

           recycler = findViewById(R.id.recycler)
           recycler.layoutManager = LinearLayoutManager(this)

           adapter = MainAdapter(this, events) {
               startActivity(intentFor<EventDetailsActivity>(
                       "extra_item" to it,
                       "boolean" to true))
               intent.clearTask()
           }

           adapterFavorite = FavoriteEventsAdapter(this, favorites) {
               startActivity(intentFor<EventDetailsActivity>(
                       "extra_item_favorite" to it,
                       "boolean" to false))
               intent.clearTask()
           }

           recycler.adapter = adapter

           val request = ApiRepository()
           val gson = Gson()
           presenter = MainPresenter(this, request, gson)
           presenterEvents = pastEvents
           presenter.getSchedule(presenterEvents)

           prevMatch.setOnClickListener{
               txtPrevMatch.setTextColor(Color.parseColor("#c90000"))
               txtPrevMatch.textSize = 12F
               txtNextMatch.setTextColor(Color.parseColor("#000000"))
               txtNextMatch.textSize = 8F
               imgPrevMatch.setColorFilter(ContextCompat.getColor(this, R.color.red))
               imgNextMatch.setColorFilter(ContextCompat.getColor(this, R.color.black))
               imgFavorite.setColorFilter(ContextCompat.getColor(this, R.color.black))
               txtFavorite.setTextColor(ContextCompat.getColor(this, R.color.black))
               txtFavorite.textSize = 8f
               presenterEvents = pastEvents
               recycler.adapter = adapter
               checkFavorite = false
               presenter.getSchedule(presenterEvents)
           }

           nextMatch.setOnClickListener{
               txtNextMatch.setTextColor(Color.parseColor("#c90000"))
               txtNextMatch.textSize = 12F
               txtPrevMatch.setTextColor(Color.parseColor("#000000"))
               txtPrevMatch.textSize = 8F
               imgNextMatch.setColorFilter(ContextCompat.getColor(this, R.color.red))
               imgPrevMatch.setColorFilter(ContextCompat.getColor(this, R.color.black))
               imgFavorite.setColorFilter(ContextCompat.getColor(this, R.color.black))
               txtFavorite.setTextColor(ContextCompat.getColor(this, R.color.black))
               txtFavorite.textSize = 8f
               presenterEvents = nextEvents
               recycler.adapter = adapter
               checkFavorite = false
               presenter.getSchedule(presenterEvents)
           }

           favorite.setOnClickListener{
               txtPrevMatch.setTextColor(Color.parseColor("#000000"))
               txtPrevMatch.textSize = 8F
               txtNextMatch.setTextColor(Color.parseColor("#000000"))
               txtNextMatch.textSize = 8F
               imgPrevMatch.setColorFilter(ContextCompat.getColor(this, R.color.black))
               imgNextMatch.setColorFilter(ContextCompat.getColor(this, R.color.black))
               imgFavorite.setColorFilter(ContextCompat.getColor(this, R.color.red))
               txtFavorite.setTextColor(ContextCompat.getColor(this, R.color.red))
               txtFavorite.textSize = 12f
               recycler.adapter = adapterFavorite
               checkFavorite = true
               favorites.clear()
               showFavorite()
           }

           swipeRefresh.onRefresh {
               if(checkFavorite) {
                   recycler.adapter = adapterFavorite
                   favorites.clear()
                   showFavorite()
               } else {
                   presenter.getSchedule(presenterEvents)
               }
           }
    }

    override fun showLoading() {
        progressBars.visible()
    }

    override fun hideLoading() {
        progressBars.invisible()
        swipeRefresh.isRefreshing = false
    }

    override fun showSchedule(data: List<Schedule>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun showFavorite() {
        database.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}