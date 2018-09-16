package divascion.marfiandhi.footballmatchschedule.view

import divascion.marfiandhi.footballmatchschedule.model.events.Schedule

/**
 * Created by Marfiandhi on 9/12/2018.
 */
interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showSchedule(data: List<Schedule>)
}