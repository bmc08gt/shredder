package dev.bmcreations.shredder.core.extensions

import java.text.NumberFormat

const val HUNDRED = 100.0
const val THOUSAND = 1000.0
const val MILLION = 1000000.0
const val BILLION = 1000000000.0

fun Number.formatted(decimals: Int = 0): String {
    val formatter = "%.${decimals}f"

    return if (decimals == 0) {
        NumberFormat.getInstance().format(this)
    } else when {
        this.toDouble() >= BILLION -> "${formatter.format(this.toDouble() / BILLION)}B"
        this.toDouble() >= MILLION -> "${formatter.format(this.toDouble() / MILLION)}M"
        this.toDouble() >= THOUSAND -> "${formatter.format(this.toDouble() / THOUSAND)}K"
        else -> this.toString()
    }
}
