package divascion.marfiandhi.footballmatchschedule.presenter

import com.google.gson.Gson
import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.events.Schedule
import divascion.marfiandhi.footballmatchschedule.model.events.ScheduleResponse
import divascion.marfiandhi.footballmatchschedule.model.events.TheSportDBApi
import divascion.marfiandhi.footballmatchschedule.utils.TestContextProvider
import divascion.marfiandhi.footballmatchschedule.view.MainView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Marfiandhi on 9/27/2018.
 */
class MainPresenterTest {
    @Test
    fun getSchedule() {
        val events: MutableList<Schedule> = mutableListOf()
        val response = ScheduleResponse(events)
        val pastEvents = "eventspastleague.php"
        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getSchedule(pastEvents)),
                ScheduleResponse::class.java
        )).thenReturn(response)
        presenter.getSchedule(pastEvents)

        verify(view).showLoading()
        verify(view).showSchedule(events)
        verify(view).hideLoading()
    }

    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, TestContextProvider())
    }
}