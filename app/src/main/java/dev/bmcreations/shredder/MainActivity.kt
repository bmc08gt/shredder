package dev.bmcreations.shredder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dev.bmcreations.shredder.core.di.Components
import dev.bmcreations.shredder.core.di.CoreComponent
import dev.bmcreations.shredder.core.di.component
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val core get() = Components.CORE.component<CoreComponent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(findNavController(R.id.nav_host_fragment)) {
            toolbar.setupWithNavController(
                this,
                AppBarConfiguration(topLevelDestinationIds = setOf(R.id.list, R.id.login_or_create_account))
            )

            addOnDestinationChangedListener { _, destination, _ ->
                toolbar.isVisible = when (destination.id) {
                    R.id.list -> false
                    else -> false
                }
            }
            when {
                core.prefs.isLoggedIn -> navigate(R.id.list)
                else -> navigate(R.id.login_or_create_account)
            }
        }
    }
}
