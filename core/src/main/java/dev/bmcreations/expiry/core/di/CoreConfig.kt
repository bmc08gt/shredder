package dev.bmcreations.expiry.core.di

import android.app.Application
import dev.bmcreations.expiry.core.preferences.UserPreferences

interface CoreComponent: Component {
    val app: Application
    val prefs: UserPreferences
}

class CoreComponentImpl(
    override val app: Application
): CoreComponent {
    override val prefs get() = UserPreferences(app)
}
