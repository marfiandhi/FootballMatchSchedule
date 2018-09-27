package divascion.marfiandhi.footballmatchschedule.presenter

import divascion.marfiandhi.footballmatchschedule.model.ApiRepository
import divascion.marfiandhi.footballmatchschedule.model.team.EPLTeamDetailsApi
import divascion.marfiandhi.footballmatchschedule.model.team.Team
import divascion.marfiandhi.footballmatchschedule.model.team.TeamResponse
import divascion.marfiandhi.footballmatchschedule.utils.TestContextProvider
import divascion.marfiandhi.footballmatchschedule.view.EventDetailsView
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Marfiandhi on 9/28/2018.
 */
class EventDetailsPresenterTest {
    @Test
    fun getDetails() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val id = "1234"
        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(EPLTeamDetailsApi.getDetails(id)),
                TeamResponse::class.java
        )).thenReturn(response)
        presenter.getDetails(id)

        Mockito.verify(view).showTeamDetails(teams, id)
        Mockito.verify(view).hideLoading()
    }
    @Mock
    private
    lateinit var view: EventDetailsView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventDetailsPresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventDetailsPresenter(view, apiRepository, gson, TestContextProvider())
    }

}