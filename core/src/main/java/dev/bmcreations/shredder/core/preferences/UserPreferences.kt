package dev.bmcreations.shredder.core.preferences

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import dev.bmcreations.shredder.models.UserLogin

class UserPreferences(appContext: Context) {

    private val gson = Gson()

    var user: UserLogin? = null
        set(value) {
            field = value
            prefs.edit { putString(USER, gson.toJson(value, UserLogin::class.java)) }
            isLoggedIn = value != null
        }

    var isLoggedIn: Boolean = false
        private set

    private val prefs = appContext.getSharedPreferences("shredder", Context.MODE_PRIVATE)

    init {
        val userString = prefs.getString(USER, null)
        user = gson.fromJson(userString, UserLogin::class.java)
    }

    companion object {
        private const val USER = "user_login"
    }
}
