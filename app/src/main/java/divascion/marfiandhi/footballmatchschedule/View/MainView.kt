package divascion.marfiandhi.footballmatchschedule.View

import divascion.marfiandhi.footballmatchschedule.Model.Events.Schedule

/**
 * Created by Marfiandhi on 9/12/2018.
 */
interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showSchedule(data: List<Schedule>)
}