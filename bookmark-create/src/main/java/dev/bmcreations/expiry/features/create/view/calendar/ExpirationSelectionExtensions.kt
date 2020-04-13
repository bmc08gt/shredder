package dev.bmcreations.expiry.features.create.view.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import androidx.core.view.ViewCompat
import com.google.android.material.chip.Chip
import com.kizitonwose.calendarview.model.CalendarDay
import dev.bmcreations.expiry.core.extensions.*
import dev.bmcreations.expiry.features.create.R
import kotlinx.android.synthetic.main.fragment_expiration_selection.*
import org.jetbrains.anko.info
import org.threeten.bp.*
import java.sql.Timestamp
import java.util.*


fun Date.toLocalDate(): LocalDate =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDate()
    } else {
        Timestamp(this.time).toLocalDate()
    }

fun CalendarDay.transformToSelectedMonth(yearMonth: YearMonth): LocalDate {
    return LocalDate.of(yearMonth.year, yearMonth.month, day.coerceToMaxDays(yearMonth.month))
}

fun LocalDate.toDate(): Date = Date(this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())

fun BookmarkExpirationSelectionFragment.selectedDate(day: Int? = null): LocalDate {
    val dayOfMonth = day ?: now.plusDays(7).dayOfMonth
    val ym = selectedYearMonth()
    return LocalDate.of(ym.year, ym.month, dayOfMonth)
}

fun Int.coerceToMaxDays(month: Month): Int = this.coerceAtMost(month.maxLength())

fun BookmarkExpirationSelectionFragment.getCalendarDayBackground(context: Context, selected: Boolean): Drawable? {
    return with(context.drawables[R.drawable.date_selector_bg]?.mutate()) {
        this?.apply {
            if (selected) {
                setTint(context.colors[R.color.color_secondary])
            } else {
                setTint(context.colors[android.R.color.transparent])
            }
        }
    }
}
fun BookmarkExpirationSelectionFragment.selectedYearMonth(): YearMonth {
    val checkedMonthChip = month_selector.findViewById<Chip>(month_selector.checkedChipId)
    val checkedYearChip = year_selector.findViewById<Chip>(year_selector.checkedChipId)
    val month = checkedMonthChip?.tag as? Month ?: YearMonth.now().month
    val year = checkedYearChip?.tag as? Year ?: Year.now()
    info { "month=${month.value}, year=${year.value}" }
    return YearMonth.of(year.value, month.value)
}

@SuppressLint("InflateParams")
fun Int.toChip(
    context: Context
): Chip =
    (LayoutInflater.from(context).inflate(
        R.layout.year_month_chip,
        null,
        false
    ) as Chip).apply {
        id = ViewCompat.generateViewId()
        text = this@toChip.toString()
        tag = Year.of(this@toChip)
        isClickable = true
        isCheckable = true
    }

