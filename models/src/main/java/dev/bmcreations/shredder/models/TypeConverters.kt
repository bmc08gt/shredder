package dev.bmcreations.shredder.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(epoch: Long?): Date? = epoch?.let { Date(it) }
}

class BookmarkConverter {
    @TypeConverter
    fun fromBookmarks(bookmarks: List<Bookmark>): String = bookmarks.toJson()

    @TypeConverter
    fun toBookmarks(string: String): List<Bookmark> = string.toBookmarks()
}

class GroupConverter {
    @TypeConverter
    fun fromGroup(group: Group): String = group.toJson()

    @TypeConverter
    fun toGroup(string: String): Group = string.toGroup()
}

class SiteConverter {
    @TypeConverter
    fun fromGroup(site: Website): String = site.toJson()

    @TypeConverter
    fun toSite(string: String): Website = string.toSite()
}

private val gson = Gson()

internal fun List<Bookmark>.toJson(): String {
    return this.run {
        val type = object : TypeToken< List<Bookmark>>() {}.type
        gson.toJson(this, type)
    }
}

internal fun String.toBookmarks():  List<Bookmark> {
    val type = object : TypeToken< List<Bookmark>>() {}.type
    return type.getInstanceFromJson(this)
}

internal fun String.toGroup(): Group {
    val type = object : TypeToken<Group>() {}.type
    return type.getInstanceFromJson(this)
}

internal fun Group.toJson(): String {
    return this.run {
        val type = object : TypeToken<Group>() {}.type
        gson.toJson(this, type)
    }
}

internal fun Website.toJson(): String {
    return this.run {
        val type = object : TypeToken<Website>() {}.type
        gson.toJson(this, type)
    }
}

internal fun String.toSite(): Website {
    val type = object : TypeToken<Website>() {}.type
    return type.getInstanceFromJson(this)
}

inline fun <reified T> Type.getInstanceFromJson(json: String?): T {
    return Gson().fromJson(json, this)
}
