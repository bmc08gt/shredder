package dev.bmcreations.shredder.models


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class WebManifest(
    val name: String?,
    val short_name: String?,
    val start_url: String?,
    val display: String?,
    val background_color: String?,
    val theme_color: String?,
    val icons: List<Icon?>?
) : Parcelable {
    @Parcelize
    data class Icon(
        val src: String?,
        val sizes: String?,
        val type: String?
    ): Parcelable

    var highestResIcon: Icon? = null
    private set
    get() = icons?.maxBy { it -> it?.sizes?.split("x")?.first()?.toInt() ?: 0  }
}
