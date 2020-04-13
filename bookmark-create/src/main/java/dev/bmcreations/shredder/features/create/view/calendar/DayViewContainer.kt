package dev.bmcreations.shredder.features.create.view.calendar

import android.view.View
import com.google.android.material.textview.MaterialTextView
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.calendar_day_layout.view.*

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView: MaterialTextView = view.calendarDayText
}
