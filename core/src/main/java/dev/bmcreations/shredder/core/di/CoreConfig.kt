package dev.bmcreations.shredder.core.di

import android.app.Application
import dev.bmcreations.shredder.core.preferences.UserPreferences

interface CoreComponent: Component {
    val app: Application
    val prefs: UserPreferences
}

class CoreComponentImpl(
    override val app: Application
): CoreComponent {
    override val prefs = UserPreferences(app)
}
