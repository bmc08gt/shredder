package dev.bmcreations.shredder.features.create.view.calendar

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.children
import com.google.android.material.textview.MaterialTextView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.ui.DayBinder
import dev.bmcreations.shredder.base.ui.BaseFragment
import dev.bmcreations.shredder.core.di.Components
import dev.bmcreations.shredder.core.di.component
import dev.bmcreations.shredder.core.extensions.*
import dev.bmcreations.shredder.features.create.R
import dev.bmcreations.shredder.features.create.di.BookmarkCreateComponent
import dev.bmcreations.shredder.features.create.view.BookmarkCreateEvent
import kotlinx.android.synthetic.main.fragment_expiration_selection.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.textColor
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.WeekFields
import java.util.*


class BookmarkExpirationSelectionFragment : BaseFragment(), AnkoLogger {

    val create get() = Components.BOOKMARKS_CREATE.component<BookmarkCreateComponent>()

    override val layoutResId: Int = R.layout.fragment_expiration_selection

    var calendarDay: CalendarDay? = null
    set(value) {
        field = value
        date = value?.date
    }

    var date: LocalDate? = null

    var initialDate: Date = now.plusDays(7)

    override fun initView() {
        initializeDateSelector()
        initializeMonthSelector()
        initializeYearSelector()
        setupDateSelectorForMonth(YearMonth.of(initialDate.whatYear, initialDate.monthOfYear))
        create.viewModel.process(BookmarkCreateEvent.ExpirationSet(selectedDate()))
    }

    private fun initializeDateSelector() {
        date_selector.dayBinder = object : DayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                if (date == null) {
                    if (day.date.toDate().isSameDayAs(initialDate)) {
                        calendarDay = day
                    }
                } else if (date != calendarDay?.date) {
                    if (day.date.toDate().isSameDayAs(date?.toDate())) {
                        calendarDay = day
                    }
                }
                container.textView.apply {
                    val selected = day.date.dayOfMonth.coerceToMaxDays(day.date.month) == date?.dayOfMonth?.coerceToMaxDays(day.date.month)
                    textColor = when {
                        selected -> context.colors[R.color.color_on_surface]
                        day.owner == DayOwner.THIS_MONTH -> context.colors[R.color.color_on_surface]
                        else -> context.colors[R.color.material_on_surface_disabled]
                    }
                    background = getCalendarDayBackground(context, selected)
                    isEnabled = day.owner == DayOwner.THIS_MONTH
                    text = day.date.dayOfMonth.toString()
                }
                container.view.setOnClickListener {
                    calendarDay?.let { date_selector.notifyDateChanged(it.transformToSelectedMonth(selectedYearMonth())) }
                    calendarDay = day
                    date_selector.notifyDayChanged(day)
                    create.viewModel.process(BookmarkCreateEvent.ExpirationSet(selectedDate(date?.dayOfMonth)))
                    setupDateSelectorForMonth(selectedYearMonth())
                }
            }

            override fun create(view: View): DayViewContainer = DayViewContainer(view)
        }
        date_selector.inDateStyle = InDateStyle.NONE
        date_selector.outDateStyle = OutDateStyle.NONE
    }

    private fun initializeMonthSelector() {
        var initialId = -1
        month_selector.children.forEachIndexed { index, view ->
            view.tag = when (index) {
                0 -> Month.JANUARY
                1 -> Month.FEBRUARY
                2 -> Month.MARCH
                3 -> Month.APRIL
                4 -> Month.MAY
                5 -> Month.JUNE
                6 -> Month.JULY
                7 -> Month.AUGUST
                8 -> Month.SEPTEMBER
                9 -> Month.OCTOBER
                10 -> Month.NOVEMBER
                11 -> Month.DECEMBER
                else -> null
            }
            if (initialDate.monthOfYear == index + 1) {
                initialId = view.id
            }
        }
        if (initialId >= -1) {
            month_selector.check(initialId)
        }
        month_selector.setOnCheckedChangeListener { _, _ ->
            val ym = selectedYearMonth()
            setupDateSelectorForMonth(ym)
            date = selectedDate(date?.dayOfMonth?.coerceToMaxDays(ym.month))
            create.viewModel.process(BookmarkCreateEvent.ExpirationSet(selectedDate(date?.dayOfMonth?.coerceToMaxDays(ym.month))))
        }
    }

    private fun initializeYearSelector() {
        // current year + 11
        val current = YearMonth.now()
        var currentId = -1
        year_selector.apply {
            year_selector.removeAllViews()
            for (i in 0 until 10) {
                val year = current.plusYears(i.toLong()).year
                val chip = year.toChip(context)
                year_selector.addView(chip)
                if (year == initialDate.whatYear) {
                    currentId = chip.id
                }
            }
            if (currentId >= 0) {
                check(currentId)
            }
            setOnCheckedChangeListener { _, _ ->
                val ym = selectedYearMonth()
                date = selectedDate(date?.dayOfMonth?.coerceToMaxDays(ym.month))
                setupDateSelectorForMonth(ym)
                create.viewModel.process(BookmarkCreateEvent.ExpirationSet(date))
            }
        }
    }

    private fun createLegend(fdow: DayOfWeek) {
        date_legend.apply {
            removeAllViews()
            for (i in 0 until 7) {
                val day = fdow.plus(i.toLong())
                addView(
                    MaterialTextView(context).apply {
                        id = ViewCompat.generateViewId()
                        text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                        textSize = 12.toFloat()
                        textColor = context.colors[R.color.material_on_surface_disabled]
                    }
                )
            }

            date_legend_group.referencedIds =
                date_legend.children.map { it.id }.toList().toIntArray()

            // setup horizontal chain
            val constraints = ConstraintSet().apply {
                clone(date_legend)
                createHorizontalChain(
                    ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                    date_legend_group.referencedIds, null,
                    ConstraintSet.CHAIN_SPREAD_INSIDE
                )
            }

            constraints.applyTo(date_legend)
        }
    }

    private fun setupDateSelectorForMonth(yearMonth: YearMonth) {
        date_selector.apply {
            val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
            createLegend(firstDayOfWeek)
            setup(yearMonth, yearMonth, firstDayOfWeek)
            scrollToDate(selectedDate(date?.dayOfMonth?.coerceToMaxDays(yearMonth.month)))
        }
    }
}
