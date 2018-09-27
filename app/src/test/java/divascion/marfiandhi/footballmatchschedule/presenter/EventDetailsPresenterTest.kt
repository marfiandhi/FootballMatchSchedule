package divascion.marfiandhi.footballmatchschedule.presenter

import android.net.Uri
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
        `when`(gson.fromJson(apiRepository
                .doRequest(EPLTeamDetailsApi.getDetails(id)),
                TeamResponse::class.java
        )).thenReturn(response)
        presenter.getDetails(id)

        verify(view).showTeamDetails(teams, "1234")
        verify(view).hideLoading()
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