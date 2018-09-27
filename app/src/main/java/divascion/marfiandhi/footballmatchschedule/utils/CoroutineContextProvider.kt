package divascion.marfiandhi.footballmatchschedule.utils

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Marfiandhi on 9/27/2018.
 */
open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { UI }
}